package com.clutch.student.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by clutchyu on 2018/3/17.
 * 创建表，运行MainActivity时首先创建该对象dbHelper
 */
//构造方法：第一个参数Context，第二个参数数据库名，第三个参数cursor允许我们在查询数据的时候返回一个自定义的光标位置，
// 一般传入的都是null，第四个参数表示目前库的版本号（用于对库进行升级）
class MyDatabaseHelper(val mContext: Context) : SQLiteOpenHelper(mContext, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        //调用SQLiteDatabase中的execSQL（）执行建表语句。
        db.execSQL(CREATE_STUDENT)
        db.execSQL(CREATE_COURSE)
        db.execSQL(CREATE_SCORE)
        db.execSQL(CREATE_LOG)
        init(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    /**
     * 插入初始化数据
     */
    fun init(db: SQLiteDatabase) {
        val sql = arrayOf(
            "insert into student values (10001,'小李','男',20,'123456789')",
            "insert into student values (10002,'小王','男',20,'123456789')",
            "insert into student values (10003,'小张','女',20,'123456789')",
            "insert into student values (10004,'小周','男',20,'123456789')",
            "insert into student values (10005,'小吾','女',20,'123456789')",
            "insert into student values (10006,'小贾','男',20,'123456789')",
            "insert into student values (00000,'管理员','男',0,'0000000')",
            "insert into course values (10001,'数学',5)",
            "insert into course values (10002,'语文',5)",
            "insert into course values (10003,'英语',5)",
            "insert into course values (10004,'物理',5)",
            "insert into course values (10005,'体育',5)",
            "insert into course values (10006,'数据库',5)",
            "insert into course values (10007,'系统结构',6)",
            "insert into course values (10008,'组成原理',5)",
            "insert into course values (10009,'程序设计',6)",
            "insert into course values (100010,'网络',5)",
            "insert into score values (10001,10001,95)",
            "insert into score values (10001,10002,95)",
            "insert into score values (10001,10003,95)",
            "insert into score values (10002,10001,95)",
            "insert into score values (10002,10006,95)",
            "insert into score values (10005,10007,95)",
            "insert into log values (10001,'123456')",
            "insert into log values (10002,'123456')",
            "insert into log values (10003,'123456')",
            "insert into log values (10004,'123456')",
            "insert into log values (10005,'123456')",
            "insert into log values (10006,'123456')",
            "insert into log values (00000,'000000')"
        )
        for (i in sql.indices) {
            db.execSQL(sql[i])
        }
    }

    companion object {
        private const val DB_VERSION = 1
        private const val DB_NAME = "StudentSystem.db"
        const val CREATE_STUDENT = "create table student(" +
                "id int  primary key," +
                "name text not null," +
                "sex  text  not null," +
                "age  int  not null," +
                "phone text not null)"
        const val CREATE_COURSE = "create table course(" +
                "course_id int primary key," +
                "course_name text unique," +
                "credit int  not null)"
        const val CREATE_SCORE = "create table score(" +
                "student_id int not null," +
                "course_id int not null," +
                "grade int," +
                "constraint  score_PK primary key(student_id,course_id)," +
                "constraint fk_SID foreign key(student_id) references student(id)on delete cascade," +
                "constraint fk_CID foreign key(course_id) references course(course_id)on delete cascade)"
        const val CREATE_LOG = "create table log(" +
                "student_id  int primary key," +
                "password   text  not null," +
                "constraint fk_ID foreign key(student_id) references student(id) on delete cascade)"
    }
}