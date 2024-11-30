package com.nicholson.client_reservation_vol.présentation.RechercheHistorique

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.domaine.entité.Historique
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheHistorique
import com.nicholson.client_reservation_vol.présentation.OTD.HistoriqueListItemOTD
import com.nicholson.client_reservation_vol.présentation.RechercheHistorique.ContratVuePrésentateurHistorique.IListeDeHistoriqueVue
import com.nicholson.client_reservation_vol.présentation.RechercherVol.RechercherUnVolVue
import java.time.LocalDate


class HistoriqueRechercheVue : Fragment(), ContratVuePrésentateurHistorique.IListeDeHistoriqueVue {

    private lateinit var recyclerView: RecyclerView
    private lateinit var rechercheHistoriqueAdapter: HistoriqueRechercheAdapter
    private lateinit var historiquePrésentateur: HistoriquePrésentateur
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historiquePrésentateur = HistoriquePrésentateur(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.recycler_historique_list)
        navController = findNavController()

        // Initialize la data source avec le context
        historiquePrésentateur.initialiserContexte(requireContext())

        // get historique data apres l'initialization
        historiquePrésentateur.traiterObtenirHistorique()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_historique_recherche_vue, container, false)
    }


    override fun afficherHistorique(listeDeHistorique: List<HistoriqueListItemOTD>) {

        if (::rechercheHistoriqueAdapter.isInitialized) {
            rechercheHistoriqueAdapter.updateData(listeDeHistorique)
        } else {
            rechercheHistoriqueAdapter = HistoriqueRechercheAdapter(listeDeHistorique) { historique ->
                val bundle = Bundle().apply {
                    putSerializable("historique", historique)
                }
                redirigerVersRechercherUnVolVue(bundle)
            }
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = rechercheHistoriqueAdapter
        }
    }


    private fun redirigerVersRechercherUnVolVue(bundle: Bundle) {
        navController.navigate(R.id.action_historiqueRechercheVue_to_rechercherUnVolVue,bundle)
    }

}


