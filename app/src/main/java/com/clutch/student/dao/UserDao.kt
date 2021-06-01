package com.clutch.student.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import com.clutch.student.entity.User

/**
 * Created by clutchyu on 2018/3/26.
 * 对log表进行数据库操作
 */
class UserDao(val context: Context) {
    private val dbHelper: MyDatabaseHelper = MyDatabaseHelper(context)

    /**
     * 检查登录表中是否有对应的账号
     */
    fun check(id: Int, password: String): Boolean {
        val sql = "select password from log where student_id = ?"
        try {
            val db = dbHelper.readableDatabase
            val cursor = db.rawQuery(sql, arrayOf(id.toString()))
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val user = User()
                    val passwd = cursor.getString(cursor.getColumnIndex("password"))
                    if (passwd == password) {
                        return true
                    }
                }
            }
        } catch (e: Exception) {
        } /*finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }*/
        return false
    }

    /**
     * 向表中插入数据
     */
    fun insertLog(studentId: Int, password: String?): Boolean {
        var db: SQLiteDatabase? = null
        try {
            db = dbHelper.writableDatabase
            db.beginTransaction()
            val values = ContentValues()
            values.put("student_id", studentId)
            values.put("password", password)
            db.insertOrThrow("log", null, values)
            db.setTransactionSuccessful()
            return true
        } catch (e: SQLiteConstraintException) {
            //Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
            return false
        } catch (e: Exception) {
        } finally {
            if (db != null) {
                db.endTransaction()
                db.close()
            }
        }
        return false
    }

    /**
     * 修改密码
     */
    fun modifyPasswd(user: User, newPasswd: String): Boolean {
        var db: SQLiteDatabase? = null
        val sql = "update log set password = ? where student_id = ?"
        try {
            db = dbHelper.writableDatabase
            db.beginTransaction()
            return if (check(user.id, user.password)) {
                db.execSQL(sql, arrayOf<Any>(newPasswd, user.id))
                db.setTransactionSuccessful()
                true
            } else {
                false
            }
        } catch (e: SQLiteConstraintException) {
            //Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
            return false
        } catch (e: Exception) {
        } finally {
            if (db != null) {
                db.endTransaction()
                db.close()
            }
        }
        return false
    }

}