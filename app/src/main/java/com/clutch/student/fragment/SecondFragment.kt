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
import com.clutch.student.adapter.ScoreAdapter
import com.clutch.student.dao.ScoreDao
import com.clutch.student.entity.Course

/**
 * Created by clutch on 2018/3/17.
 * BottomNavigationView对应的第二个Fragment
 * 用于学生选课，学生点击对应课程即可提示选课成功
 */
class SecondFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        recyclerView_undo = view.findViewById<View>(R.id.recycler_undo) as RecyclerView
        emptyText = view.findViewById<View>(R.id.empty_text) as TextView
        undoList = uncourse.getUnCourse(MainActivity.studentId)
        if (undoList == null) {
            emptyText!!.visibility = View.VISIBLE
            recyclerView_undo!!.visibility = View.GONE
        } else {
            //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            val layoutManager = LinearLayoutManager(mContext)
            recyclerView_undo!!.layoutManager = layoutManager
            val adapter = ScoreAdapter(undoList!!)
            recyclerView_undo!!.adapter = adapter
            val mDividerItemDecoration = DividerItemDecoration(recyclerView_undo!!.context, layoutManager.orientation)
            recyclerView_undo!!.addItemDecoration(mDividerItemDecoration)
        }
        return view
    }

    companion object {
        var mContext: Context = MyApplication.getInstance()
        private val uncourse = ScoreDao(mContext) //未选的课程
        private var recyclerView_undo: RecyclerView? = null
        private var undoList: List<Course>? = null
        private val adapter: ScoreAdapter? = null
        private var emptyText: TextView? = null

        /**
         * 刷新数据，点击对应课程在Adapter中调用该函数实现页面刷新
         */
        fun update() {
            undoList = uncourse.getUnCourse(MainActivity.studentId)
            if (undoList == null) {
                emptyText!!.visibility = View.VISIBLE
                recyclerView_undo!!.visibility = View.GONE
            } else {
                val layoutManager = LinearLayoutManager(mContext)
                recyclerView_undo!!.layoutManager = layoutManager
                val adapter = ScoreAdapter(undoList!!)
                recyclerView_undo!!.adapter = adapter
                val mDividerItemDecoration =
                    DividerItemDecoration(recyclerView_undo!!.context, layoutManager.orientation)
                recyclerView_undo!!.addItemDecoration(mDividerItemDecoration)
            }
        }
    }
}