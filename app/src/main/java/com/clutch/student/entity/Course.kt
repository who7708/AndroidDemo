package com.clutch.student.entity

/**
 * Created by clutchyu on 2018/3/17.
 */
/**
 * 课程类，课程号，课程名，学分，图片id
 */
class Course {
    var id = 0
    var name: String? = null
    var credit = 0
    var imageId = 0

    constructor() {}
    constructor(courseId: Int, courseName: String?, credit: Int, imageId: Int) {
        id = courseId
        name = courseName
        this.credit = credit
        this.imageId = imageId
    }
}