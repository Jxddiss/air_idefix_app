package com.nicholson.client_reservation_vol.présentation.listeVols

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.domaine.entité.Ville
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.présentation.OTD.VolListItemOTD
import com.nicholson.client_reservation_vol.présentation.listeVols.ContratVuePrésentateurListeVols.*

class ListeDeVolsVue : Fragment(), IListeDeVolsVue {
    var présentateur : IListeDeVolsPrésentateur? = ListeDeVolsPrésentateur( this )
    lateinit var adaptateur : RecyclerAdapterVol
    lateinit var recyclerVol : RecyclerView
    lateinit var imageViewDestination : ImageView
    lateinit var textViewNomDestination : TextView
    lateinit var navController: NavController

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
        navController = Navigation.findNavController( vue )
    }


    override fun afficherVols( listeDeVols : List<VolListItemOTD> ) {
        ajouterAdaptateurVolAuRecycler( listeDeVols )
    }

    private fun ajouterAdaptateurVolAuRecycler( listeDeVols : List<VolListItemOTD> ){
        adaptateur = RecyclerAdapterVol( listeDeVols )
        adaptateur.itemCliquéÉvènement = {
            présentateur?.traiterVolCliqué( it )
        }
        recyclerVol.layoutManager = LinearLayoutManager( requireContext() )
        recyclerVol.itemAnimator = DefaultItemAnimator()
        recyclerVol.adapter = adaptateur
    }

    override fun afficherInfoDestination( nomVille : String, urlImage : String ){
        textViewNomDestination.text = "Vols vers $nomVille"
        Glide.with( requireContext() )
            .load( urlImage )
            .into( imageViewDestination )
    }

    override fun redirigerVersChoixClasse() {
        navController.navigate( R.id.action_listeDeVolsVue_vers_choisirClasseVue )
    }
}