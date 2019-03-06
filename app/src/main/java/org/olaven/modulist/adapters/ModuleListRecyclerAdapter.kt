package org.olaven.modulist.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.list_card.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import org.olaven.modulist.R
import org.olaven.modulist.database.entity.Item

class ModuleListRecyclerAdapter(val context: Context) : RecyclerView.Adapter<ModuleListRecyclerAdapter.MyViewHolder>() {

    var items = mutableListOf<Item>()

    // could be added by observer on livedata
    fun addItem(item: Item) =
        items.add(item)

    fun clear() = {
        items = mutableListOf()
    }


    override fun getItemCount(): Int =
        items.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(parent?.context)
        val view = layoutInflater.inflate(R.layout.list_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = items[position]

        holder.view.apply {

            list_item_checkbox.isChecked = item.done
            list_item_name.text = item.name
            list_item_info.text = "add some info or other about attachments"
        }
    }

    inner class MyViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {

                val moduleList = items[adapterPosition]
                Toast.makeText(context, "clicked an item", Toast.LENGTH_SHORT)
            }
        }
    }

}
