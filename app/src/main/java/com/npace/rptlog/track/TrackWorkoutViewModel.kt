package com.npace.rptlog.track

import android.arch.lifecycle.ViewModel
import com.npace.rptlog.model.Exercise
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by lubo on 2/3/2018.
 */
class TrackWorkoutViewModel : ViewModel() {
    private val exerciseListSubject = BehaviorSubject.create<List<Exercise>>()
    fun exerciseListStream() : Observable<List<Exercise>> = exerciseListSubject

    init {
        Observable.just(listOf(
                Exercise("Deadlift"),
                Exercise("Squat"),
                Exercise("Bench Press"),
                Exercise("Overhead Press"),
                Exercise("Chin-up"),
                Exercise("Cable Row"),
                Exercise("Hanging Leg Raise")))
                .subscribe({
                    exerciseListSubject.onNext(it)
                }, {

                })
    }

}