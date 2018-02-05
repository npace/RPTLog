package com.npace.rptlog.track

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ArrayAdapter
import android.widget.TextView
import com.npace.rptlog.BaseFragment
import com.npace.rptlog.R
import com.npace.rptlog.model.entity.Exercise
import com.npace.rptlog.model.entity.WeightSet
import com.npace.rptlog.toast
import kotlinx.android.synthetic.main.fragment_add_exercise.*

/**
 * Created by lubo on 2/4/2018.
 */
class AddExerciseFragment : BaseFragment() {
    private lateinit var viewModel: AddExerciseViewModel
    private lateinit var adapter: ArrayAdapter<Exercise>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddExerciseViewModel::class.java)

        adapter = Adapter(context)
        spinner.setAdapter(adapter)
        spinner.setOnItemClickListener { adapterView, view, position, id ->
            val exercise = adapter.getItem(position)
            viewModel.setExercise(exercise)
            spinner.setText(exercise.name, false)
        }

        buttonAdd.setOnClickListener { addNewSet() }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                viewModel.save()
                true
            }
            else -> false
        }
    }

    override fun onStart() {
        super.onStart()
        addSubscription(viewModel.exercisesStream().subscribe {
            adapter.apply {
                clear()
                addAll(it)
                notifyDataSetChanged()
            }
        })

        addSubscription(viewModel.errorStream().subscribe {
            val stringId = when (it) {
                is TooManySets -> R.string.too_many_sets
                is SelectExercise -> R.string.select_exercise
                is InvalidSetsOrReps -> R.string.invalid_sets_or_reps
            }
            toast(stringId)
        })

        addSubscription(viewModel.successStream().subscribe {
            (context as AddEntryCallback).addEntry(it)
        })

        viewModel.getWorkoutSets().forEach {
            addSetViewAtLastPosition(it)
        }
    }

    private fun addNewSet() {
        viewModel.addNewSet()?.let {
            addSetViewAtLastPosition(it)
        }
    }

    private fun addSetViewAtLastPosition(weightSet: WeightSet) {
        val lastPosition = linearLayout.childCount
        addSetView(lastPosition, weightSet)
    }

    private fun addSetView(position: Int, weightSet: WeightSet) {
        val setLayout = SetLayout(context!!)
        setLayout.apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)

            onClickCopyListener = {
                val index = linearLayout.indexOfChild(this)
                viewModel.copySet(index)?.let {
                    addSetView(index + 1, it)
                }
            }
            onClickDeleteListener = {
                val index = linearLayout.indexOfChild(this)
                viewModel.deleteSet(index)
                linearLayout.removeViewAt(index)
            }
            onWeightChangedListener = { viewModel.updateWeight(position, it) }
            onRepsChangedListener = { viewModel.updateReps(position, it) }

            displaySet(weightSet)
        }

        linearLayout.addView(setLayout, position)
    }
}

class Adapter(context: Context?) : ArrayAdapter<Exercise>(context, android.R.layout.simple_dropdown_item_1line) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.text = getItem(position).name
        return view
    }
}