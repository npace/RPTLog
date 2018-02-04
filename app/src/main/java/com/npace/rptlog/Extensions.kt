package com.npace.rptlog

import android.content.Context
import android.support.v4.app.Fragment
import android.widget.Toast

/**
 * Created by lubo on 2/4/2018.
 */

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.toast(resId: Int) {
    toast(getString(resId))
}

fun Fragment.toast(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(resId: Int) {
    toast(getString(resId))
}