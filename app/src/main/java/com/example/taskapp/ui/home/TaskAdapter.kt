package com.example.taskapp.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.R
import com.example.taskapp.databinding.ItemTaskBinding

class TaskAdapter(
    private var onLongClick: (Int) -> Unit,
    private var onUpdateClick: (TaskModel) -> Unit,
) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private var arrayTask = arrayListOf<TaskModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun addTasks(list: List<TaskModel>) {
        arrayTask.clear()
        arrayTask.addAll(list)
        notifyDataSetChanged()
    }

    fun getTask(position: Int): TaskModel {
        return arrayTask[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.OnBind(
            arrayTask[position]
        )
        if (position % 2 == 0) {
            holder.itemView.setBackgroundResource(R.drawable.black_zebra)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.white_zebra)
        }
    }

    override fun getItemCount(): Int {
        return arrayTask.size
    }
    fun deleteItemsAndNotifyAdapter(pos: Int) {
        arrayTask.removeAt(pos)
        notifyItemRemoved(pos)
    }

    inner class ViewHolder(private var binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun OnBind(taskMode: TaskModel) {
            binding.tvTitle.text = taskMode.title
            binding.tvDesc.text = taskMode.desc

            if (adapterPosition % 2 == 0) {
                binding.tvTitle.setTextColor(Color.WHITE)
                binding.tvDesc.setTextColor(Color.WHITE)
                binding.lineRv.setBackgroundColor(Color.WHITE)
            } else {
                binding.tvTitle.setTextColor(Color.BLACK)
                binding.tvDesc.setTextColor(Color.BLACK)
                binding.lineRv.setBackgroundColor(Color.BLACK)
            }

            itemView.setOnLongClickListener {
                Log.e("ololo", "OnBind: $adapterPosition")
                onLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
            itemView.setOnClickListener{
                onUpdateClick(taskMode)
            }
        }
    }
}
