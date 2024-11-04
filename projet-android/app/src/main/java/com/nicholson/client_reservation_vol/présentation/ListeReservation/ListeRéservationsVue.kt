package com.nicholson.client_reservation_vol.présentation.ListeReservation

import android.media.Image
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.domaine.entité.Aeroport
import com.nicholson.client_reservation_vol.domaine.entité.Avion
import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.domaine.entité.Siège
import com.nicholson.client_reservation_vol.domaine.entité.Ville
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.domaine.entité.VolStatut
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ListeRéservationsVue : Fragment() {

    lateinit var recycler: RecyclerView
    lateinit var reservationList: ArrayList<Réservation>
    lateinit var volList: ArrayList<Vol>
    lateinit var adapter : RecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_liste_reservations_vue, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(vue: View, savedInstanceState: Bundle?) {
        super.onViewCreated(vue, savedInstanceState)

        val client = Client(1, "Doe", "John", "123 Main St", "A12345678", "john.doe@example.com", "123-456-7890")
        val siege = Siège("E12", "Économique", "occupé", 101, 1)

        val paris = Ville(
            id = 1,
            nom = "Paris",
            pays = "France",
            url_photo = "https://media.istockphoto.com/id/635758088/photo/sunrise-at-the-eiffel-tower-in-paris-along-the-seine.jpg?s=612x612&w=0&k=20&c=rdy3aU1CDyh66mPyR5AAc1yJ0yEameR_v2vOXp2uuMM="
        )

        val newYork = Ville(
            id = 2,
            nom = "New York",
            pays = "USA",
            url_photo = "https://media.istockphoto.com/id/1454217037/photo/statue-of-liberty-and-new-york-city-skyline-with-manhattan-financial-district-world-trade.jpg?s=612x612&w=0&k=20&c=6V54_qVlDfo59GLEdY2W8DOjLbbHTJ9y4AnJ58a3cis="

        )

        val aeroportCharlesDeGaulle = Aeroport(
            code = "CDG",
            nom = "Charles de Gaulle Airport",
            ville = paris,
            pays = "France"
        )

        val aeroportJFK = Aeroport(
            code = "JFK",
            nom = "John F. Kennedy International Airport",
            ville = newYork,
            pays = "USA"
        )

        val avion = Avion(
            id = 1,
            type = "Boeing 737",
            sièges = listOf(siege),
            numéroVol = "FLIGHT001"
        )

        val volStatut = VolStatut(
            numéroVol = "FLIGHT001",
            Statut = "On Time",
            heure = LocalTime.of(14, 30)
        )

        val vol = Vol(
            id = 1,
            numeroVol = "FLIGHT001",
            aeroportDebut = aeroportCharlesDeGaulle,
            aeroportFin = aeroportJFK,
            dateDepart = LocalDateTime.of(2024, 11, 6, 14, 30),
            dateArrivee = LocalDateTime.of(2024, 11, 6, 16, 30),
            avion = avion,
            prixParClasse = mapOf("Économique" to 150.0, "Business" to 300.0),
            poidsMaxBag = 20,
            statutVol = listOf(volStatut),
            durée = 5.toDuration(DurationUnit.HOURS)
        )

        val vol2 = Vol(
            id = 2,
            numeroVol = "FLIGHT002",
            aeroportDebut = aeroportJFK,
            aeroportFin = aeroportCharlesDeGaulle,
            dateDepart = LocalDateTime.of(2024, 11, 14, 14, 30),
            dateArrivee = LocalDateTime.of(2024, 11, 16, 16, 30),
            avion = avion,
            prixParClasse = mapOf("Économique" to 150.0, "Business" to 300.0),
            poidsMaxBag = 20,
            statutVol = listOf(volStatut),
            durée = 5.toDuration(DurationUnit.HOURS)
        )



        val reservation = Réservation(1 ,"RES123", "FLIGHT001", listOf(client), listOf(siege))

        val reservation2 = Réservation(2 ,"RES321", "FLIGHT002", listOf(client), listOf(siege))

        volList = ArrayList()

        volList.add(vol)
        volList.add(vol2)

        reservationList = ArrayList()

        reservationList.add(reservation)
        reservationList.add(reservation2)

        recycler = vue.findViewById(R.id.recyclerVoyages)
        setInfoAdapter()
    }

    fun setInfoAdapter(){
        adapter = RecyclerAdapter(reservationList, volList)
        var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recycler.layoutManager = layoutManager
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.adapter = adapter

    }

}