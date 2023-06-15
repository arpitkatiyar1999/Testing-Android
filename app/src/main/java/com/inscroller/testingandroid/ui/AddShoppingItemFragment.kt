package com.inscroller.testingandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.inscroller.testingandroid.databinding.FragmentAddShoppingItemkBinding

class AddShoppingItemFragment : Fragment() {
    private lateinit var viewModel: ShoppingViewModel
    private var _binding: FragmentAddShoppingItemkBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
        _binding = FragmentAddShoppingItemkBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}