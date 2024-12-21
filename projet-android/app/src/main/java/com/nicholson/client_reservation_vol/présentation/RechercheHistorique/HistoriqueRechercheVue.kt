package com.nicholson.client_reservation_vol.présentation.RechercheHistorique

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
import com.nicholson.client_reservation_vol.présentation.OTD.HistoriqueListItemOTD
import com.nicholson.client_reservation_vol.présentation.RechercheHistorique.ContratVuePrésentateurHistorique.*


class HistoriqueRechercheVue : Fragment(), IListeDeHistoriqueVue {

    private lateinit var recyclerView: RecyclerView
    private lateinit var rechercheHistoriqueAdapter: HistoriqueRechercheAdapter
    private lateinit var historiquePrésentateur: IListeDeHistoriquePrésentateur
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historiquePrésentateur = HistoriquePrésentateur(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_historique_list)
        navController = findNavController()

        historiquePrésentateur.traiterObtenirHistorique()
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
            rechercheHistoriqueAdapter = HistoriqueRechercheAdapter(listeDeHistorique,
                onItemClick = {
                    historiquePrésentateur.traiterHistoriqueCliqué(it)
                },
                onDeleteClick = { position ->
                    // Appelle le preseteur pour delete the item
                    historiquePrésentateur.traiterSupprimerHistorique(position)
                }
            )
        }
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = rechercheHistoriqueAdapter
        }


    override fun redirigerVersRechercherUnVolVue() {
        navController.navigate(R.id.action_historiqueRechercheVue_to_rechercherUnVolVue)
    }

    override fun supprimerHistorique(indice: Int) {
        rechercheHistoriqueAdapter.supprimerItem(indice)
    }

}


