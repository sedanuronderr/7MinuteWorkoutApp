package com.seda.a7minuteworkoutapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.seda.a7minuteworkoutapp.R
import com.seda.a7minuteworkoutapp.databinding.ItemExerciseStatusBinding
import com.seda.a7minuteworkoutapp.model.ExerciseModel

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemExerciseStatusBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model: ExerciseModel = items[position]

        holder.tvItem.text = model.id.toString()
        when{
            model.isSelected->{
                holder.tvItem.text = ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_thin_color_accent_border).toString()
                holder.tvItem.text = model.id.toString()
                holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            model.isCompleted ->{
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_accent_background)
                holder.tvItem.text = model.id.toString()
                holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else->{
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_gray_background)
                holder.tvItem.text = model.id.toString()
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(binding: ItemExerciseStatusBinding) : RecyclerView.ViewHolder(binding.root) {
        // Holds the TextView that will add each item to
        val tvItem = binding.tvItem
    }
}
