package com.mhs.userinfo.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat

fun View.isVisible(isShowLoading: Boolean, container: View) {
    if (isShowLoading) {
        this.visibility = View.VISIBLE
        container.visibility = View.GONE
    } else {
        this.visibility = View.GONE
        container.visibility = View.VISIBLE
    }
}
fun RecyclerView.initRecycler(layoutManager: RecyclerView.LayoutManager, adapter: RecyclerView.Adapter<*>) {
    this.adapter=adapter
    this.layoutManager=layoutManager
}

