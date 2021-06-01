package com.clutch.student

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.clutch.student.EditCheck.checkInt
import com.clutch.student.EditCheck.checkString
import com.clutch.student.EditCheck.warning
import com.clutch.student.dao.StudentDao
import com.clutch.student.entity.Student

/**
 * 修改个人信息界面
 */
class StudentChangeActivity : AppCompatActivity(), View.OnClickListener {
    private var name: EditText? = null
    private var mMaleRb: RadioButton? = null
    private var mFemaleRb: RadioButton? = null
    private var age: EditText? = null
    private var phone: EditText? = null
    private var studentId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_change)
        val intent = intent
        studentId = intent.getIntExtra("id", -1)
        val save = findViewById<View>(R.id.save) as Button
        val cancel = findViewById<View>(R.id.cancel) as Button
        name = findViewById<View>(R.id.name) as EditText
        //sex = (EditText)findViewById(R.id.sex);
        mMaleRb = findViewById<View>(R.id.male_rb) as RadioButton
        mFemaleRb = findViewById<View>(R.id.famale_rb) as RadioButton
        age = findViewById<View>(R.id.age) as EditText
        phone = findViewById<View>(R.id.phone) as EditText
        save.setOnClickListener(this)
        cancel.setOnClickListener { finish() }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.save -> {
                val sName = name!!.text.toString()
                var sSex = ""
                if (mMaleRb!!.isChecked) {
                    sSex = "男"
                } else if (mFemaleRb!!.isChecked) {
                    sSex = "女"
                }
                val sage = age!!.text.toString()
                val sPhone = phone!!.text.toString()
                if (!checkString(sName, "名字", 6)) {
                    Toast.makeText(v.context, warning, Toast.LENGTH_SHORT).show()
                    return
                } else if (!mMaleRb!!.isChecked && !mFemaleRb!!.isChecked) {
                    Toast.makeText(v.context, "性别不能为空！", Toast.LENGTH_SHORT).show()
                    return
                } else if (!checkInt(sage, "年龄", 10, 60)) {
                    Toast.makeText(v.context, warning, Toast.LENGTH_SHORT).show()
                    return
                } else if (!checkString(sPhone, "电话", 15)) {
                    Toast.makeText(v.context, warning, Toast.LENGTH_SHORT).show()
                    return
                }
                val student = Student(studentId, sName, sSex, sage.toInt(), sPhone, R.drawable.health)
                writeDatabase(student)
                finish()
            }
            R.id.cancel -> finish()
            else -> {
            }
        }
    }

    /**
     * 写入数据库
     */
    private fun writeDatabase(student: Student) {
        val context: Context = MyApplication.getInstance()
        val stuDao = StudentDao(context)
        stuDao.updateStudent(student)
    }
}