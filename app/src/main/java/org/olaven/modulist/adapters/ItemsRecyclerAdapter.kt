package org.olaven.modulist.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.list_item.view.*
import org.olaven.modulist.R
import org.olaven.modulist.database.entity.Item

class ItemsRecyclerAdapter(val context: Context, var days: Int):  RecyclerView.Adapter<ItemsRecyclerAdapter.MyViewHolder>() {

    private val items = mutableListOf<Item>()

    fun add(item: Item) {
        items.add(item)
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {

                Toast.makeText(context, "Clicked item", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount() =
        items.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item, parent, false)

        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = items[position]
        holder.itemView.apply {

            list_item_name.text = getItemText(item)
            list_item_info.text = "some attachments or somethign"
            list_item_checkbox.isChecked = item.done
        }
    }

    private fun getItemText(item: Item): String {

        val name = item.name
        val amount =
            if (days / item.dayDistribution == 0)
                1
            else
                days / item.dayDistribution

        return "$name, x$amount"
    }
}
