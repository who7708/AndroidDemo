package com.clutch.student

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.clutch.student.dao.CourseDao
import com.clutch.student.entity.Course

class AddCourseActivity : AppCompatActivity() {
    private var idText: EditText? = null
    private var nameText: EditText? = null
    private var creditText: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        idText = findViewById<View>(R.id.studentId) as EditText
        nameText = findViewById<View>(R.id.courseId) as EditText
        creditText = findViewById<View>(R.id.credit) as EditText
        val add = findViewById<View>(R.id.add) as Button
        val delete = findViewById<View>(R.id.delete) as Button
        val cancel = findViewById<View>(R.id.cancel_a) as Button
        add.setOnClickListener {
            val word: String
            if (!EditCheck.checkInt(idText!!.text.toString(), "课程号", 10000, 99999)) {
                showNormalDialog(EditCheck.warning)
            } else if (!EditCheck.checkString(nameText!!.text.toString(), "课程名", 10)) {
                showNormalDialog(EditCheck.warning)
            } else if (!EditCheck.checkInt(creditText!!.text.toString(), "学分", 1, 10)) {
                showNormalDialog(EditCheck.warning)
            } else if (addDatebase()) {
                word = "添加课程成功！"
                showNormalDialog(word)
                idText!!.setText("")
                nameText!!.setText("")
                creditText!!.setText("")
                // finish();
            } else {
                word = "添加课程失败，请重试！"
                showNormalDialog(word)
            }
        }
        delete.setOnClickListener {
            val word: String
            when {
                idText!!.text.toString() == "" -> {
                    word = "课程号不能为空！"
                    showNormalDialog(word)
                }
                deleteDatabase() -> {
                    word = "删除课程成功！"
                    showNormalDialog(word)
                    idText!!.setText("")
                    nameText!!.setText("")
                    creditText!!.setText("")
                    // finish();
                }
                else -> {
                    word = "删除课程失败，请重试！"
                    showNormalDialog(word)
                }
            }
        }
        cancel.setOnClickListener { finish() }
    }

    private fun showNormalDialog(word: String?) {
        val normalDialog = AlertDialog.Builder(this@AddCourseActivity)
        normalDialog.setTitle("提示")
        normalDialog.setMessage(word)
        normalDialog.setPositiveButton(
            "确定"
        ) { _, _ ->
            normalDialog.setCancelable(true)
            //...To-do
        }

        // 显示
        normalDialog.show()
    }

    /**
     * 向数据库添加课程信息
     */
    fun addDatebase(): Boolean {
        val courseDao = CourseDao(MyApplication.getInstance())
        val courseId = idText!!.text.toString().toInt()
        val courseName = nameText!!.text.toString()
        val credit = creditText!!.text.toString().toInt()
        val course = Course(courseId, courseName, credit, R.drawable.health)
        return courseDao.addCourse(course)
    }

    /**
     * 在数据库删除课程信息
     */
    fun deleteDatabase(): Boolean {
        val courseDao = CourseDao(MyApplication.getInstance())
        val courseId = idText!!.text.toString().toInt()
        return courseDao.deleteCourse(courseId)
    }
}