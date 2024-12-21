package com.nicholson.client_reservation_vol.présentation.modifierReservation

import com.nicholson.client_reservation_vol.présentation.OTD.ClientOTD

interface ContratVueModifierReservation {
    interface IModifierReservationVue {
        fun miseEnPlace(clientOTD: ClientOTD)
        fun seConnecter( réussite: ( String ) -> Unit, échec : ( String ) -> Unit )
        fun montrerChargement()
        fun masquerChargement()
        fun redirigerBienvenueErreur()
    }

    interface IModifierReservationPrésentateur {
        fun traiterDémarage()

    }
}
