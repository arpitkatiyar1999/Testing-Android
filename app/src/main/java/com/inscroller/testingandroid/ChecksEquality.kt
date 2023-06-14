package com.inscroller.testingandroid

import android.content.Context

class ChecksEquality {
    fun isEqual(context: Context, stringRes: Int, string: String):Boolean {
        return context.getString(stringRes) == string
    }
}