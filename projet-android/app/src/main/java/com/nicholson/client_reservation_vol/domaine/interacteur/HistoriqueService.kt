package com.nicholson.client_reservation_vol.domaine.interacteur

import com.nicholson.client_reservation_vol.domaine.entité.Historique
import com.nicholson.client_reservation_vol.donnée.SourceDeDonnées
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheHistorique

class HistoriqueService(private val sourceDeDonnées: SourceDeDonnées = SourceDonnéesFictive()){

    fun obtenirListeHistorique() = sourceDeDonnées.obtenirListHistorique()

    fun ajouterHistorique( historique: Historique ) = sourceDeDonnées.ajouterHistorique( historique )
}