package com.clutch.student.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clutch.student.MainActivity
import com.clutch.student.MyApplication
import com.clutch.student.R
import com.clutch.student.StudentChangeActivity
import com.clutch.student.adapter.StudentAdapter
import com.clutch.student.dao.StudentDao

/**
 * Created by asus on 2018/3/17.
 * BottomNavigationView对应的第三个Fragment
 * 显示个人信息
 * 从修改信息的界面返回ThirdFragment时，状态为onResume,重写onResume方法实现刷新数据。
 */
class ThirdFragment : Fragment() {
    var mContext: Context = MyApplication.getInstance()
    private val student = StudentDao(mContext)
    private var studentList = student.getStudent(MainActivity.studentId)
    private var recyclerView: RecyclerView? = null
    private val studentId = MainActivity.studentId
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_third, container, false)
        //initCourse();
        recyclerView = view.findViewById<View>(R.id.recycler_view) as RecyclerView
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        val layoutManager = LinearLayoutManager(mContext)
        recyclerView!!.layoutManager = layoutManager
        val adapter = StudentAdapter(studentList!!)
        recyclerView!!.adapter = adapter
        val mDividerItemDecoration = DividerItemDecoration(recyclerView!!.context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(mDividerItemDecoration)
        val button = view.findViewById<View>(R.id.click) as Button
        button.setOnClickListener {
            val intent = Intent(mContext, StudentChangeActivity::class.java)
            intent.putExtra("id", studentId)
            startActivity(intent)
        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onResume() {
        super.onResume()
        studentList = student.getStudent(MainActivity.studentId)
        val adapter = StudentAdapter(studentList!!)
        recyclerView!!.adapter = adapter
    }
}