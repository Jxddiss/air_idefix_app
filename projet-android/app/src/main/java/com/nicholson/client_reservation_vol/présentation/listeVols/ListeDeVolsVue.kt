package com.nicholson.client_reservation_vol.présentation.listeVols

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.domaine.entité.Ville
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.présentation.listeVols.ContratVuePrésentateurListeVols.*

class ListeDeVolsVue : Fragment(), IListeDeVolsVue {
    var présentateur : IListeDeVolsPrésentateur? = ListeDeVolsPrésentateur( this )
    lateinit var adaptateur : RecyclerAdapterVol
    lateinit var recyclerVol : RecyclerView
    lateinit var imageViewDestination : ImageView
    lateinit var textViewNomDestination : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_liste_de_vols_vue, container, false )
    }

    override fun onViewCreated( vue : View, savedInstanceState: Bundle? ) {
        super.onViewCreated( vue, savedInstanceState )

        imageViewDestination = vue.findViewById( R.id.imgDestinationListeVols )
        textViewNomDestination = vue.findViewById( R.id.textViewNomDestination )
        recyclerVol = vue.findViewById( R.id.RecyclerVols )
        présentateur?.traiterObtenirVols()
    }


    override fun afficherVols( listeDeVols : MutableList<Vol> ) {
        val indexAléatoire = ( 0 until listeDeVols.size ).random()
        val villeAléatoire =  listeDeVols[indexAléatoire].aeroportFin.ville

        obtenirInfoDestination(villeAléatoire)
        ajouterAdaptateurVolAuRecycler( listeDeVols )
    }

    private fun ajouterAdaptateurVolAuRecycler( listeDeVols : MutableList<Vol> ){
        adaptateur = RecyclerAdapterVol( listeDeVols )
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager( requireContext() )
        recyclerVol.layoutManager = layoutManager
        recyclerVol.itemAnimator = DefaultItemAnimator()
        recyclerVol.adapter = adaptateur
    }

    private fun obtenirInfoDestination(ville : Ville ){
        textViewNomDestination.text = "Vols vers ${ville.nom}"
        if ( context != null ) {
            Glide.with( requireContext() )
                .load( ville.url_photo )
                .into( imageViewDestination )
        }
    }
}