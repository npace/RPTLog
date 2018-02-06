package com.npace.rptlog.ui.track

import android.arch.lifecycle.ViewModel
import com.npace.rptlog.di.DependencyInjection
import com.npace.rptlog.model.WorkoutRepository
import com.npace.rptlog.model.entity.WorkoutEntry
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by lubo on 2/3/2018.
 */
class TrackWorkoutViewModel : ViewModel() {
    @Inject
    lateinit var workoutRepository: WorkoutRepository

    private val workoutEntries = mutableListOf<WorkoutEntry>()

    init {
        DependencyInjection.injectGlobalScope(this)
    }

    fun getWorkoutEntries(): List<WorkoutEntry> = workoutEntries

    fun addEntry(entry: WorkoutEntry) {
        workoutEntries.add(entry)
    }

    fun saveWorkout() {
        Single.fromCallable { workoutRepository.createWorkout(workoutEntries) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

}