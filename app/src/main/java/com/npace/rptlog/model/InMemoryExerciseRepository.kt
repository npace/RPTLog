package com.npace.rptlog.model

import com.npace.rptlog.model.entity.Exercise
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class InMemoryExerciseRepository : ExerciseRepository {
    private val exerciseListSubject = BehaviorSubject.createDefault<List<Exercise>>(listOf(
            Exercise("Deadlift"),
            Exercise("Squat"),
            Exercise("Bench Press"),
            Exercise("Overhead Press"),
            Exercise("Chin-up"),
            Exercise("Cable Row"),
            Exercise("Hanging Leg Raise"))
    )

    override fun getAllExercises(): Observable<List<Exercise>> {
        return exerciseListSubject
    }
}