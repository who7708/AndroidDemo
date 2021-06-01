package com.clutch.student.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.clutch.student.R
import com.clutch.student.entity.Student
import com.clutch.student.entity.Student_info
import java.util.*

/**
 * Created by clutchyu on 2018/3/19.
 * 对student表进行数据库操作
 */
class StudentDao(val context: Context) {
    // 列定义
    private val ORDER_COLUMNS = arrayOf("id", "name", "sex", "age", "phone")
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
            cursor = db.query("student", arrayOf("COUNT(Id)"), null, null, null, null, null)
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
     * 根据学生Id查询学生信息
     */
    fun getStudent(id: Int): List<Student_info>? {
        var db: SQLiteDatabase? = null
        var cursor: Cursor? = null
        try {
            db = dbHelper.readableDatabase
            val sql = "select * from student where id = ?"
            cursor = db.rawQuery(sql, arrayOf(id.toString()))
            if (cursor.count > 0) {
                var student = Student()
                while (cursor.moveToNext()) {
                    student = parseStudent(cursor)
                }
                val StudentList: MutableList<Student_info> = ArrayList(5)
                val sid = Student_info(R.drawable.id, Integer.toString(student.id))
                StudentList.add(sid)
                val sname = Student_info(R.drawable.name, student.name)
                StudentList.add(sname)
                val sex = Student_info(R.drawable.sex, student.sex)
                StudentList.add(sex)
                val age = Student_info(R.drawable.age, Integer.toString(student.age))
                StudentList.add(age)
                val phone = Student_info(R.drawable.phone, student.phone)
                StudentList.add(phone)
                return StudentList
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
     * 更新学生信息,学生用户自己修改个人信息,根据Id进行更新，无法更改Id
     */
    fun updateStudent(student: Student): Student? {
        var db: SQLiteDatabase? = null
        try {
            db = dbHelper.writableDatabase
            db.beginTransaction()
            val value = ContentValues()
            value.put("name", student.name)
            value.put("sex", student.sex)
            value.put("age", student.age)
            value.put("phone", student.phone)
            db.update("student", value, "id = ?", arrayOf(student.id.toString()))
            db.setTransactionSuccessful()
            return student //直接返回了参数中的Student类对象，并没有重新查询数据库中刚进行更新的对象，结果是一样的
        } catch (e: Exception) {
            Log.e(TAG, "", e)
        } finally {
            if (db != null) {
                db.endTransaction()
                db.close()
            }
        }
        return null
    }

    /**
     * 插入学生信息，用户注册
     */
    fun insertStudent(student: Student): Boolean {
        var db: SQLiteDatabase? = null
        try {
            db = dbHelper.writableDatabase
            db.beginTransaction()
            val values = ContentValues()
            values.put("id", student.id)
            values.put("name", student.name)
            values.put("sex", student.sex)
            values.put("age", student.age)
            values.put("phone", student.phone)
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
     * 转化为Student类
     */
    private fun parseStudent(cursor: Cursor?): Student {
        val student = Student()
        student.id = cursor!!.getInt(cursor.getColumnIndex("id"))
        student.name = cursor.getString(cursor.getColumnIndex("name"))
        student.sex = cursor.getString(cursor.getColumnIndex("sex"))
        student.age = cursor.getInt(cursor.getColumnIndex("age"))
        student.phone = cursor.getString(cursor.getColumnIndex("phone"))
        student.imageId = R.drawable.health
        return student
    }

    companion object {
        private const val TAG = "StudentDao"
    }

}