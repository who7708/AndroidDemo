package com.clutch.student.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.clutch.student.R
import com.clutch.student.entity.Course
import com.clutch.student.entity.CourseSec
import com.clutch.student.entity.Score
import java.util.*

/**
 * Created by clutchyu on 2018/3/21.
 * 对Score表进行数据库操作
 */
class ScoreDao(private val context: Context) {
    private val dbHelper: MyDatabaseHelper = MyDatabaseHelper(context) // select count(Id) from Orders

    /**
     * 判断表中是否有数据
     */
    fun isDataExist(): Boolean {
        var count = 0
        var db: SQLiteDatabase? = null
        var cursor: Cursor? = null
        try {
            db = dbHelper.readableDatabase
            // select count(Id) from Orders
            cursor = db.query("score", arrayOf("COUNT(Id)"), null, null, null, null, null)
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0)
            }
            if (count > 0) {
                return true
            }
        } catch (e: Exception) {
            Log.e(TAG, "", e)
        } finally {
            cursor?.close()
            db?.close()
        }
        return false
    }

    /**
     * 录入选课信息及成绩
     */
    fun setScore(score: Score): Boolean {
        var db: SQLiteDatabase? = null
        try {
            db = dbHelper.writableDatabase
            db.beginTransaction()
            val values = ContentValues()
            values.put("student_id", score.studentId)
            values.put("course_id", score.courseId)
            values.put("grade", score.grade) //此项数据可能为空
            db.insertOrThrow("student", null, values)
            db.setTransactionSuccessful()
            return true
        } catch (e: SQLiteConstraintException) {
            //Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
            return false
        } catch (e: Exception) {
            Log.e(TAG, "", e)
        } finally {
            if (db != null) {
                db.endTransaction()
                db.close()
            }
        }
        return false
    }

    /**
     * 查询选课情况及课程分数,根据学生Id
     */
    fun getScore(student_id: Int): List<CourseSec>? {
        var db: SQLiteDatabase? = null
        var cursor: Cursor? = null
        val sql = "select c.course_id,c.course_name,credit,grade " +
                "from course as c,score as s " +
                "where s.student_id= ? and s.course_id=c.course_id"
        try {
            db = dbHelper.writableDatabase
            cursor = db.rawQuery(sql, arrayOf(student_id.toString()))
            if (cursor.count > 0) {
                val scoreList: MutableList<CourseSec> = ArrayList(cursor.count)
                while (cursor.moveToNext()) {
                    val course = parseCourseSec(cursor)
                    scoreList.add(course)
                }
                return scoreList
            }
        } catch (e: Exception) {
            Log.e(TAG, "", e)
        } finally {
            cursor?.close()
            db?.close()
        }
        return null
    }

    /**
     * 查询学生已选课程，Course类
     */
    fun getCourse(id: Int): List<Course>? {
        var db: SQLiteDatabase? = null
        var cursor: Cursor? = null
        val sql = "select c.course_id,c.course_name,credit " +
                "from course as c,score as s " +
                "where s.student_id= ? and s.course_id=c.course_id" //出去socre表中已选的课程
        try {
            db = dbHelper.readableDatabase
            cursor = db.rawQuery(sql, arrayOf(id.toString()))
            if (cursor.count > 0) {
                val courseList: MutableList<Course> = ArrayList(cursor.count)
                while (cursor.moveToNext()) {
                    val course = parseCourse(cursor)
                    courseList.add(course)
                }
                return courseList
            }
        } catch (e: Exception) {
            Log.e(TAG, "", e)
        } finally {
            cursor?.close()
            db?.close()
        }
        return null
    }

    /**
     * 查询学生未选课程信息
     */
    fun getUnCourse(id: Int): List<Course>? {
        var db: SQLiteDatabase? = null
        var cursor: Cursor? = null
        val sql = "select * from course where course_id not in" +
                "(select course_id from score where student_id = ?)" //除去socre表中已选的课程
        try {
            db = dbHelper.readableDatabase
            cursor = db.rawQuery(sql, arrayOf(id.toString()))
            if (cursor.count > 0) {
                val courseList: MutableList<Course> = ArrayList(cursor.count)
                while (cursor.moveToNext()) {
                    val course = parseCourse(cursor)
                    courseList.add(course)
                }
                return courseList
            }
        } catch (e: Exception) {
            Log.e(TAG, "", e)
        } finally {
            cursor?.close()
            db?.close()
        }
        return null
    }

    /**
     * 学生选课
     */
    fun chooseCourse(student_id: Int, course_id: Int): Boolean {
        var db: SQLiteDatabase? = null
        try {
            db = dbHelper.writableDatabase
            db.beginTransaction()
            val values = ContentValues()
            values.put("student_id", student_id)
            values.put("course_id", course_id)
            db.insertOrThrow("score", null, values)
            db.setTransactionSuccessful()
            return true
        } catch (e: SQLiteConstraintException) {
            //Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
            return false
        } catch (e: Exception) {
            Log.e(TAG, "", e)
        } finally {
            if (db != null) {
                db.endTransaction()
                db.close()
            }
        }
        return false
    }

    /**
     * 录入学生成绩，需要先判断学生是否选了该门课程，没选提示错误
     * 进行update操作即便是表中数据不符合要更新的条件，无法进行更行，也不会产生异常
     * 先对要进行更新的学生id和课程id进行查询，看表中是否有对应的项，如果有则进行update操作，没有则返回false
     */
    fun writeScore(score: Score): Boolean {
        var db: SQLiteDatabase? = null
        val sql = "update score set grade = ? where student_id = ? and course_id = ?"
        try {
            db = dbHelper.writableDatabase
            db.beginTransaction()
            return if (checkCourse(score.studentId, score.courseId)) {
                db.execSQL(sql, arrayOf<Any>(score.grade, score.studentId, score.courseId))
                db.setTransactionSuccessful()
                true
            } else {
                false
            }
        } catch (e: SQLiteConstraintException) {
            return false
        } catch (e: Exception) {
            Log.e(TAG, "", e)
        } finally {
            if (db != null) {
                db.endTransaction()
                db.close()
            }
        }
        return false
    }

    /**
     * 根据学生id,和课程Id，去Score中查询是否有对应项，有返回true,无返回false
     */
    private fun checkCourse(student_id: Int, course_id: Int): Boolean {
        val db = dbHelper.readableDatabase
        val sql = "select * from score where student_id = ? and course_id = ?"
        val cursor = db.rawQuery(sql, arrayOf(student_id.toString(), course_id.toString()))
        return cursor.count > 0
    }

    /**
     * 将查询到的数据转换为CourseSec类对象
     *
     * @param cursor
     * @return
     */
    private fun parseCourseSec(cursor: Cursor?): CourseSec {
        val course = CourseSec()
        val id = cursor!!.getInt(cursor.getColumnIndex("course_id"))
        course.course_id = cursor.getInt(cursor.getColumnIndex("course_id"))
        course.course_name = cursor.getString(cursor.getColumnIndex("course_name"))
        course.credit = cursor.getInt(cursor.getColumnIndex("credit"))
        course.grade = cursor.getInt(cursor.getColumnIndex("grade"))
        when (id) {
            10001 -> course.imageId = R.drawable.math
            10002 -> course.imageId = R.drawable.chinese
            10003 -> course.imageId = R.drawable.english
            10004 -> course.imageId = R.drawable.physic
            10005 -> course.imageId = R.drawable.sport
            else -> course.imageId = R.drawable.health
        }
        return course
    }

    /**
     * 将找到的数据转化为Course类
     */
    private fun parseCourse(cursor: Cursor?): Course {
        val course = Course()
        val id = cursor!!.getInt(cursor.getColumnIndex("course_id"))
        course.id = id
        course.name = cursor.getString(cursor.getColumnIndex("course_name"))
        course.credit = cursor.getInt(cursor.getColumnIndex("credit"))
        when (id) {
            10001 -> course.imageId = R.drawable.math
            10002 -> course.imageId = R.drawable.chinese
            10003 -> course.imageId = R.drawable.english
            10004 -> course.imageId = R.drawable.physic
            10005 -> course.imageId = R.drawable.sport
            else -> course.imageId = R.drawable.health
        }
        return course
    }

    companion object {
        private const val TAG = "ScoreDao"
    }

}