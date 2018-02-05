package com.npace.rptlog.ui.history

import android.arch.lifecycle.ViewModel
import com.npace.rptlog.di.DependencyInjection
import com.npace.rptlog.model.WorkoutRepository
import com.npace.rptlog.model.entity.Workout
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by lubo on 2/5/2018.
 */
class HistoryViewModel : ViewModel() {
    init {
        DependencyInjection.injectGlobalScope(this)
    }

    @Inject
    lateinit var workoutRepository: WorkoutRepository

    fun historyStream(): Observable<List<Workout>> = workoutRepository.getAllWorkouts()

    fun delete(workout: Workout) {
        workoutRepository.deleteWorkout(workout)
    }
}