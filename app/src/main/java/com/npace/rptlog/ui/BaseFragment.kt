package com.npace.rptlog.ui

import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by lubo on 2/3/2018.
 */
open class BaseFragment : Fragment() {
    private val disposable = CompositeDisposable()

    override fun onStop() {
        disposable.clear()
        super.onStop()
    }

    fun addSubscription(subscription: Disposable) {
        disposable.add(subscription)
    }
}