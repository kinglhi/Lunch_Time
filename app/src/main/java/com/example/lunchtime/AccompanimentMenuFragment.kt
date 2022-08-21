package com.example.lunchtime

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.lunchtime.databinding.FragmentAccompanimentMenuBinding
import com.example.lunchtime.model.OrderViewModel
import com.example.lunchtime.EntreeMenuFragment

class AccompanimentMenuFragment : Fragment(R.layout.fragment_accompaniment_menu) {
    private var binding: FragmentAccompanimentMenuBinding? = null
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val navHostFragment = FragmentAccompanimentMenuBinding.inflate(inflater, container, false)
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
        findNavController().navigate(R.id.action_accompanimentMenuFragment_to_checkoutFragment)
    }

    private fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_accompanimentMenuFragment_to_startOrderFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}