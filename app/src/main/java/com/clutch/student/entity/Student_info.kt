package com.clutch.student.entity

/**
 * Created by clutchyu on 2018/3/19.
 * 拆分学生信息的内部类，用于单项展示学生属性在RecycleView上
 */
class Student_info {
    var image = 0
    var info: String? = null

    constructor() {}
    constructor(image: Int, info: String?) {
        this.image = image
        this.info = info
    }
}