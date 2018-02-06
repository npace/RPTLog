package com.npace.rptlog.model.entity

import android.arch.persistence.room.*

/**
 * Created by lubo on 2/4/2018.
 */
@Entity(foreignKeys = arrayOf(
        ForeignKey(entity = Workout::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("workoutId"),
                onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Exercise::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("exerciseId"),
                onDelete = ForeignKey.CASCADE)))
data class WorkoutEntry(
        @Ignore var exercise: Exercise = Exercise(-1, "none"),
        @Ignore var sets: List<WeightSet> = emptyList(),
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") var id: Long = 0,
        @ColumnInfo(name = "workoutId") var workoutId: Long = 0,
        @ColumnInfo(name = "exerciseId") var exerciseId: Long = 0
)