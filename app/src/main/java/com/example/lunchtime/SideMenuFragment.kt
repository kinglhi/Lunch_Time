package com.example.lunchtime

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lunchtime.databinding.FragmentSideMenuBinding
import com.example.lunchtime.model.OrderViewModel
import androidx.fragment.app.activityViewModels

class SideMenuFragment : Fragment(R.layout.fragment_side_menu) {
    private var binding: FragmentSideMenuBinding? = null

    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val navHostFragment = FragmentSideMenuBinding.inflate(inflater, container, false)
        binding = navHostFragment
        return navHostFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            nextButton.setOnClickListener {
                goToNextScreen()
            }
            cancelButton.setOnClickListener {
                cancelOrder()
            }
        }
    }

    private fun goToNextScreen() {
        findNavController().navigate(R.id.action_sideMenuFragment_to_accompanimentMenuFragment)
    }

    private fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_sideMenuFragment_to_startOrderFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}