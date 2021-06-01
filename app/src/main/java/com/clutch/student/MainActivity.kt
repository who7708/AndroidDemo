package com.clutch.student

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.clutch.student.adapter.ViewPagerAdapter
import com.clutch.student.dao.MyDatabaseHelper
import com.clutch.student.fragment.FirstFragment
import com.clutch.student.fragment.ManagerFirstFragment
import com.clutch.student.fragment.ManagerSecondFragment
import com.clutch.student.fragment.ManagerThirdFragment
import com.clutch.student.fragment.SecondFragment
import com.clutch.student.fragment.ThirdFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Created by clutchyu on 2018/3/17.
 * 主界面，包含三个Fragment,以学生身份和管理员身份登入系统时对应界面不同
 */
class MainActivity : AppCompatActivity() {
    //dbHelper
    private var dbHelper: MyDatabaseHelper? = null
    var bottomNavigationView: BottomNavigationView? = null

    //This is our viewPager
    private var viewPager: ViewPager? = null
    private val adapter: ViewPagerAdapter? = null

    //Fragments
    var firstFragment: FirstFragment? = null
    var secondFragment: SecondFragment? = null
    var thirdFragment: ThirdFragment? = null
    var managerFirstFragment: ManagerFirstFragment? = null
    var managerSecondFragment: ManagerSecondFragment? = null
    var managerThirdFragment: ManagerThirdFragment? = null
    var prevMenuItem: MenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = intent
        studentId = intent.getIntExtra("id", -1)
        //创建或打开数据库
        dbHelper = MyDatabaseHelper(this)
        dbHelper!!.writableDatabase
        //Initializing viewPager
        viewPager = findViewById<View>(R.id.viewpager) as ViewPager

        //Initializing the bottomNavigationView
        bottomNavigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationView!!.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_first -> viewPager!!.currentItem = 0
                R.id.action_second -> viewPager!!.currentItem = 1
                R.id.action_third -> viewPager!!.currentItem = 2
            }
            false
        }
        viewPager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                if (prevMenuItem != null) {
                    prevMenuItem!!.isChecked = false
                } else {
                    bottomNavigationView!!.menu.getItem(0).isChecked = false
                }
                Log.d("page", "onPageSelected: $position")
                bottomNavigationView!!.menu.getItem(position).isChecked = true
                prevMenuItem = bottomNavigationView!!.menu.getItem(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        setupViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.switch_item -> {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.quit_item -> finish()
            else -> {
            }
        }
        return true
    }

    fun context(): Context {
        return this
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        if (studentId == 0) {
            managerFirstFragment = ManagerFirstFragment()
            managerSecondFragment = ManagerSecondFragment()
            managerThirdFragment = ManagerThirdFragment()
            adapter.addFragment(managerFirstFragment!!)
            adapter.addFragment(managerSecondFragment!!)
            adapter.addFragment(managerThirdFragment!!)
        } else {
            firstFragment = FirstFragment()
            secondFragment = SecondFragment()
            thirdFragment = ThirdFragment()
            adapter.addFragment(firstFragment!!)
            adapter.addFragment(secondFragment!!)
            adapter.addFragment(thirdFragment!!)
        }
        viewPager!!.adapter = adapter
    }

    companion object {
        var studentId = 0
            private set
    }
}