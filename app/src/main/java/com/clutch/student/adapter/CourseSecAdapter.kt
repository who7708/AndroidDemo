package com.clutch.student.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.clutch.student.entity.CourseSec
import com.clutch.student.R

/**
 * Created by clutchyu on 2018/3/22.
 */
/**
 * 课程类适配器，将课程信息展示在CourseSecFragment上
 * 管理员查看所有课程信息
 */
class CourseSecAdapter(private var mCourseSecList: List<CourseSec>) :
    RecyclerView.Adapter<CourseSecAdapter.ViewHolder>() {
    class ViewHolder(var courseSecView: View) : RecyclerView.ViewHolder(courseSecView) {
        var courseSecImage: ImageView = courseSecView.findViewById<View>(R.id.course_image) as ImageView
        var courseSecName: TextView = courseSecView.findViewById<View>(R.id.course_name) as TextView
    }

    fun changeAdapter(courseSecList: List<CourseSec>) {
        mCourseSecList = courseSecList
    }

    /**
     * 点击对应部分显示toast
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_item, parent, false)
        val holder = ViewHolder(view)
        holder.courseSecView.setOnClickListener {
            val position = holder.adapterPosition
            val courseSec = mCourseSecList[position]
        }
        holder.courseSecImage.setOnClickListener {
            val position = holder.adapterPosition
            val courseSec = mCourseSecList[position]
        }
        return holder
    }

    /**
     * 将课程信息显示在textView上
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val CourseSec = mCourseSecList[position]
        holder.courseSecImage.setImageResource(CourseSec.imageId)
        holder.courseSecName.text =
            """学号： ${CourseSec.course_id}         ${CourseSec.course_name}
学分：${CourseSec.credit}
成绩：${CourseSec.grade}"""
    }

    override fun getItemCount(): Int {
        return mCourseSecList.size
    }
}