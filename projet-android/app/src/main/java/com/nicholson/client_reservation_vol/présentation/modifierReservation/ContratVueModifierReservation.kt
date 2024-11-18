package com.nicholson.client_reservation_vol.présentation.modifierReservation

interface ContratVueModifierReservation {
    interface IModifierReservationVue {
        fun redirigerAChoisirSiege()
        fun redirigerAChoisirClasse()
        fun redirigerAChoisirInformation()
    }

    interface IModifierReservationPrésentateur {
        fun traiterDemandeRedirectionChoisirSiege()
        fun traiterDemandeRedirectionChoisirClasse()
        fun traiterDemandeRedirectionChoisirInformation()
    }
}
