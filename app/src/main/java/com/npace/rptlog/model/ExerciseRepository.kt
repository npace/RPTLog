package com.npace.rptlog.model

import com.npace.rptlog.model.entity.Exercise
import io.reactivex.Observable

/**
 * Created by lubo on 2/4/2018.
 */
interface ExerciseRepository {
    companion object {
        val DEFAULT_EXERCISES = arrayOf(
                Exercise(name="Deadlift"),
                Exercise(name="Squat"),
                Exercise(name="Bench Press"),
                Exercise(name="Overhead Press"),
                Exercise(name="Chin-up"),
                Exercise(name="Cable Row"),
                Exercise(name="Hanging Leg Raise"))
    }

    fun getAllExercises() : Observable<List<Exercise>>
}