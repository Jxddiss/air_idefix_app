package com.nicholson.client_reservation_vol.présentation.RéservationSpécifique

import com.nicholson.client_reservation_vol.présentation.OTD.RéservationSpécifiqueOTD

interface ContratVuePrésentateurRéservationSpécifique {
    interface IRéservationSpécifiqueVue{
        fun miseEnPlace( réservationSpécifiqueOTD: RéservationSpécifiqueOTD )
        fun redirigerModifier()
        fun ouvrirCalendrier(eventDetails: Map<String, Any>)
        fun afficherErreur(message: String)
        fun redirigerBienvenueErreur()
        fun seConnecter( réussite: ( String ) -> Unit, échec : ( String ) -> Unit )
        fun montrerChargement()
        fun masquerChargement()
    }
    interface IRéservationSpécifiquePrésentateur{
        fun traiterCalendrier(réservationSpécifiqueOTD: RéservationSpécifiqueOTD)
        fun traiterDémarage()
        fun traiterModifier()
    }
}