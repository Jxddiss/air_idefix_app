package com.nicholson.client_reservation_vol.présentation.RéservationSpécifique

import com.nicholson.client_reservation_vol.présentation.OTD.RéservationSpécifiqueOTD

interface ContratVuePrésentateurRéservationSpécifique {
    interface IRéservationSpécifiqueVue{
        fun miseEnPlace( réservationSpécifiqueOTD: RéservationSpécifiqueOTD )
        fun redirigerModifier()
    }
    interface IRéservationSpécifiquePrésentateur{
        fun traiterDémarage()
        fun traiterModifier()
    }
}