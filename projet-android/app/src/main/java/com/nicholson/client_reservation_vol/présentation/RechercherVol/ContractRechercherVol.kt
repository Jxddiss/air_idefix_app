package com.nicholson.client_reservation_vol.présentation.RechercherVol

interface ContractRechercherVol {
    interface IRechercheVolVue {
        fun afficherListeVilles(villes: List<String>)
        fun redirigerVersListeVols()
    }

    interface IRechercheVolVuePrésentateur {
        fun attacherVue(vue: IRechercheVolVue)
        fun détacherVue()
        fun obtenirListeVilles()
    }
}