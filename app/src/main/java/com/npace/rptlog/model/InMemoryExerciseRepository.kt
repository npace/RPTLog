package com.npace.rptlog.model

import com.npace.rptlog.model.entity.Exercise
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class InMemoryExerciseRepository : ExerciseRepository {
    private val exerciseListSubject = BehaviorSubject.createDefault<List<Exercise>>(ExerciseRepository.DEFAULT_EXERCISES.asList())

    override fun getAllExercises(): Observable<List<Exercise>> {
        return exerciseListSubject
    }
}