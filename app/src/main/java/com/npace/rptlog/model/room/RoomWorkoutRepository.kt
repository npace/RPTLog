package com.npace.rptlog.model.room

import android.arch.persistence.room.Room
import android.content.Context
import com.npace.rptlog.model.WorkoutRepository
import com.npace.rptlog.model.entity.Workout
import com.npace.rptlog.model.entity.WorkoutEntry
import io.reactivex.Observable

/**
 * Created by lubo on 2/5/2018.
 */
class RoomWorkoutRepository(private val dao: WorkoutDao) : WorkoutRepository {
    companion object {
        fun create(context: Context) : RoomWorkoutRepository {
            val db = Room.databaseBuilder(context, WorkoutDatabase::class.java, "workout-db")
                    .build()
            return RoomWorkoutRepository(db.dao())
        }
    }

    override fun getAllWorkouts(): Observable<List<Workout>> {
        return dao.getAllWorkouts().toObservable()
    }

    override fun createWorkout(workoutEntries: List<WorkoutEntry>) {
        dao.insert(Workout(workoutEntries))
    }

    override fun deleteWorkout(workout: Workout) {
        dao.delete(workout)
    }
}