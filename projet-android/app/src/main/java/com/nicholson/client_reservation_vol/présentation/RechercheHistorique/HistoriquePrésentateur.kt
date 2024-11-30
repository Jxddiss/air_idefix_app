package com.nicholson.client_reservation_vol.présentation.RechercheHistorique

import android.content.Context
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

    // pass le context ici!
    fun initialiserContexte(context: Context) {
        modèle.initialiserSourceDeDonnées(context)
    }
    override fun traiterObtenirHistorique() {
        val listeDeHistorique = modèle.getSourceDeDonnées().obtenirListHistorique()
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

}