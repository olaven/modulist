package org.olaven.modulist.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_card.view.*
import org.olaven.modulist.R
import org.olaven.modulist.models.ModuleList

class RecyclerAdapter(private val lists: List<ModuleList>) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    override fun getItemCount(): Int =
        lists.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(parent?.context)
        val view = layoutInflater.inflate(R.layout.list_card, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val moduleList = lists[position]
        holder.view.list_card_name.text = moduleList.name
        holder.view.list_card_favourite.text = "not favourite"
    }

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {



    }
}
