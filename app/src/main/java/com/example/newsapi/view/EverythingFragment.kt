package com.example.newsapi.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi.databinding.FragmentEverythingBinding
import com.example.newsapi.model.network.InternetCheck
import com.example.newsapi.utils.BindingFragment
import com.example.newsapi.viewModel.NewsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class EverythingFragment :
    BindingFragment<FragmentEverythingBinding>(FragmentEverythingBinding::inflate) {

    private var isLoading = false
    private var listSize = 15
    private val viewModel: NewsViewModel by viewModel()
    private var layoutManager: LinearLayoutManager? = null
    private lateinit var recycler: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            layoutManager = LinearLayoutManager(context)
            recycler = recyclerView
            recycler.layoutManager = layoutManager
            recycler.setHasFixedSize(true)
            recycler.isNestedScrollingEnabled = false
            swipeRefreshLayout.isEnabled = false

            InternetCheck { internet ->
                if (!internet) {
                    Toast.makeText(context, "Нет подключения к интернету!", Toast.LENGTH_SHORT)
                        .show()
                    if (swipeRefreshLayout.isRefreshing) {
                        swipeRefreshLayout.isRefreshing = false
                    }
                } else {
                    loadMore()
                    swipeRefreshLayout.isEnabled = true
                    swipeRefreshLayout.setOnRefreshListener {
                        swipeRefreshLayout.isRefreshing = true
                        listSize = 10
                        loadMore()
                        swipeRefreshLayout.isRefreshing = false
                    }
                    recyclerView.setOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            if (!isLoading) {

                                val visibleItemCount = layoutManager!!.childCount
                                val pastVisibleItem =
                                    layoutManager!!.findFirstCompletelyVisibleItemPosition()
                                val total = recyclerView.adapter!!.itemCount

                                if ((visibleItemCount + pastVisibleItem) >= total) {

                                    listSize += 10
                                    loadMore()

                                }
                            }
                            super.onScrolled(recyclerView, dx, dy)
                        }
                    })
                }
            }
            viewModel.getNewsList().observe(viewLifecycleOwner, Observer {
                recycler.adapter = EverythingAdapter(it)
                recycler.adapter?.notifyDataSetChanged()
                swipeRefreshLayout.isRefreshing = false
            })
        }
    }

    private fun loadMore() {
        isLoading = true

        recycler.adapter?.notifyItemInserted(listSize - 1)
        var scrollPosition = listSize
        recycler.adapter?.notifyItemRemoved(scrollPosition)

        viewModel.requestToNewsAPI(listSize)

        isLoading = false

    }
}