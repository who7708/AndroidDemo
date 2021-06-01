package com.clutch.student.entity

/**
 * Created by clutchyu on 2018/3/26.
 * 登录信息类
 */
class User {
    var id = 0
    lateinit var password: String

    constructor() {}
    constructor(student_id: Int, password: String) {
        id = student_id
        this.password = password
    }
}