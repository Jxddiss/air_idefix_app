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
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ListeRéservationsVue : Fragment() {

    lateinit var recycler: RecyclerView
    lateinit var btnRechercherVoyages : Button
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

    override fun onViewCreated(vue: View, savedInstanceState: Bundle?) {
        super.onViewCreated(vue, savedInstanceState)
        btnRechercherVoyages = vue.findViewById(R.id.btnRechercherFromVoyages)
        btnRechercherVoyages.setOnClickListener{
            val navController = Navigation.findNavController(vue)
            navController.navigate(R.id.action_listeRéservationsVue_vers_rechercherUnVolVue)
        }

        recycler = vue.findViewById(R.id.recyclerVoyages)
        setInfoAdapter(vue)
    }

    fun setInfoAdapter(vue: View){
        adapter = RecyclerAdapter(SourceDonnéesFictive.listeRéservation, SourceDonnéesFictive.listVol, vue)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recycler.layoutManager = layoutManager
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.adapter = adapter

    }

}