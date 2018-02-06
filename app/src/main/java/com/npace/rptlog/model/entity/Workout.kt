package com.npace.rptlog.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

/**
 * Created by lubo on 2/4/2018.
 */
@Entity
data class Workout(
        @Ignore var workoutEntries: List<WorkoutEntry> = emptyList(),
        @PrimaryKey(autoGenerate = true) var id: Long = 0
)
