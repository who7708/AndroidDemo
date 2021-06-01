package com.clutch.student.entity

/**
 * Created by clutchyu on 2018/3/21.
 * 选课信息类，课程Id,学生Id,分别对应各自表中的属性，有外键约束
 * 成绩grade为可空属性，即学生选课并未结课，结课后由管理员录入成绩
 * 学生无权限修改该表中的数据
 */
class Score {
    var courseId = 0
    var studentId = 0
    var grade = 0

    constructor() {}
    constructor(course: Int, student: Int, grade: Int) {
        courseId = course
        studentId = student
        this.grade = grade
    }
}