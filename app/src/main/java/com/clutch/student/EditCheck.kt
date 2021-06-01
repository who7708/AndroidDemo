package com.clutch.student

/**
 * Created by clutchyu on 2018/3/31.
 * 用于数据输入检验
 */
object EditCheck {
    var warning: String? = null
        private set

    @JvmStatic
    fun checkInt(input: String, label: String, min: Int, max: Int): Boolean {
        if (input == "") {
            warning = label + "不能为空！"
            return false
        } else {
            try {
                val value = input.toInt()
                if (value < min || value > max) {
                    warning = label + "应在" + min + "至" + max + "区间"
                    return false
                }
            } catch (e: RuntimeException) {
                warning = label + "格式错误！"
                return false
            }
        }
        return true
    }

    @JvmStatic
    fun checkString(input: String, label: String, size: Int): Boolean {
        if (input == "") {
            warning = label + "不能为空！"
            return false
        } else if (input.length > size) {
            warning = label + "长度错误！"
        }
        return true
    }
}