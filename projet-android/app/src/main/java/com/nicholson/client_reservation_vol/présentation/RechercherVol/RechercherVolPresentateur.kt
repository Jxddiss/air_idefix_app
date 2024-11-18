package com.nicholson.client_reservation_vol.présentation.RechercherVol

import com.nicholson.client_reservation_vol.domaine.entité.Ville
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.RechercherVol.ContractRechercherVol.*



class RechercherVolPresentateur:  ContractRechercherVol.IRechercheVolVuePrésentateur {

    private val modèle: Modèle = Modèle.obtenirInstance()
    private var vue: ContractRechercherVol.IRechercheVolVue? = null

    override fun attacherVue(vue: ContractRechercherVol.IRechercheVolVue) {
        this.vue = vue
    }

    override fun détacherVue() {
        vue = null
    }

    override fun obtenirListeVilles() {
        val aéroportsAvecCodes = modèle.obtenirListeAéroports().map { "${it.ville.nom} (${it.code})" }
        vue?.afficherListeVilles(aéroportsAvecCodes)
    }
}