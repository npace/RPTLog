package com.npace.rptlog.track

import android.arch.lifecycle.ViewModel
import com.npace.rptlog.di.DependencyInjection
import com.npace.rptlog.model.ExerciseRepository
import com.npace.rptlog.model.entity.Exercise
import com.npace.rptlog.model.entity.WeightSet
import com.npace.rptlog.model.entity.WorkoutEntry
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by lubo on 2/4/2018.
 */
class AddExerciseViewModel : ViewModel() {
    companion object {
        private const val MAX_SETS = 5
    }

    private val workoutSets = mutableListOf(WeightSet(0, 0f))
    private var exercise: Exercise? = null
    private val errorSubject = PublishSubject.create<AddExerciseError>()
    private val successSubject = BehaviorSubject.create<WorkoutEntry>()

    @Inject
    lateinit var exerciseRepository: ExerciseRepository

    init {
        DependencyInjection.injectGlobalScope(this)
    }

    fun exercisesStream(): Observable<List<Exercise>> = exerciseRepository.getAllExercises()
    fun errorStream(): Observable<AddExerciseError> = errorSubject
    fun successStream(): Observable<WorkoutEntry> = successSubject
    fun getWorkoutSets(): List<WeightSet> = workoutSets

    fun save() {
        if (exercise == null) {
            errorSubject.onNext(SelectExercise())
            return
        }

        val invalidSet = workoutSets.firstOrNull { it.reps == 0 || it.weight == 0f }
        if (invalidSet != null) {
            errorSubject.onNext(InvalidSetsOrReps())
            return
        }

        successSubject.onNext(WorkoutEntry(exercise!!, workoutSets))
    }

    private var canAddMoreSets: Boolean = true
        get() = workoutSets.size < MAX_SETS

    fun setExercise(exercise: Exercise) {
        this.exercise = exercise
    }

    fun addNewSet(): WeightSet? {
        return withSizeCheck {
            val set = WeightSet(0, 0f)
            workoutSets.add(set)
            set
        }
    }

    fun copySet(position: Int): WeightSet? {
        return withSizeCheck {
            val original = workoutSets[position]
            val copy = original.copy()
            workoutSets.add(position, copy)
            return copy
        }
    }

    private inline fun withSizeCheck(body: () -> WeightSet): WeightSet? {
        return if (canAddMoreSets) {
            body()
        } else {
            errorSubject.onNext(TooManySets())
            null
        }
    }

    fun deleteSet(position: Int) {
        workoutSets.removeAt(position)
    }

    fun updateWeight(position: Int, weight: Float) {
        workoutSets[position].weight = weight
    }

    fun updateReps(position: Int, reps: Int) {
        workoutSets[position].reps = reps
    }
}

sealed class AddExerciseError
class SelectExercise : AddExerciseError()
class InvalidSetsOrReps : AddExerciseError()
class TooManySets : AddExerciseError()