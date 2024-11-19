package com.nicholson.client_reservation_vol.présentation.RechercherVol

import com.nicholson.client_reservation_vol.domaine.entité.Aeroport

interface ContractRechercherVol {
    interface IRechercheVolVue {
        fun afficherListeVilles(villes: List<String>)
        fun redirigerVersListeVols()
        fun obtenirInfoRecherche()
        fun afficherToast(message: String)
    }

    interface IRechercheVolVuePrésentateur {
        fun attacherVue(vue: IRechercheVolVue)
        fun détacherVue()
        fun obtenirListeVilles()
        fun traiterInfoRecherche(villeAeroportDe:String, villeAeroportVers:String, dateDebutString:String, dateRetour:String, nbrPassagers:String)
        fun traiterActionRecherche()
    }
}