package com.nicholson.client_reservation_vol.présentation.ListeReservation

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import kotlinx.coroutines.handleCoroutineException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.time.Duration

class RecyclerAdapter(var reservationList: ArrayList<Réservation>, var volList: ArrayList<Vol>):
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>(){

        class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var tempsRestant :TextView
            var destination : TextView
            var dateDepart : TextView
            var image : ImageView

            init {
                tempsRestant = itemView.findViewById(R.id.textTempsRestant)
                destination = itemView.findViewById(R.id.textDestination)
                dateDepart = itemView.findViewById(R.id.textDate)
                image = itemView.findViewById(R.id.imgDestination)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.list_reservations,parent,false)

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return reservationList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var vol = findVolById(volList ,reservationList[position].idVol)


        val volDate : LocalDateTime = vol.dateDepart
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("fr"))
        val dateFormater = volDate.format(formatter)

        val tempMtn : LocalDateTime = LocalDateTime.now()

        val hoursRemaining: Long = ChronoUnit.HOURS.between(tempMtn, volDate)

        var temp : String = hoursRemaining.toString() + "\nheures"
        var destination : String = vol.aeroportFin.pays

        holder.tempsRestant.text = temp
        holder.destination.text = destination
        holder.dateDepart.text = dateFormater.toString()


    }

    fun findVolById(volList: List<Vol>, id: String): Vol {

        return volList.firstOrNull { it.numeroVol == id } ?: throw (Exception())
    }


}