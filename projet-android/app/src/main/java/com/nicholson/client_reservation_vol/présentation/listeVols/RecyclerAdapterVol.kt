package com.nicholson.client_reservation_vol.présentation.listeVols

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.présentation.OTD.VolListItemOTD

class RecyclerAdapterVol( private val volListItemOTDS : List<VolListItemOTD> ) :
    RecyclerView.Adapter<RecyclerAdapterVol.MyViewHolder>() {

    var itemCliquéÉvènement: ((Int) ->Unit)? = null
    private var lastPosition = -1

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
        holder.textViewDateDépart.text = volListItemOTDS[position].dateDépart
        holder.textViewPrixÉcono.text =  volListItemOTDS[position].prixÉconomique
        holder.textViewHeureDépart.text = volListItemOTDS[position].heureDépart
        holder.textViewHeureArrivée.text = volListItemOTDS[position].heureArrivée
        holder.textViewNomVilleDépart.text = volListItemOTDS[position].nomVilleDépart
        holder.textViewNomVilleArrivée.text = volListItemOTDS[position].nomVilleArrivée
        holder.textViewCodeAéroportDépart.text = volListItemOTDS[position].codeAéroportDépart
        holder.textViewCodeAéroportArrivée.text = volListItemOTDS[position].codeAéroportArrivée
        holder.textViewDurée.text = volListItemOTDS[position].durée

        holder.itemView.setOnClickListener{
            itemCliquéÉvènement?.invoke(position)
        }

        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.glisser_de_la_gauche)
            animation.startOffset = (position * 100).toLong()
            holder.itemView.startAnimation(animation)
            lastPosition = holder.adapterPosition
        }
    }

    override fun getItemCount(): Int = volListItemOTDS.size

}