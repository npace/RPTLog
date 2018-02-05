package com.npace.rptlog.ui.history

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.npace.rptlog.R
import com.npace.rptlog.model.entity.Workout
import com.npace.rptlog.ui.BaseFragment
import com.npace.rptlog.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.item_workout_entry.view.*

/**
 * Created by lubo on 2/5/2018.
 */
class HistoryFragment : BaseFragment() {
    lateinit var viewModel: HistoryViewModel
    lateinit var adapter: HistoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = HistoryAdapter {
            viewModel.delete(it)
        }
        recyclerView.adapter = adapter

        fab.setOnClickListener {
            (activity as MainActivity).goToTrackWorkout()
        }
    }

    override fun onStart() {
        super.onStart()
        addSubscription(viewModel.historyStream().subscribe {
            adapter.setData(it)
        })
    }
}

class HistoryAdapter(private val onClick: (Workout) -> Unit) : RecyclerView.Adapter<HistoryViewHolder>() {
    private var entries: List<Workout> = emptyList()

    fun setData(data: List<Workout>) {
        entries = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = entries.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HistoryViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.textView.text = entries[position].toString()
        holder.itemView.setOnClickListener {
            val index = holder.adapterPosition
            if (index != RecyclerView.NO_POSITION) {
                onClick(entries[index])
            }
        }
    }
}

class HistoryViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_workout_entry, parent, false)) {
    var textView: TextView = itemView.textView
}