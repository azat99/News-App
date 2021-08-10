package com.example.newsapi.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.newsapi.databinding.FragmentDetailsBinding
import com.example.newsapi.model.entity.SaveNews
import com.example.newsapi.utils.BindingFragment
import com.example.newsapi.viewModel.DetailsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsFragment : BindingFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val viewModel: DetailsViewModel by viewModel()
    var position = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = DetailsFragmentArgs.fromBundle(it).position
        }

        binding.run {

            viewModel.getDetailsInfo().observe(viewLifecycleOwner, Observer {
                titleTv.text = it[position].title
                authorTv.text = it[position].author
                Glide.with(this@DetailsFragment).load(it[position].urlToImage)
                    .into(postIv)
                contentTv.text = it[position].content
                saveIv.setOnClickListener { it2->
                    viewModel.saveNewsToDB(SaveNews(
                        it[position].article_id,
                        it[position].author,
                        it[position].title,
                        it[position].urlToImage,
                        it[position].content
                    ))
                }
            })

        }
    }
}