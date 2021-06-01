package com.clutch.student.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.clutch.student.MainActivity
import com.clutch.student.MyApplication
import com.clutch.student.R
import com.clutch.student.dao.ScoreDao
import com.clutch.student.entity.Course
import com.clutch.student.fragment.FirstFragment
import com.clutch.student.fragment.SecondFragment

/**
 * Created by clutchyu on 2018/3/26.
 * 显示学生未选课的信息，显示到SecondFragment中
 */
class ScoreAdapter(private val mCourseList: List<Course>) : RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {
    var context: Context = MyApplication.getInstance()
    private val score = ScoreDao(context)

    class ViewHolder(var courseView: View) : RecyclerView.ViewHolder(courseView) {
        var courseImage: ImageView = courseView.findViewById<View>(R.id.course_image) as ImageView
        var courseName: TextView = courseView.findViewById<View>(R.id.course_name) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_item, parent, false)
        val holder = ViewHolder(view)
        holder.courseView.setOnClickListener { v ->
            val position = holder.adapterPosition
            val course = mCourseList[position]
            score.chooseCourse(MainActivity.studentId, course.id)
            Toast.makeText(v.context, "选课成功！", Toast.LENGTH_SHORT).show()
            SecondFragment.update()
            FirstFragment.update()
        }
        holder.courseImage.setOnClickListener { v ->
            val position = holder.adapterPosition
            val course = mCourseList[position]
            score.chooseCourse(MainActivity.studentId, course.id)
            Toast.makeText(v.context, "选课成功！ ", Toast.LENGTH_SHORT).show()
            SecondFragment.update()
            FirstFragment.update()
        }
        return holder
    }

    /**
     * 将课程信息显示在textView上
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = mCourseList[position]
        holder.courseImage.setImageResource(course.imageId)
        holder.courseName.text = """课程号：${course.id}          ${course.name}
学分：${course.credit}"""
    }

    override fun getItemCount(): Int {
        return mCourseList.size
    }
}