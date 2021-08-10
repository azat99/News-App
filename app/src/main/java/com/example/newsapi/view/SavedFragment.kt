package com.example.newsapi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapi.R
import com.example.newsapi.databinding.FragmentSavedBinding
import com.example.newsapi.utils.BindingFragment
import com.example.newsapi.viewModel.NewsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SavedFragment : BindingFragment<FragmentSavedBinding>(FragmentSavedBinding::inflate) {

    private val viewModel:NewsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.setHasFixedSize(true)
            viewModel.getSavedNews().observe(viewLifecycleOwner, Observer {
                recyclerView.adapter = SavedNewsAdapter(it)
            })
        }

    }
}