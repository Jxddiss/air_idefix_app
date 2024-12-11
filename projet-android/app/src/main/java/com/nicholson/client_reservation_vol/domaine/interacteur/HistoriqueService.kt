package com.nicholson.client_reservation_vol.domaine.interacteur

import android.content.Context
import android.util.Log
import com.nicholson.client_reservation_vol.domaine.entité.Historique
import com.nicholson.client_reservation_vol.donnée.DataBase.SourceDeDonnéesLocalImpl
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonnéesHistorique
import com.nicholson.client_reservation_vol.donnée.SourceDeDonnées
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheHistorique

class HistoriqueService(private val sourceDeDonnées: ISourceDeDonnéesHistorique) {


    fun obtenirListeHistorique(): List<Historique> {
        return sourceDeDonnées.obtenirListHistorique()
    }

    fun ajouterHistorique(historique: Historique) {
        sourceDeDonnées.ajouterHistorique(historique)
    }

    fun supprimerHistorique(historique: Historique) {
        sourceDeDonnées.supprimerHistorique(historique)
    }
}