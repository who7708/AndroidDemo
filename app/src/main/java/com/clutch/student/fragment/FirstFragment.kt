package com.clutch.student.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clutch.student.MainActivity
import com.clutch.student.MyApplication
import com.clutch.student.R
import com.clutch.student.adapter.CourseSecAdapter
import com.clutch.student.dao.ScoreDao
import com.clutch.student.entity.CourseSec

/**
 * Created by clutchyu on 2018/3/17.
 * BottomNavigationView对应的第一个Fragment
 * 显示学生已选课程信息，整个页面布局为一个RecyclerView
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        // initCourse();
        recyclerView = view.findViewById<View>(R.id.recycler_view) as RecyclerView
        emptyText = view.findViewById<View>(R.id.empty_text) as TextView //用于在无数据时进行提示
        courseList = course.getScore(MainActivity.studentId) //获取已选课程的List
        if (courseList == null) {
            //若数据为空，则显示emptyText,隐藏recyclerView
            emptyText!!.visibility = View.VISIBLE
            recyclerView!!.visibility = View.GONE
        } else {
            val layoutManager = LinearLayoutManager(mContext) //线性布局
            recyclerView!!.layoutManager = layoutManager //RecyclerView加载线性布局
            val adapter = CourseSecAdapter(courseList!!) //创建课程类适配器，参数为刚查询到的存有课程信息的CourseList
            recyclerView!!.adapter = adapter //将适配器加载入recyclerView中
            //为RecyclerView中的每一项加分割线
            val mDividerItemDecoration = DividerItemDecoration(recyclerView!!.context, layoutManager.orientation)
            recyclerView!!.addItemDecoration(mDividerItemDecoration)
        }
        // Inflate the layout for this fragment
        return view
    }

    companion object {
        var mContext: Context = MyApplication.getInstance()
        private val course = ScoreDao(mContext)
        private var emptyText: TextView? = null
        private var recyclerView: RecyclerView? = null
        private var courseList: List<CourseSec>? = null

        /**
         * 刷新数据，点击对应课程在Adapter中调用该函数实现页面刷新
         * 内容为重新查询数据库中的数据放入recyclerView
         */
        fun update() {
            courseList = course.getScore(MainActivity.studentId)
            if (courseList == null) {
                emptyText!!.visibility = View.VISIBLE
                recyclerView!!.visibility = View.GONE
            } else {
                emptyText!!.visibility = View.GONE
                recyclerView!!.visibility = View.VISIBLE
                val layoutManager = LinearLayoutManager(mContext)
                recyclerView!!.layoutManager = layoutManager
                val adapter = CourseSecAdapter(courseList!!)
                recyclerView!!.adapter = adapter
                val mDividerItemDecoration = DividerItemDecoration(recyclerView!!.context, layoutManager.orientation)
                recyclerView!!.addItemDecoration(mDividerItemDecoration)
            }
        }
    }
}