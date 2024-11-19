package com.nicholson.client_reservation_vol.présentation.RechercheHistorique

import android.util.Log
import com.nicholson.client_reservation_vol.domaine.entité.Historique
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive.Companion.listHistorique
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheHistorique
import com.nicholson.client_reservation_vol.présentation.OTD.HistoriqueListItemOTD


class HistoriquePrésentateur (
    val vue: ContratVuePrésentateurHistorique.IListeDeHistoriqueVue = HistoriqueRechercheVue()
) : ContratVuePrésentateurHistorique.IListeDeHistoriquePrésentateur {

    val modèle: Modèle = Modèle.obtenirInstance()

    override fun traiterObtenirHistorique() {
        val listeDeHistorique = getHistoriqueList()
        Log.d("HistoriquePrésentateur", "Historique list size: ${listeDeHistorique.size}")
        listeDeHistorique.forEach { Log.d("HistoriquePrésentateur", "Historique item: $it") }

        val listeHistoriqueOTD = listeDeHistorique.map {
            HistoriqueListItemOTD(
                villeDe = it.villeDe,
                villeVers = it.villeVers,
                aeroportDe = it.aeroportDe,
                aeroportVers = it.aeroportVers,
                dateDepart = it.dateDepart,
                dateRetour = it.dateRetour,
                nbrPassangers = it.nbrPassangers
            )
        }


        vue.afficherHistorique(listeHistoriqueOTD)
    }

    fun getHistoriqueList(): List<Historique> {
        return listHistorique
    }

    private fun convertirEnHistoriqueOTD(historique: Historique): HistoriqueListItemOTD {
        return HistoriqueListItemOTD(
            villeDe = historique.villeDe,
            villeVers = historique.villeVers,
            aeroportDe = historique.aeroportDe,
            aeroportVers = historique.aeroportVers,
            dateDepart = historique.dateDepart,
            dateRetour = historique.dateRetour,
            nbrPassangers = historique.nbrPassangers
        )
    }

}