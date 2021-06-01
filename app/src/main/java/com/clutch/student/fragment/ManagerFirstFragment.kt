package com.clutch.student.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clutch.student.AddCourseActivity
import com.clutch.student.MyApplication
import com.clutch.student.R
import com.clutch.student.adapter.CourseAdapter
import com.clutch.student.dao.CourseDao
import com.clutch.student.entity.Course

/**
 * Created by clutchyu on 2018/3/27.
 * 以管理员身份登入系统的第一个界面，显示所有课程信息及选课人数，底部有添加课程和删除课程的按钮
 */
class ManagerFirstFragment : Fragment() {
    private val course = CourseDao(mContext)
    private var add: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_manager_first, container, false)
        recyclerView = view.findViewById<View>(R.id.recycler_view) as RecyclerView
        emptyText = view.findViewById<View>(R.id.empty_text) as TextView
        add = view.findViewById<View>(R.id.add) as Button
        courseList = course.getCourse()
        if (courseList == null) {
            emptyText!!.visibility = View.VISIBLE
            recyclerView!!.visibility = View.GONE
        } else {
            val layoutManager = LinearLayoutManager(Companion.mContext)
            recyclerView!!.layoutManager = layoutManager
            val adapter = CourseAdapter(courseList!!)
            recyclerView!!.adapter = adapter
            val mDividerItemDecoration = DividerItemDecoration(recyclerView!!.context, layoutManager.orientation)
            recyclerView!!.addItemDecoration(mDividerItemDecoration)
        }
        add!!.setOnClickListener { //跳转到添加课程页面
            val intent = Intent(Companion.mContext, AddCourseActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        courseList = course.getCourse()
        if (courseList == null) {
            emptyText!!.visibility = View.VISIBLE
            recyclerView!!.visibility = View.GONE
        } else {
            emptyText!!.visibility = View.GONE
            recyclerView!!.visibility = View.VISIBLE
            val layoutManager = LinearLayoutManager(mContext)
            recyclerView!!.layoutManager = layoutManager
            val adapter = CourseAdapter(courseList!!)
            recyclerView!!.adapter = adapter
            val mDividerItemDecoration = DividerItemDecoration(recyclerView!!.context, layoutManager.orientation)
            recyclerView!!.addItemDecoration(mDividerItemDecoration)
        }
    }

    companion object {
        var mContext: Context = MyApplication.getInstance()
        private var recyclerView: RecyclerView? = null
        private var emptyText: TextView? = null
        private var courseList: List<Course>? = null
    }
}