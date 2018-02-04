package com.npace.rptlog.model

import com.npace.rptlog.model.entity.Exercise
import io.reactivex.Observable

/**
 * Created by lubo on 2/4/2018.
 */
interface ExerciseRepository {
    fun getAllExercises() : Observable<List<Exercise>>
}