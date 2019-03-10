package org.olaven.modulist.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_card.view.*
import org.olaven.modulist.R
import org.olaven.modulist.activities.ModuleListActivity
import org.olaven.modulist.database.entity.ModuleList

class HomeFragmentRecyclerAdapter(private val context: Context, private val moduleLists: List<ModuleList>): RecyclerView.Adapter<HomeFragmentRecyclerAdapter.MyViewHolder>() {

    override fun getItemCount(): Int =
        moduleLists.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(parent?.context)
        val view = layoutInflater.inflate(R.layout.list_card, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val moduleList = moduleLists[position]
        holder.view.apply {

            list_card_name.text = moduleList.name
            list_card_favourite.text = "not favourite"
        }
    }

    inner class MyViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {

                val moduleList = moduleLists[adapterPosition]
                val intent = Intent(context, ModuleListActivity::class.java)
                val key = context.getString(R.string.extra_id_key)

                intent.putExtra(key, moduleList.id)
                context.startActivity(intent)
            }
        }
    }

}