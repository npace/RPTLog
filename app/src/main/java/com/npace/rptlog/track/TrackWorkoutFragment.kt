package com.npace.rptlog.track

import android.arch.lifecycle.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.Toast
import com.npace.rptlog.BaseFragment
import com.npace.rptlog.R
import com.npace.rptlog.model.Exercise
import kotlinx.android.synthetic.main.fragment_track.*

/**
 * Created by lubo on 2/3/2018.
 */

class TrackWorkoutFragment : BaseFragment() {

    private lateinit var viewModel : TrackWorkoutViewModel
    private lateinit var adapter : ChooseExerciseAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_track, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TrackWorkoutViewModel::class.java)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.track_workout)
        setHasOptionsMenu(true)

        adapter = ChooseExerciseAdapter(onExerciseSelected)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.track_workout, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add) {
            addExercise()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addExercise() {
        drawerLayout.openDrawer(Gravity.END)
    }

    private val onExerciseSelected : (Exercise) -> Unit = {
        Toast.makeText(context, it.name, Toast.LENGTH_LONG).show()
        drawerLayout.closeDrawer(Gravity.END)
    }

    override fun onStart() {
        super.onStart()
        addSubscription(viewModel.exerciseListStream()
                .subscribe({
                    adapter.setData(it)
                }))
    }
}
