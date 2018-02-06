package com.npace.rptlog.model.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.npace.rptlog.model.entity.Exercise
import com.npace.rptlog.model.entity.WeightSet
import com.npace.rptlog.model.entity.Workout
import com.npace.rptlog.model.entity.WorkoutEntry

/**
 * Created by lubo on 2/5/2018.
 */
@Database(entities = arrayOf(
        Workout::class,
        WorkoutEntry::class,
        WeightSet::class,
        Exercise::class), version = 1)
abstract class WorkoutDatabase : RoomDatabase() {
    abstract fun dao(): WorkoutDao
}