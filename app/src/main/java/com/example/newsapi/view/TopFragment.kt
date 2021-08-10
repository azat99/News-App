package com.example.newsapi.view

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi.databinding.FragmentTopBinding
import com.example.newsapi.model.network.InternetCheck
import com.example.newsapi.utils.BindingFragment
import com.example.newsapi.viewModel.NewsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.koin.android.viewmodel.ext.android.viewModel

class TopFragment : BindingFragment<FragmentTopBinding>(FragmentTopBinding::inflate) {

    private var isLoading = false
    private var listSize = 15
    val viewModel: NewsViewModel by viewModel()
    private lateinit var recycler: RecyclerView
    private var layoutManager: LinearLayoutManager? = null
    var job: Job? = null

    override fun onStart() {
        super.onStart()
        job = CoroutineScope(Dispatchers.IO).launchPeriodicAsync(5000) {
            init()
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            recycler = recyclerView
            layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            recycler.setHasFixedSize(true)

            InternetCheck { internet ->
                if (!internet) {
                    Toast.makeText(context, "Нет подключения к интернету!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    init()
                    recycler.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                        if (!isLoading) {

                            val visibleItemCount = recycler.layoutManager!!.childCount
                            val pastVisibleItem =
                                layoutManager!!.findFirstCompletelyVisibleItemPosition()
                            val total = recycler.adapter!!.itemCount

                            if ((visibleItemCount + pastVisibleItem) >= total) {
                                listSize += 10
                                init()
                            }
                        }
                    }
                }
            }
            viewModel.getNewsList().observe(viewLifecycleOwner, Observer {
                recycler.adapter = NewsAdapter(it)
                println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
            })

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    private fun init() {
        isLoading = true

        recycler.adapter?.notifyItemInserted(listSize - 1)
        var scrollPosition = listSize
        recycler.adapter?.notifyItemRemoved(scrollPosition)

        viewModel.requestToNewsAPI(listSize)
        isLoading = false
    }

    fun CoroutineScope.launchPeriodicAsync(
        repeatMillis: Long,
        action: () -> Unit
    ) = this.async {
        if (repeatMillis > 0) {
            while (isActive) {
                action()
                delay(repeatMillis)
            }
        } else {
            action()
        }
    }
}