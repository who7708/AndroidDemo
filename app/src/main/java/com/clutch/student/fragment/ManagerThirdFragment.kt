package com.clutch.student.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.clutch.student.EditCheck
import com.clutch.student.MyApplication
import com.clutch.student.R
import com.clutch.student.dao.StudentDao
import com.clutch.student.dao.UserDao
import com.clutch.student.entity.Student

/**
 * Created by clutchyu on 2018/3/29.
 * 以管理员身份登入系统的第三个界面，添加学生
 */
class ManagerThirdFragment : Fragment() {
    private var id: EditText? = null
    private var name: EditText? = null
    private var mMaleRb: RadioButton? = null
    private var mFamaleRb: RadioButton? = null
    private var age: EditText? = null
    private var phone: EditText? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_manager_third, container, false)
        id = view.findViewById<View>(R.id.id_t) as EditText
        val save = view.findViewById<View>(R.id.save_t) as Button
        val cancel = view.findViewById<View>(R.id.cancel_t) as Button
        name = view.findViewById<View>(R.id.name_t) as EditText
        mMaleRb = view.findViewById<View>(R.id.male_rb_t) as RadioButton
        mFamaleRb = view.findViewById<View>(R.id.famale_rb_t) as RadioButton
        age = view.findViewById<View>(R.id.age_t) as EditText
        phone = view.findViewById<View>(R.id.phone_t) as EditText
        save.setOnClickListener { v ->
            val sname = name!!.text.toString()
            var ssex = ""
            if (mMaleRb!!.isChecked) {
                ssex = "男"
            } else if (mFamaleRb!!.isChecked) {
                ssex = "女"
            }
            val sage = age!!.text.toString()
            val sphone = phone!!.text.toString()
            if (!EditCheck.checkInt(id!!.text.toString(), "学号", 10000, 99999)) {
                Toast.makeText(v.context, EditCheck.warning, Toast.LENGTH_SHORT).show()
            } else if (!EditCheck.checkString(sname, "名字", 6)) {
                Toast.makeText(v.context, EditCheck.warning, Toast.LENGTH_SHORT).show()
            } else if (!mMaleRb!!.isChecked && !mFamaleRb!!.isChecked) {
                Toast.makeText(v.context, "性别不能为空！", Toast.LENGTH_SHORT).show()
            } else if (!EditCheck.checkInt(sage, "年龄", 10, 60)) {
                Toast.makeText(v.context, EditCheck.warning, Toast.LENGTH_SHORT).show()
            } else if (!EditCheck.checkString(sphone, "电话", 15)) {
                Toast.makeText(v.context, EditCheck.warning, Toast.LENGTH_SHORT).show()
            } else {
                val sid = id!!.text.toString().toInt()
                val student = Student(sid, sname, ssex, sage.toInt(), sphone, R.drawable.health)
                if (writeDatabase(student)) {
                    Toast.makeText(v.context, "添加学生信息成功！", Toast.LENGTH_SHORT).show()
                    id!!.setText("")
                    name!!.setText("")
                    age!!.setText("")
                    phone!!.setText("")
                    mFamaleRb!!.isChecked = false
                    mMaleRb!!.isChecked = false
                } else {
                    Toast.makeText(v.context, "写入失败，请重试！（可能原因为学号重复）", Toast.LENGTH_SHORT).show()
                }
            }
        }
        cancel.setOnClickListener { //清空输入框
            id!!.setText("")
            name!!.setText("")
            age!!.setText("")
            phone!!.setText("")
            mFamaleRb!!.isChecked = false
            mMaleRb!!.isChecked = false
        }
        return view
    }

    fun writeDatabase(student: Student): Boolean {
        val context: Context = MyApplication.getInstance()
        val stuDao = StudentDao(context)
        val user = UserDao(context)
        return if (stuDao.insertStudent(student)) {
            //插入学生信息后，设置默认登录密码为123456，账号为学号
            user.insertLog(student.id, "123456")
            true
        } else {
            false
        }
    }
}