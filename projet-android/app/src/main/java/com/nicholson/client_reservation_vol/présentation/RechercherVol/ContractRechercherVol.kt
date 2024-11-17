package com.nicholson.client_reservation_vol.présentation.RechercherVol

interface ContractRechercherVol {
    interface Vue {
        fun afficherListeVilles(villes: List<String>)
    }

    interface Présentateur {
        fun attacherVue(vue: Vue)
        fun détacherVue()
        fun obtenirListeVilles()
    }
}