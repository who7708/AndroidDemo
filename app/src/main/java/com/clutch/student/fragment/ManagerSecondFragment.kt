package com.clutch.student.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.clutch.student.EditCheck
import com.clutch.student.MyApplication
import com.clutch.student.R
import com.clutch.student.dao.ScoreDao
import com.clutch.student.entity.Score

/**
 * Created by clutchyu on 2018/3/27.
 * 以管理员身份登入系统的第二个界面，修改学生选课成绩
 */
class ManagerSecondFragment : Fragment() {
    private val score = Score()
    private var studentText: EditText? = null
    private var courseText: EditText? = null
    private var creditText: EditText? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_manager_second, container, false)
        studentText = view.findViewById<View>(R.id.editText) as EditText
        courseText = view.findViewById<View>(R.id.editText2) as EditText
        creditText = view.findViewById<View>(R.id.editText3) as EditText
        val commit = view.findViewById<View>(R.id.commit) as Button
        val cancel = view.findViewById<View>(R.id.cancel_s) as Button
        commit.setOnClickListener { v ->
            if (!EditCheck.checkInt(studentText!!.text.toString(), "学生学号", 10000, 99999)) {
                Toast.makeText(v.context, EditCheck.warning, Toast.LENGTH_SHORT).show()
            } else if (!EditCheck.checkInt(courseText!!.text.toString(), "课程号", 10000, 99999)) {
                Toast.makeText(v.context, EditCheck.warning, Toast.LENGTH_SHORT).show()
            } else if (!EditCheck.checkInt(creditText!!.text.toString(), "成绩", 0, 100)) {
                Toast.makeText(v.context, EditCheck.warning, Toast.LENGTH_SHORT).show()
            } else if (writeDatabase()) {
                val word = "录入成功！"
                studentText!!.setText("")
                courseText!!.setText("")
                creditText!!.setText("")
                Toast.makeText(v.context, word, Toast.LENGTH_SHORT).show()
            } else {
                val word = "录入失败\n不存在此学生或此学生未选课"
                Toast.makeText(v.context, word, Toast.LENGTH_SHORT).show()
            }
        }
        cancel.setOnClickListener { //清空输入框
            studentText!!.setText("")
            courseText!!.setText("")
            creditText!!.setText("")
        }
        return view
    }

    fun writeDatabase(): Boolean {
        val scoreDao = ScoreDao(MyApplication.getInstance())
        val studentId = studentText!!.text.toString().toInt()
        val courseId = courseText!!.text.toString().toInt()
        val grade = creditText!!.text.toString().toInt()
        score.studentId = studentId
        score.courseId = courseId
        score.grade = grade
        return scoreDao.writeScore(score)
    }
}