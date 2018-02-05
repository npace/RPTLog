package com.npace.rptlog.track

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.TextView
import com.npace.rptlog.BaseFragment
import com.npace.rptlog.MainActivity
import com.npace.rptlog.R
import com.npace.rptlog.model.entity.WorkoutEntry
import com.npace.rptlog.toast
import kotlinx.android.synthetic.main.fragment_track.*
import kotlinx.android.synthetic.main.item_footer_add_entry.view.*
import kotlinx.android.synthetic.main.item_workout_entry.view.*

/**
 * Created by lubo on 2/3/2018.
 */

class TrackWorkoutFragment : BaseFragment(), AddEntryCallback {

    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: TrackWorkoutViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_track, container, false)
    }

    private lateinit var adapter: WorkoutEntryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        mainActivity.supportActionBar?.title = getString(R.string.track_workout)

        viewModel = ViewModelProviders.of(this).get(TrackWorkoutViewModel::class.java)

        adapter = WorkoutEntryAdapter(viewModel.getWorkoutEntries()) { mainActivity.goToAddExercise() }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.save, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save -> {
                saveWorkout()
                true
            }
            else -> false
        }
    }

    private fun saveWorkout() {
        viewModel.saveWorkout()
    }

    override fun addEntry(entry: WorkoutEntry) {
        viewModel.addEntry(entry)
        adapter.notifyItemInserted(adapter.itemCount)
    }
}

class WorkoutEntryAdapter(private val entries: List<WorkoutEntry>, private val onAddClicked: () -> Unit) : RecyclerView.Adapter<ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_WORKOUT = 0
        private const val VIEW_TYPE_FOOTER = 1
    }

    override fun getItemCount(): Int = entries.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            VIEW_TYPE_FOOTER
        } else {
            VIEW_TYPE_WORKOUT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_WORKOUT -> WorkoutViewHolder(inflater, parent)
            VIEW_TYPE_FOOTER -> {
                val holder = FooterViewHolder(inflater, parent)
                holder.button.setOnClickListener { onAddClicked() }
                return holder
            }
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (holder) {
            is WorkoutViewHolder -> {
                val stringBuilder = StringBuilder()
                val workoutEntry = entries[position]
                stringBuilder.append(workoutEntry.exercise.name)
                workoutEntry.sets.forEach {
                    stringBuilder.append("\n${it.reps} x ${it.weight}")
                }
                holder.textView.text = stringBuilder
            }
        }

    }
}

sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class WorkoutViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup) :
        ViewHolder(layoutInflater.inflate(R.layout.item_workout_entry, parent, false)) {
    var textView: TextView = itemView.textView
}

class FooterViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup) :
        ViewHolder(layoutInflater.inflate(R.layout.item_footer_add_entry, parent, false)) {
    var button: Button = itemView.buttonAdd
}