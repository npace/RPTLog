package com.npace.rptlog.track

import com.npace.rptlog.model.entity.WorkoutEntry

/**
 * Created by lubo on 2/4/2018.
 */
interface AddEntryCallback {
    fun addEntry(entry: WorkoutEntry)
}