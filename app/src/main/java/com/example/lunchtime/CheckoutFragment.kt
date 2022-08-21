package com.example.lunchtime

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.lunchtime.databinding.FragmentCheckoutBinding
import com.example.lunchtime.model.OrderViewModel
import com.google.android.material.snackbar.Snackbar

class CheckoutFragment : Fragment(R.layout.fragment_checkout) {
    private var binding: FragmentCheckoutBinding? = null
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val navHostFragment = FragmentCheckoutBinding.inflate(inflater, container, false)
        binding = navHostFragment
        return navHostFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            submitButton.setOnClickListener {
                Snackbar.make(view, "Order Submitted!", Snackbar.LENGTH_LONG).show()
                cancelOrder()
            }
            cancelButton.setOnClickListener {
                cancelOrder()
            }
        }
    }

    private fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_checkoutFragment_to_startOrderFragment2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}