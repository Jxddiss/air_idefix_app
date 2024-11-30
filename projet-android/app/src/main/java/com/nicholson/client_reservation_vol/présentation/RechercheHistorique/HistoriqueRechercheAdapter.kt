package com.nicholson.client_reservation_vol.présentation.RechercheHistorique

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.présentation.OTD.HistoriqueListItemOTD
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class HistoriqueRechercheAdapter(private var rechercheHistoriqueList:  List<HistoriqueListItemOTD>,
                                 private val onItemClick: (HistoriqueListItemOTD) -> Unit) :
    RecyclerView.Adapter<HistoriqueRechercheAdapter.HistoriqueRechercheViewHolder>() {


    class HistoriqueRechercheViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewAeroportDe: TextView = itemView.findViewById(R.id.AeroportDe)
        val textViewVilleDe: TextView = itemView.findViewById(R.id.VilleDE)
        val textViewVilleVers: TextView = itemView.findViewById(R.id.VilleVers)
        val textViewAeroportVers: TextView = itemView.findViewById(R.id.AeroportVers)
        val textViewDateDepart: TextView = itemView.findViewById(R.id.DateDepart)
        val textViewDateReturn: TextView = itemView.findViewById(R.id.DateReturn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoriqueRechercheViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reycler_historique_list, parent, false)
        return HistoriqueRechercheViewHolder(view)
    }


    override fun getItemCount(): Int {
        return rechercheHistoriqueList.size
    }

    override fun onBindViewHolder(holder: HistoriqueRechercheViewHolder, position: Int) {
        val historiqueItem = rechercheHistoriqueList[position]

        holder.textViewAeroportDe.text = historiqueItem.aeroportDe
        holder.textViewVilleDe.text = historiqueItem.villeDe
        holder.textViewVilleVers.text = historiqueItem.villeVers
        holder.textViewAeroportVers.text = historiqueItem.aeroportVers

        val dateFormat = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())

        holder.textViewDateDepart.text = historiqueItem.dateDepart.format(dateFormat)
        holder.textViewDateReturn.text = historiqueItem.dateRetour?.format(dateFormat) ?: "Date non disponible"


        // click listener
        holder.itemView.setOnClickListener {
            onItemClick(historiqueItem)
        }
    }

    fun updateData(newList: List<HistoriqueListItemOTD>) {
        rechercheHistoriqueList = newList
        notifyDataSetChanged()
    }

}
