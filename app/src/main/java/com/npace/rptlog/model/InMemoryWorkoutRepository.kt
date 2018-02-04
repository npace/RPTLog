package com.npace.rptlog.model

import com.npace.rptlog.model.entity.WorkoutEntry
import com.npace.rptlog.model.entity.Workout
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by lubo on 2/4/2018.
 */
class InMemoryWorkoutRepository : WorkoutRepository {
    private val list = listOf<Workout>()
    private val workouts = BehaviorSubject.createDefault(list)

    override fun getAllWorkouts(): Observable<List<Workout>> {
        return workouts
    }

    override fun createWorkout(workoutEntries: List<WorkoutEntry>) {
        list.toMutableList().apply {
            add(Workout(workoutEntries))
            workouts.onNext(this)
        }
    }
}