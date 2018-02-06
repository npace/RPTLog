package com.npace.rptlog.model

import com.npace.rptlog.model.entity.Workout
import com.npace.rptlog.model.entity.WorkoutEntry
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by lubo on 2/4/2018.
 */
class InMemoryWorkoutRepository : WorkoutRepository {
    private val workouts = BehaviorSubject.createDefault(listOf<Workout>())
    override fun getAllWorkouts(): Observable<List<Workout>> {
        return workouts
    }

    override fun createWorkout(workoutEntries: List<WorkoutEntry>) {
        workouts.value.toMutableList().apply {
            add(Workout(workoutEntries))
            workouts.onNext(this)
        }
    }

    override fun deleteWorkout(workout: Workout) {
        workouts.value.toMutableList().apply {
            remove(workout)
            workouts.onNext(this)
        }
    }
}
