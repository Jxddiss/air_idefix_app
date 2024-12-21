package com.nicholson.client_reservation_vol.présentation.RechercheHistorique

import android.util.Log
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.HistoriqueListItemOTD
import com.nicholson.client_reservation_vol.présentation.RechercheHistorique.ContratVuePrésentateurHistorique.*

class HistoriquePrésentateur (
    val vue: IListeDeHistoriqueVue = HistoriqueRechercheVue()
) : IListeDeHistoriquePrésentateur {

    val modèle: Modèle = Modèle.obtenirInstance()

    override fun traiterObtenirHistorique() {
        val listeDeHistorique = modèle.listeHistorique
        listeDeHistorique.forEach { Log.d("HistoriquePrésentateur", "Historique item: $it") }

        val listeHistoriqueOTD = listeDeHistorique.map {
            HistoriqueListItemOTD(
                villeDe = it.villeDe,
                villeVers = it.villeVers,
                aeroportDe = it.aeroportDe,
                aeroportVers = it.aeroportVers,
                dateDepart = it.dateDepart,
                dateRetour = it.dateRetour
            )
        }

        vue.afficherHistorique(listeHistoriqueOTD)
    }

    override fun traiterHistoriqueCliqué(indice: Int) {
        if (indice in modèle.listeHistorique.indices) {
            modèle.historiqueCliqué = true
            modèle.indiceHistoriqueCourrant = indice
            vue.redirigerVersRechercherUnVolVue()
        }
    }

    override fun traiterSupprimerHistorique(indice: Int) {
        if (indice in modèle.listeHistorique.indices) {
            val historique = modèle.listeHistorique[indice]
            modèle.supprimerHistorique(historique)
            vue.supprimerHistorique(indice)
        }
    }


    }