package com.nicholson.client_reservation_vol.présentation.modifierReservation

import com.nicholson.client_reservation_vol.présentation.OTD.ClientOTD

interface ContratVueModifierReservation {
    interface IModifierReservationVue {
        fun miseEnPlace(clientOTD: ClientOTD)
    }

    interface IModifierReservationPrésentateur {
        fun traiterDémarage()

    }
}
