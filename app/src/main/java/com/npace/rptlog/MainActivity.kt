package com.npace.rptlog

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.npace.rptlog.di.DependencyInjection
import com.npace.rptlog.track.TrackWorkoutFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            DependencyInjection.configureGlobalScope()
            navigateTo(TrackWorkoutFragment(), false)
        }
    }

    @SuppressLint("CommitTransaction")
    fun navigateTo(fragment: Fragment, addToBackStack: Boolean = true) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.content, fragment)
            if (addToBackStack) {
                addToBackStack(null)
            }
            commit()
        }
    }
}
