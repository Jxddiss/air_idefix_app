package com.nicholson.client_reservation_vol.présentation.RechercherVol

import com.nicholson.client_reservation_vol.présentation.OTD.HistoriqueListItemOTD

interface ContractRechercherVol {
    interface IRechercheVolVue {
        fun afficherListeVilles(villes: List<String>)
        fun redirigerVersListeVols()
        fun obtenirInfoRecherche()
        fun afficherToast(message: String)
        fun afficherHistorique(listeDeHistorique : HistoriqueListItemOTD )
        fun redirigerBienvenueErreur()
        fun montrerChargement()
    }

    interface IRechercheVolVuePrésentateur {
        fun attacherVue(vue: IRechercheVolVue)
        fun détacherVue()
        fun obtenirListeVilles()
        fun traiterInfoRecherche(villeAeroportDe:String, villeAeroportVers:String, dateDebutString:String, dateRetour:String )
        fun traiterActionRecherche()
        fun traiterObtenirHistorique()
    }
}