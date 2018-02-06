package com.npace.rptlog.di

import android.arch.persistence.room.Room
import android.content.Context
import com.npace.rptlog.model.ExerciseRepository
import com.npace.rptlog.model.WorkoutRepository
import com.npace.rptlog.model.room.RoomExerciseRepository
import com.npace.rptlog.model.room.RoomWorkoutRepository
import com.npace.rptlog.model.room.WorkoutDatabase
import toothpick.Toothpick
import toothpick.config.Module

/**
 * Created by lubo on 2/4/2018.
 */
object DependencyInjection {
    private const val SCOPE_GLOBAL = "global"

    fun configureGlobalScope(context: Context) {
        val scope = Toothpick.openScope(SCOPE_GLOBAL)
        scope.installModules(object : Module() {
            init{
                val db = Room.databaseBuilder(context, WorkoutDatabase::class.java, "workout-db").build()
                val dao = db.dao()
                bind(WorkoutRepository::class.java).toInstance(RoomWorkoutRepository(dao))
                bind(ExerciseRepository::class.java).toInstance(RoomExerciseRepository(dao))
            }
        })
    }

    fun injectGlobalScope(target: Any) {
        Toothpick.inject(target, Toothpick.openScope(SCOPE_GLOBAL))
    }
}