package com.tut.mymovieplayground.view.adapters

import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewPaginator(
    private val isLoading: LiveData<Boolean>,
    private val loadPage: (Int) -> Unit,
    private val onLast: () -> Boolean = { true }
) : RecyclerView.OnScrollListener() {

    var threshold = 15
    var currentPage: Int = 0


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (onLast() || isLoading.value!!) return
        val layoutManager = recyclerView.layoutManager
        layoutManager?.let {
            val visibleItemCount = it.childCount
            val totalItemCount = it.itemCount
            val firstVisibleItemPosition =
                (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            if (isLoading.value!!) return

            if ((visibleItemCount + firstVisibleItemPosition + threshold) >= totalItemCount) {
                loadPage(++currentPage)
            }
        }
    }

}