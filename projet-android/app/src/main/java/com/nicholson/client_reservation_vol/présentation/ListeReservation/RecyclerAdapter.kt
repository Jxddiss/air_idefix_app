package com.nicholson.client_reservation_vol.présentation.ListeReservation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.présentation.OTD.RéservationListItemOTD
import com.nicholson.client_reservation_vol.présentation.OTD.VolListItemOTD
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale


class RecyclerAdapter(val reservationListItemOTDS: MutableList<RéservationListItemOTD>)://, var volList: MutableList<VolListItemOTD>, val appVue : View):
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>(){

        var itemCliquéÉvènement: ((Int) ->Unit)? = null

        class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var tempsRestant :TextView
            var destination : TextView
            var dateDepart : TextView
            var imageView : ImageView
            var tempsUnite : TextView
            var progressTemp : ProgressBar
            var btnConsulterVoyage : Button

            init {
                tempsRestant = itemView.findViewById(R.id.textTempsRestant)
                destination = itemView.findViewById(R.id.textDestination)
                dateDepart = itemView.findViewById(R.id.textDate)
                imageView = itemView.findViewById(R.id.imgDestination)
                tempsUnite = itemView.findViewById(R.id.textTempsUnite)
                progressTemp = itemView.findViewById<ProgressBar>(R.id.progressBarTime)
                btnConsulterVoyage = itemView.findViewById(R.id.btnConsulteVoyage)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.list_reservations,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return reservationListItemOTDS.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Glide.with(holder.itemView.context)
            .load(reservationListItemOTDS[position].url_photo)
            .into(holder.imageView)

        holder.tempsRestant.text = reservationListItemOTDS[position].tempsRestant
        holder.tempsUnite.text = reservationListItemOTDS[position].tempsUnite
        holder.destination.text = reservationListItemOTDS[position].destination
        holder.dateDepart.text = reservationListItemOTDS[position].dateDepart
        holder.progressTemp.progress = reservationListItemOTDS[position].barProgres.toInt()

        holder.btnConsulterVoyage.setOnClickListener{
            itemCliquéÉvènement?.invoke(position)
        }
    }




}