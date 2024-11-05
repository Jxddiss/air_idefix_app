package com.nicholson.client_reservation_vol.présentation.ListeReservation

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale


class RecyclerAdapter(var reservationList: MutableList<Réservation>, var volList: MutableList<Vol>, val appVue : View):
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>(){

        lateinit var navController: NavController

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
        var itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.list_reservations,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return reservationList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val vol = findVolById(volList ,reservationList[position].idVol)
        val volDate : LocalDateTime = vol.dateDepart
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("fr"))
        val dateFormater = volDate.format(formatter)
        navController = Navigation.findNavController(appVue)

        val tempMtn : LocalDateTime = LocalDateTime.now()

        var timeRemaining: Long = ChronoUnit.HOURS.between(tempMtn, volDate)
        var unitTemp = "Heures"

        if(timeRemaining > 24){
             timeRemaining = ChronoUnit.DAYS.between(tempMtn, volDate)
             unitTemp = "Jours"
        }

        if(unitTemp=="Jours"){
            holder.progressTemp.progress = 30-timeRemaining.toInt()
        }
        else{
            holder.progressTemp.progress =30 - 1
        }

        val destination : String = vol.aeroportFin.pays

        val url_photo : String = vol.aeroportFin.ville.url_photo

        Glide.with(holder.itemView.context)
            .load(url_photo)
            .into(holder.imageView)

        holder.tempsRestant.text = timeRemaining.toString()
        holder.tempsUnite.text = unitTemp
        holder.destination.text = destination
        holder.dateDepart.text = dateFormater.toString()

        holder.btnConsulterVoyage.setOnClickListener{
            navController.navigate(R.id.action_listeRéservationsVue_vers_réservationSpécifiqueVue)
        }
    }

    fun findVolById(volList: List<Vol>, id: Int): Vol {
        return volList.first{ it.id == id }
    }


}