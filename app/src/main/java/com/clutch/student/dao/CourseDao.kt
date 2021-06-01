package com.clutch.student.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.clutch.student.R
import com.clutch.student.entity.Course
import java.util.*

/**
 * Created by clutchyu on 2018/3/17.
 * 对course表进行数据库操作
 */
class CourseDao(val context: Context) {
    // 列定义
    private val ORDER_COLUMNS = arrayOf("course_id", "course_name", "credit")
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
            cursor = db.query("course", arrayOf("COUNT(Id)"), null, null, null, null, null)
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
    //cursor = db.rawQuery("select * from course",null);
    /* cursor = db.query(dbHelper.TABLE_NAME,
              ORDER_COLUMNS,
              "course_name = ?",
              new String[] {"math"},
              null, null, null);*/
    /**
     * 查询课程信息
     */
    fun getCourse(): List<Course>? {
        var db: SQLiteDatabase? = null
        var cursor: Cursor? = null
        val sql = "select * from course"
        try {
            db = dbHelper.readableDatabase
            cursor = db.rawQuery(sql, null)
            //cursor = db.rawQuery("select * from course",null);
            /* cursor = db.query(dbHelper.TABLE_NAME,
                      ORDER_COLUMNS,
                      "course_name = ?",
                      new String[] {"math"},
                      null, null, null);*/if (cursor.count > 0) {
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
     * 添加课程
     */
    fun addCourse(course: Course): Boolean {
        var db: SQLiteDatabase? = null
        try {
            db = dbHelper.writableDatabase
            db.beginTransaction()
            val values = ContentValues()
            values.put("course_id", course.id)
            values.put("course_name", course.name)
            values.put("credit", course.credit)
            db.insertOrThrow("course", null, values)
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
     * 删除课程,以课程id
     */
    fun deleteCourse(courseId: Int): Boolean {
        var db: SQLiteDatabase? = null
        try {
            db = dbHelper.writableDatabase
            db.beginTransaction()
            db.delete("course", "course_id = ?", arrayOf(courseId.toString()))
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
        private const val TAG = "CourseDao"
    }

}