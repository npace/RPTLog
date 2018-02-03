package com.npace.rptlog

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.npace.rptlog.track.TrackWorkoutFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.content, TrackWorkoutFragment())
                    .commit()
        }
    }
}
