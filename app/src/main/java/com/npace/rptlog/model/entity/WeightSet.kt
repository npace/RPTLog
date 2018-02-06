package com.npace.rptlog.model.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Created by lubo on 2/4/2018.
 */
@Entity(foreignKeys = arrayOf(ForeignKey(entity = WorkoutEntry::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("workoutEntryId"),
        onDelete = ForeignKey.CASCADE)))
data class WeightSet(
        @ColumnInfo(name = "reps") var reps: Int = 0,
        @ColumnInfo(name = "weight") var weight: Float = 0f,
        @field:PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") var id: Long = 0,
        @ColumnInfo(name = "workoutEntryId") var workoutEntryId: Long = 0
)