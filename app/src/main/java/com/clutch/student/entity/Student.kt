package com.clutch.student.entity

/**
 * Created by clutchyu on 2018/3/19.
 * 学生实体类，id,name,sex,age,phone
 */
class Student {
    var id = 0
    var name: String? = null
    var sex: String? = null
    var age = 0
    var phone: String? = null
    var imageId = 0

    constructor() {}
    constructor(id: Int, name: String?, sex: String?, age: Int, phone: String?, imageId: Int) {
        this.id = id
        this.name = name
        this.sex = sex
        this.age = age
        this.phone = phone
        this.imageId = imageId
    }
}