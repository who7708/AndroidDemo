package com.clutch.student.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.clutch.student.R
import com.clutch.student.entity.Student_info

/**
 * Created by clutchyu on 2018/3/19.
 */
/**
 * 学生信息类适配器，将查找到的信息展示在ThirdFragment上
 * Student_info类，属性为图片Id，和一个字符串代表student中的各个属性，即每获取一个student对象将其转化成5个该对象，添加到List中。
 */
class StudentAdapter(private val mStudentList: List<Student_info>) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    class ViewHolder(var studentView: View) : RecyclerView.ViewHolder(
        studentView
    ) {
        var studentImage: ImageView = studentView.findViewById<View>(R.id.student_image) as ImageView
        var studentText: TextView = studentView.findViewById<View>(R.id.student_text) as TextView
    }

    /**
     * 点击对应部分显示toast
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        val holder = ViewHolder(view)
        holder.studentView.setOnClickListener {
            val position = holder.adapterPosition
            val student = mStudentList[position]
        }
        holder.studentImage.setOnClickListener {
            val position = holder.adapterPosition
            val student = mStudentList[position]
        }
        return holder
    }

    /**
     * 将课程信息显示在textView上
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = mStudentList[position]
        holder.studentImage.setImageResource(student.image)
        holder.studentText.text = student.info
    }

    override fun getItemCount(): Int {
        return mStudentList.size
    }
}