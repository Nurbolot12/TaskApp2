package com.example.taskapp.ui.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskapp.databinding.FragmentOnBoardPageBinding
import com.example.taskapp.utila.Preferences

@Suppress("DEPRECATION")
class OnBoardPageFragment(
    private var listenerSkip: () -> Unit,
    private var listenerNext: () -> Unit,
) : Fragment() {

    private lateinit var binding: FragmentOnBoardPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOnBoardPageBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initViews() {
        arguments.let {
            val data = it?.getSerializable("onBoard") as BoardModel
            binding.tvTitleBoard.text = data.title
            binding.tvDescBoard.text = data.description
            data.img?.let { it1 -> binding.imgBoard.setImageResource(it1) }
            binding.btnSkip.isVisible = data.isLast == false
            binding.btnNext.isVisible = data.isLast == false
            binding.btnStart.isVisible = data.isLast == true

            if (data.isLast == true) {
                binding.pageBoard.setBackgroundResource(data.bg)
            } else {
                binding.pageBoard.setBackgroundResource(data.bg)
            }
        }
    }

    private fun initListeners() {

        binding.btnNext.setOnClickListener {
            listenerNext.invoke()
        }

        binding.btnSkip.setOnClickListener {
            listenerSkip.invoke()
        }

        binding.btnStart.setOnClickListener {
            Preferences(requireContext()).setBoardingShowed(true)
            findNavController().navigateUp()
        }
    }
}