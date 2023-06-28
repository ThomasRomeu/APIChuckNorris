package com.example.segundoexamen_thomasromeu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CategoriaAdapter(private val category: List<String>) :
    ListAdapter<String, CategoriaAdapter.ViewHolder>(DiffCallBack) {
    lateinit var categoriaSeleccionada: (String) -> Unit

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(R.id.textViewCategory)

        fun bind(category: String){
            textView.text = category
            view.setOnClickListener {
                categoriaSeleccionada(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.itemlist, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = category[position]
        holder.bind(categoria)
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

}