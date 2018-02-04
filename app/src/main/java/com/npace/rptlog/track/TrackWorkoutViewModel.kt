package com.npace.rptlog.track

import android.arch.lifecycle.ViewModel
import com.npace.rptlog.di.DependencyInjection
import com.npace.rptlog.model.ExerciseRepository
import com.npace.rptlog.model.entity.WeightSet
import com.npace.rptlog.model.entity.WorkoutEntry
import javax.inject.Inject

/**
 * Created by lubo on 2/3/2018.
 */
class TrackWorkoutViewModel : ViewModel() {
    @Inject
    lateinit var exerciseRepository: ExerciseRepository

    private val workoutEntries = mutableListOf<WorkoutEntry>()

    init {
        DependencyInjection.injectGlobalScope(this)
        exerciseRepository.getAllExercises()
                .take(1)
                .subscribe { exercises ->
                    (0..4).forEach {
                        exercises.forEach {
                            workoutEntries.add(WorkoutEntry(it, (1..3).map {
                                WeightSet(4 * it, 200 / it.toFloat())
                            }))
                        }
                    }
                }
    }

    fun getWorkoutEntries(): List<WorkoutEntry> = workoutEntries

    fun addEntry(entry: WorkoutEntry) {
        workoutEntries.add(entry)
    }

}