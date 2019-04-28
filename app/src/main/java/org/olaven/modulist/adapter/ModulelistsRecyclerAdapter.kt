package org.olaven.modulist.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_card.view.*
import org.olaven.modulist.R
import org.olaven.modulist.activity.ModuleListActivity
import org.olaven.modulist.database.entity.ModuleList


class ModulelistsRecyclerAdapter(val context: Context, private val moduleLists: List<ModuleList>):  androidx.recyclerview.widget.RecyclerView.Adapter<ModulelistsRecyclerAdapter.MyViewHolder>() {


    override fun getItemCount() =
        moduleLists.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(parent?.context)
        val view = layoutInflater.inflate(R.layout.list_card, parent, false)

        return MyViewHolder(view as androidx.cardview.widget.CardView)
    }


    override fun onBindViewHolder(holder: ModulelistsRecyclerAdapter.MyViewHolder, position: Int) {

        val moduleList = moduleLists[position]
        holder.view.apply {

            setCardBackgroundColor(moduleList.color)
            list_card_name.text = moduleList.name
        }
    }

    inner class MyViewHolder(val view: androidx.cardview.widget.CardView): androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {

                val moduleList = moduleLists[adapterPosition]
                val intent = Intent(context, ModuleListActivity::class.java)
                val key = context.getString(R.string.extra_modulelist_key)

                intent.putExtra(key, moduleList.id)
                context.startActivity(intent)
            }
        }
    }
}
