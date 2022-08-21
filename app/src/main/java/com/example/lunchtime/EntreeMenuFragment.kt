package com.example.lunchtime

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.lunchtime.databinding.FragmentEntreeMenuBinding
import com.example.lunchtime.model.OrderViewModel

class EntreeMenuFragment : Fragment(R.layout.fragment_entree_menu) {
    private var binding: FragmentEntreeMenuBinding? = null
    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val navHostFragment = FragmentEntreeMenuBinding.inflate(inflater, container, false)
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
        findNavController().navigate(R.id.action_entreeMenuFragment_to_sideMenuFragment)
    }

    private fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_entreeMenuFragment_to_startOrderFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}