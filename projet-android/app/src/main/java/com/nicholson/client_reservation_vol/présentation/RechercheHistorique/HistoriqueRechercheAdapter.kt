package com.nicholson.client_reservation_vol.présentation.RechercheHistorique

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nicholson.client_reservation_vol.R

class HistoriqueRechercheAdapter(private val rechercheHistoriqueList: List<String>) :
    RecyclerView.Adapter<HistoriqueRechercheAdapter.HistoriqueRechercheViewHolder>() {

    // Inflate the item layout and return a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoriqueRechercheViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reycler_historique_list, parent, false)
        return HistoriqueRechercheViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: HistoriqueRechercheViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class HistoriqueRechercheViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


 }