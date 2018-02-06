package com.npace.rptlog.model.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.npace.rptlog.model.entity.Exercise
import com.npace.rptlog.model.entity.Workout
import io.reactivex.Flowable

/**
 * Created by lubo on 2/5/2018.
 */
@Dao
interface WorkoutDao {

    @Query("select * from Workout")
    fun getAllWorkouts() : Flowable<List<Workout>>

    @Insert
    fun insert(workout: Workout)

    @Delete
    fun delete(workout: Workout)

    @Query("select * from Exercise")
    fun getAllExercises() : Flowable<List<Exercise>>

    @Insert
    fun insertExercises(vararg exercises: Exercise)
}