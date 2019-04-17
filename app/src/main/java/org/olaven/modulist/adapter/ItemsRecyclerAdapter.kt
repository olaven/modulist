package org.olaven.modulist.adapter

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*
import org.olaven.modulist.R
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.dialog.DeleteItemDialog
import org.olaven.modulist.task.update.UpdateItemTask

class ItemsRecyclerAdapter(val application: Application, val activity: AppCompatActivity, var moduleList: ModuleList, var days: Int):  androidx.recyclerview.widget.RecyclerView.Adapter<ItemsRecyclerAdapter.MyViewHolder>() {

    private val items = mutableListOf<Item>()

    fun add(item: Item) {
        items.add(item)
    }

    inner class MyViewHolder(view: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

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

            list_item_checkbox.isChecked = item.done
            list_item_checkbox.setOnClickListener {

                item.done = list_item_checkbox.isChecked
                val dto = UpdateItemTask.DTO(item)
                UpdateItemTask(application).execute(dto)
            }

            list_item_delete.setOnClickListener {

                DeleteItemDialog(item, moduleList, activity)
                    .show()
            }
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

    fun clear() =
            items.clear()
}
