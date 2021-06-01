package com.clutch.student.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import com.clutch.student.fragment.FirstFragment
import com.clutch.student.fragment.SecondFragment
import java.util.*

/**
 * Created by clutchyu on 2018/3/17.
 */
class ViewPagerAdapter(private val fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var mFragmentList: MutableList<Fragment>? = ArrayList()
    override fun getItemPosition(obj: Any): Int {
        if (obj is FirstFragment) {
            FirstFragment.update()
        } else if (obj is SecondFragment) {
            SecondFragment.update()
        }
        return super.getItemPosition(obj)
    }

    fun setFragments(fragments: MutableList<Fragment>?) {
        if (mFragmentList != null) {
            var ft: FragmentTransaction? = fm.beginTransaction()
            for (f in mFragmentList!!) {
                ft!!.remove(f)
            }
            ft!!.commit()
            ft = null
            fm.executePendingTransactions()
        }
        mFragmentList = fragments
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList!![position]
    }

    override fun getCount(): Int {
        return mFragmentList!!.size
    }

    fun addFragment(fragment: Fragment) {
        mFragmentList!!.add(fragment)
    }
}