package com.nicholson.client_reservation_vol.présentation.ListeReservation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.présentation.OTD.RéservationListItemOTD
import com.nicholson.client_reservation_vol.présentation.OTD.VolListItemOTD
import com.nicholson.client_reservation_vol.présentation.listeVols.ContratVuePrésentateurListeVols.*
import com.nicholson.client_reservation_vol.présentation.ListeReservation.ContratVuePrésentateurListeRéservation.*

class ListeRéservationsVue : Fragment(),
    ContratVuePrésentateurListeRéservation.IListeDeRéservationsVue {

    var présentateur : IListeDeRéservationsPrésentateur? = ListeRéservationsPrésentateur(this)
    lateinit var recycler: RecyclerView
    lateinit var btnRechercherVoyages : Button
    lateinit var adaptateur : RecyclerAdapter
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_liste_reservations_vue, container, false)
    }

    override fun onViewCreated(vue: View, savedInstanceState: Bundle?) {
        super.onViewCreated(vue, savedInstanceState)

        btnRechercherVoyages = vue.findViewById(R.id.btnRechercherFromVoyages)
        btnRechercherVoyages.setOnClickListener{
            présentateur?.traiterBtnRechercheVolCliqué()
        }
        navController = Navigation.findNavController(vue)
        recycler = vue.findViewById(R.id.recyclerVoyages)
        présentateur?.traiterObtenirRéservation()

    }

    override fun afficherRéservations(listeDeReservation: MutableList<RéservationListItemOTD>){//, listeDeVols : MutableList<VolListItemOTD>?, vue: View?) {
        ajouterAdaptateurRéservationAuRecycler(listeDeReservation)//,listeDeVols,vue)
    }

    override fun redirigerPageRéservationSpécifique() {
        navController.navigate(R.id.action_listeRéservationsVue_vers_réservationSpécifiqueVue)
    }

    override fun redirigerVueRechercherVol() {
        navController.navigate(R.id.action_listeRéservationsVue_vers_rechercherUnVolVue)
    }

    fun ajouterAdaptateurRéservationAuRecycler(listeDeRéservation: MutableList<RéservationListItemOTD>){//, listeDeVols: MutableList<VolListItemOTD>, vue: View){
        adaptateur = RecyclerAdapter(listeDeRéservation)
        adaptateur.itemCliquéÉvènement = {
            présentateur?.traiterRéservationCliqué( it )
        }

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.adapter = adaptateur


    }

}