package com.clutch.student.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.clutch.student.entity.Course
import com.clutch.student.R

/**
 * Created by clutchyu on 2018/3/17.
 */
/**
 * 课程类适配器，将课程信息展示在CourseFragment上
 * 管理员查看所有课程信息
 */
class CourseAdapter(private val mCourseList: List<Course>) : RecyclerView.Adapter<CourseAdapter.ViewHolder>() {
    class ViewHolder(var courseView: View) : RecyclerView.ViewHolder(courseView) {
        var courseImage: ImageView = courseView.findViewById<View>(R.id.course_image) as ImageView
        var courseName: TextView = courseView.findViewById<View>(R.id.course_name) as TextView
    }

    /**
     * 点击对应部分显示toast
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_item, parent, false)
        val holder = ViewHolder(view)
        holder.courseView.setOnClickListener { v ->
            val position = holder.adapterPosition
            val course = mCourseList[position]
            Toast.makeText(v.context, "you clicked view " + course.name, Toast.LENGTH_SHORT).show()
        }
        holder.courseImage.setOnClickListener { v ->
            val position = holder.adapterPosition
            val course = mCourseList[position]
            Toast.makeText(v.context, "you clicked image " + course.name, Toast.LENGTH_SHORT).show()
        }
        return holder
    }

    /**
     * 将课程信息显示在textView上
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = mCourseList[position]
        holder.courseImage.setImageResource(course.imageId)
        holder.courseName.text = """课程号：${course.id}          ${course.name} 学分：${course.credit}"""
    }

    override fun getItemCount(): Int {
        return mCourseList.size
    }
}