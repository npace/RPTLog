package com.npace.rptlog.di

import com.npace.rptlog.model.ExerciseRepository
import com.npace.rptlog.model.InMemoryExerciseRepository
import com.npace.rptlog.model.InMemoryWorkoutRepository
import com.npace.rptlog.model.WorkoutRepository
import toothpick.Toothpick
import toothpick.config.Module

/**
 * Created by lubo on 2/4/2018.
 */
object DependencyInjection {
    private const val SCOPE_GLOBAL = "global"

    fun configureGlobalScope() {
        val scope = Toothpick.openScope(SCOPE_GLOBAL)
        scope.installModules(object : Module() {
            init{
                bind(WorkoutRepository::class.java).toInstance(InMemoryWorkoutRepository())
                bind(ExerciseRepository::class.java).toInstance(InMemoryExerciseRepository())
            }
        })
    }

    fun injectGlobalScope(target: Any) {
        Toothpick.inject(target, Toothpick.openScope(SCOPE_GLOBAL))
    }
}