package com.npace.rptlog.model

import com.npace.rptlog.model.entity.WorkoutEntry
import com.npace.rptlog.model.entity.Workout
import io.reactivex.Observable

/**
 * Created by lubo on 2/4/2018.
 */
interface WorkoutRepository {
    fun getAllWorkouts() : Observable<List<Workout>>

    fun createWorkout(workoutEntries: List<WorkoutEntry>)
    fun deleteWorkout(workout: Workout)
}