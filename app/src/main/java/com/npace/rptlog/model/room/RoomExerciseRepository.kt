package com.npace.rptlog.model.room

import com.npace.rptlog.model.ExerciseRepository
import com.npace.rptlog.model.entity.Exercise
import io.reactivex.Observable

/**
 * Created by lubo on 2/6/2018.
 */
class RoomExerciseRepository(private val dao: WorkoutDao) : ExerciseRepository {
    override fun getAllExercises(): Observable<List<Exercise>> {
        return dao.getAllExercises().toObservable().doOnNext {
            if (it.isEmpty()) {
                dao.insertExercises(*ExerciseRepository.DEFAULT_EXERCISES)
            }
        }
    }

}