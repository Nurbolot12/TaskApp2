package com.example.taskapp.ui.home.new_task

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskapp.App
import com.example.taskapp.databinding.FragmentNewTaskBinding
import com.example.taskapp.ui.home.TaskModel

@Suppress("UNREACHABLE_CODE", "DEPRECATION")
class NewTaskFragment : Fragment() {

    private lateinit var binding: FragmentNewTaskBinding
    private var task: TaskModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewTaskBinding.inflate(inflater, container, false)
        initListeners()
        return binding.root
    }

    private fun initListeners() {
        getTask()
        checkTask()
        binding.animationView.visibility = View.GONE
    }

    private fun checkTask() {
        binding.btnSave.setOnClickListener {
            if (binding.etTitle.text.toString().isNotEmpty()) {
                if (task != null) {
                    updateTask()
                } else {
                    binding.animationView.visibility = View.VISIBLE
                    val handler = Handler()
                    handler.postDelayed(Runnable {
                        insertFun()
                    }, 1750)
                }
            } else {
                binding.etTitle.error = "Заполните"
            }
        }

    }

    private fun getTask() {
        arguments?.let {
            val value = it.getSerializable("update")
            if (value != null) {
                task = value as TaskModel
                binding.etTitle.setText(task?.title.toString())
                binding.etDesc.setText(task?.desc.toString())
            }
        }
    }

    private fun updateTask() {
        task?.title = binding.etTitle.text.toString()
        task?.desc = binding.etDesc.text.toString()

        App.database.TaskDao()?.update(
task
        )
        Toast.makeText(this.requireContext(), "Обновленно", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    private fun insertFun() {
        App.database.TaskDao()?.insert(
            TaskModel(
                title = binding.etTitle.text.toString(),
                desc = binding.etDesc.text.toString()
            )
        )
        Toast.makeText(this.requireContext(), "Добавленна", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }
}

