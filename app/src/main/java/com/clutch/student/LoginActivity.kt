package com.clutch.student

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.clutch.student.dao.MyDatabaseHelper
import com.clutch.student.dao.UserDao

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val dbHelper = MyDatabaseHelper(this)
        dbHelper.writableDatabase
        val account = findViewById<EditText>(R.id.account)
        val password = findViewById<EditText>(R.id.password)
        val log = findViewById<Button>(R.id.login)
        val sign = findViewById<Button>(R.id.signin)
        val user = UserDao(this)
        log.setOnClickListener {
            val idStr: String = account.text.toString()
            // int id = Integer.parseInt(id_str);
            val passwd: String = password.getText().toString()
            if (!EditCheck.checkInt(idStr, "账号", 0, 99999)) {
                showNormalDialog(EditCheck.warning)
            } else if (!EditCheck.checkString(passwd, "密码", 20)) {
                showNormalDialog(EditCheck.warning)
            } else if (user.check(idStr.toInt(), passwd)) {              //账号匹配成功,进入MainActivity
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.putExtra("id", idStr.toInt())
                startActivity(intent)
                finish()
            } else {                              //弹出错误提示框
                val word = "账号或密码错误！"
                showNormalDialog(word)
            }
        }
        sign.setOnClickListener {
            val intent = Intent(this@LoginActivity, ModifyPasswdActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showNormalDialog(word: String?) {
        val normalDialog = AlertDialog.Builder(this@LoginActivity)
        normalDialog.setTitle("提示")
        normalDialog.setMessage(word)
        normalDialog.setPositiveButton("确定") { _, _ ->
            normalDialog.setCancelable(true)
            //...To-do
        }

        // 显示
        normalDialog.show()
    }
}