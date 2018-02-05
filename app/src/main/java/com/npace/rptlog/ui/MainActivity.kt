package com.npace.rptlog.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.npace.rptlog.R
import com.npace.rptlog.di.DependencyInjection
import com.npace.rptlog.model.entity.WorkoutEntry
import com.npace.rptlog.ui.track.AddEntryCallback
import com.npace.rptlog.ui.track.AddExerciseFragment
import com.npace.rptlog.ui.track.TrackWorkoutFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AddEntryCallback {
    companion object {
        private const val TAG_TRACK_WORKOUT = "track"
        private const val TAG_ADD_EXERCISE = "add exercise"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            DependencyInjection.configureGlobalScope()
            navigateTo(TrackWorkoutFragment(), TAG_TRACK_WORKOUT, false)
        }
    }

    fun goToAddExercise() {
        navigateTo(AddExerciseFragment(), TAG_ADD_EXERCISE)
    }

    @SuppressLint("CommitTransaction")
    private fun navigateTo(fragment: Fragment, tag: String, addToBackStack: Boolean = true) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.content, fragment, tag)
            if (addToBackStack) {
                addToBackStack(tag)
            }
            commit()
        }
    }

    override fun addEntry(entry: WorkoutEntry) {
        supportFragmentManager.popBackStackImmediate()
        val trackWorkoutFragment = supportFragmentManager.findFragmentByTag(TAG_TRACK_WORKOUT) as TrackWorkoutFragment
        trackWorkoutFragment.addEntry(entry)
    }
}
