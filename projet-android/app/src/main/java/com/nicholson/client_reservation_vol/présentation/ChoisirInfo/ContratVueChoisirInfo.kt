package com.nicholson.client_reservation_vol.présentation.ChoisirInfo

import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.présentation.OTD.ClientOTD

interface ContratVueChoisirInfo {
    interface IChoisirInfoVue {
        fun miseEnPlace( nomVilleDépart : String,
                         nomVilleArrivée: String,
                         urlPhoto : String,
                         clientOTD: ClientOTD )
        fun obtenirInfoClient()
        fun redirigerAChoisirSiege()
        fun afficherMessageErreur(message: String)
    }

    interface IChoisirInfoPrésentateur {
        fun traiterDémarage()
        fun traiterObtenirInfo( clientOTD: ClientOTD )
        fun traiterDemandeRedirectionChoisirSiege()
    }
}
