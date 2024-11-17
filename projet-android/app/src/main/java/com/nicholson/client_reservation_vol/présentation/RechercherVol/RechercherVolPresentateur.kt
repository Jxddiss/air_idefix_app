package com.nicholson.client_reservation_vol.présentation.RechercherVol

import com.nicholson.client_reservation_vol.domaine.entité.Ville
import com.nicholson.client_reservation_vol.présentation.Modèle

class RechercherVolPresentateur {
    private val modèle: Modèle = Modèle.obtenirInstance()
    //J'ai utilisa ca pour optenier la liste de villes dans le quele je veux afficher dans ma vue RecherhcherUnVolVue
    fun obtenirListeVilles(): List<Ville> {
        return modèle.obtenirListeVilles()
    }
}