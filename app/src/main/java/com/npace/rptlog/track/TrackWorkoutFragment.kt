package com.npace.rptlog.track

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.npace.rptlog.BaseFragment
import com.npace.rptlog.MainActivity
import com.npace.rptlog.R
import kotlinx.android.synthetic.main.fragment_track.*

/**
 * Created by lubo on 2/3/2018.
 */

class TrackWorkoutFragment : BaseFragment() {

    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_track, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.supportActionBar?.title = getString(R.string.track_workout)

        buttonAdd.setOnClickListener {
            addExercise()
        }
    }

    private fun addExercise() {
        mainActivity.navigateTo(AddExerciseFragment())
    }
}
