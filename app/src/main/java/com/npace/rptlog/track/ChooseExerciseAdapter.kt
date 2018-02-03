package com.npace.rptlog.track

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.npace.rptlog.R
import com.npace.rptlog.model.Exercise
import kotlinx.android.synthetic.main.item_add_exercise.view.*

/**
 * Created by lubo on 2/3/2018.
 */
class ChooseExerciseAdapter(private val listener: (Exercise) -> Unit) : RecyclerView.Adapter<ChooseExerciseAdapter.ViewHolder>() {
    private var items : List<Exercise> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_add_exercise, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.textView.text = item.name
        holder.itemView.setOnClickListener { listener(item) }

    }

    override fun getItemCount(): Int = items.size

    fun setData(items: List<Exercise>) {
        this.items = items
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView : TextView = itemView.textView
    }
}