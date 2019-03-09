package org.olaven.modulist.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.list_item.view.*
import org.olaven.modulist.R
import org.olaven.modulist.activities.ModuleListActivity
import org.olaven.modulist.database.entity.Item

class ItemsRecyclerAdapter(val context: Context, private val items: List<Item>):  RecyclerView.Adapter<ItemsRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {

                Toast.makeText(context, "Clicked item", Toast.LENGTH_SHORT).show()
            }
        }
    }

    init {
        Toast.makeText(context, "${items.count()} - count in items - from init", Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int {
        Toast.makeText(context, "${items.count()} - count in items", Toast.LENGTH_SHORT).show()
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(parent?.context)
        val view = layoutInflater.inflate(R.layout.list_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = items[position]
        holder.itemView.apply {

            list_item_name.text = item.name
            list_item_info.text = "some attachments or somethign"
            list_item_checkbox.isChecked = item.done
        }
    }
}
