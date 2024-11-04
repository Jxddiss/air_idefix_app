package com.nicholson.client_reservation_vol.présentation.listeVols

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import java.time.format.DateTimeFormatter
import java.util.Locale

class RecyclerAdapterVol( val listeVol : MutableList<Vol> ) :
    RecyclerView.Adapter<RecyclerAdapterVol.MyViewHolder>() {

    class MyViewHolder( itemView : View ) : RecyclerView.ViewHolder( itemView ) {

        val textViewDateDépart : TextView = itemView
            .findViewById( R.id.textViewDateDépart )

        val textViewPrixÉcono : TextView = itemView
            .findViewById( R.id.textViewPrixÉcono )

        val textViewHeureDépart : TextView = itemView
            .findViewById( R.id.textViewHeureDépart )

        val textViewNomVilleDépart : TextView = itemView
            .findViewById( R.id.textViewNomVilleDépart )

        val textViewCodeAéroportDépart : TextView = itemView
            .findViewById( R.id.textViewCodeAéroportDépart )

        val textViewHeureArrivée : TextView = itemView
            .findViewById( R.id.textViewHeureArrivée )

        val textViewNomVilleArrivée : TextView = itemView
            .findViewById( R.id.textViewNomVilleArrivée )

        val textViewCodeAéroportArrivée : TextView = itemView
            .findViewById( R.id.textViewCodeAéroportArrivée )

        val textViewDurée : TextView = itemView
            .findViewById( R.id.textViewDurée )

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val itemView : View = LayoutInflater.from( parent.context )
            .inflate( R.layout.liste_vol_item, parent, false )

        return MyViewHolder( itemView )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val formatterDate = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("fr"))
        val formatterHeure = DateTimeFormatter.ofPattern("HH:MM", Locale("fr"))

        holder.textViewDateDépart.text = listeVol[position]
            .dateDepart
            .format( formatterDate )

        holder.textViewPrixÉcono.text = String.format( "%.2f$", listeVol[position]
            .prixParClasse["Économique"] )

        holder.textViewHeureDépart.text = listeVol[position]
            .dateDepart.format( formatterHeure )

        holder.textViewHeureArrivée.text = listeVol[position]
            .dateArrivee
            .format( formatterHeure )

        holder.textViewNomVilleDépart.text = listeVol[position]
            .aeroportDebut.ville.nom

        holder.textViewNomVilleArrivée.text = listeVol[position]
            .aeroportFin.ville.nom

        holder.textViewCodeAéroportDépart.text = listeVol[position]
            .aeroportDebut.code

        holder.textViewCodeAéroportArrivée.text = listeVol[position]
            .aeroportFin.code

        holder.textViewDurée.text = listeVol[position].durée.toComponents {
            hrs, min, sec, nanos ->
            "${hrs}h${min}"
        }
    }

    override fun getItemCount(): Int = listeVol.size

}