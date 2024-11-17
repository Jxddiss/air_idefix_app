package com.nicholson.client_reservation_vol.présentation.RechercherVol

import com.nicholson.client_reservation_vol.domaine.entité.Ville
import com.nicholson.client_reservation_vol.présentation.Modèle

class RechercherVolPresentateur:  ContractRechercherVol.Présentateur {

    private val modèle: Modèle = Modèle.obtenirInstance()
    private var vue: ContractRechercherVol.Vue? = null

    override fun attacherVue(vue: ContractRechercherVol.Vue) {
        this.vue = vue
    }

    override fun détacherVue() {
        vue = null
    }

    override fun obtenirListeVilles() {
        val villes = modèle.obtenirListeVilles().map { it.nom }
        vue?.afficherListeVilles(villes)
    }
}