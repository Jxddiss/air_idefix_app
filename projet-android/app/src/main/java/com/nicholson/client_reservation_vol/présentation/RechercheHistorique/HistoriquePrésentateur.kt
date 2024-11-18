package com.nicholson.client_reservation_vol.présentation.RechercheHistorique

import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheHistorique
import com.nicholson.client_reservation_vol.présentation.OTD.HistoriqueListItemOTD


class HistoriquePrésentateur (
    val vue: ContratVuePrésentateurHistorique.IListeDeHistoriqueVue = HistoriqueRechercheVue()
) : ContratVuePrésentateurHistorique.IListeDeHistoriquePrésentateur {

    val modèle: Modèle = Modèle.obtenirInstance()

    override fun traiterObtenirHistorique() {
        val listeDeHistorique = modèle.listeHistorique

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
}