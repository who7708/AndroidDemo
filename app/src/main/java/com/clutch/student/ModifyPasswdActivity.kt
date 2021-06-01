package com.clutch.student

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.clutch.student.dao.UserDao
import com.clutch.student.entity.User

class ModifyPasswdActivity : AppCompatActivity() {
    private var account: EditText? = null
    private var passwdOld: EditText? = null
    private var passwdNew: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_passwd)
        account = findViewById<View>(R.id.account_m) as EditText
        passwdOld = findViewById<View>(R.id.passwd_o) as EditText
        passwdNew = findViewById<View>(R.id.passwd_n) as EditText
        val commit = findViewById<View>(R.id.commit_m) as Button
        val cancel = findViewById<View>(R.id.cancel_m) as Button
        commit.setOnClickListener {
            val word: String
            if (!EditCheck.checkInt(account!!.text.toString(), "账号", 10000, 99999)) {
                showNormalDialog(EditCheck.warning)
            } else if (!EditCheck.checkString(passwdOld!!.text.toString(), "旧密码", 20)) {
                showNormalDialog(EditCheck.warning)
            } else if (!EditCheck.checkString(passwdNew!!.text.toString(), "新密码", 20)) {
                showNormalDialog(EditCheck.warning)
            } else {
                val user = User(account!!.text.toString().toInt(), passwdOld!!.text.toString())
                val password = passwdNew!!.text.toString()
                if (writeDatabase(user, password)) {
                    word = "修改密码成功！"
                    showNormalDialog(word)
                    //延时1s
                    val handler = Handler()
                    handler.postDelayed({ finish() }, 1000)
                } else {
                    word = "修改失败！账号与原密码不匹配！请重试："
                    showNormalDialog(word)
                }
            }
        }
        cancel.setOnClickListener { finish() }
    }

    private fun showNormalDialog(word: String?) {
        val normalDialog = AlertDialog.Builder(this@ModifyPasswdActivity)
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

    private fun writeDatabase(user: User, passwd: String): Boolean {
        val userDao = UserDao(MyApplication.getInstance())
        return userDao.modifyPasswd(user, passwd)
    }
}