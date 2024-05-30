package com.example.prueba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InformeAdapter(private val informeList: List<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_TITLE = 0
    private val VIEW_TYPE_ITEM = 1

    // ViewHolder para elementos de tipo InformeItem
    class InformeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreProyecto: TextView = itemView.findViewById(R.id.nombreProyecto)
        val recursosEconomicos: TextView = itemView.findViewById(R.id.recursosEconomicos)
    }

    // ViewHolder para elementos de tipo InformeTitle
    class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_TITLE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_informe_title, parent, false)
            TitleViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_informe, parent, false)
            InformeViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_TITLE) {
            val titleViewHolder = holder as TitleViewHolder
            val informeTitle = informeList[position] as InformeTitle
            titleViewHolder.title.text = informeTitle.title
        } else {
            val informeViewHolder = holder as InformeViewHolder
            val informeItem = informeList[position] as InformeItem
            informeViewHolder.nombreProyecto.text = informeItem.nombreProyecto
            informeViewHolder.recursosEconomicos.text = informeItem.recursosEconomicos
        }
    }

    override fun getItemCount(): Int = informeList.size

    override fun getItemViewType(position: Int): Int {
        return if (informeList[position] is InformeTitle) VIEW_TYPE_TITLE else VIEW_TYPE_ITEM
    }
}
