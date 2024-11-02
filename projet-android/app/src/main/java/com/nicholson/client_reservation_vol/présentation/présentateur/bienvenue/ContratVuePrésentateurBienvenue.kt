package com.nicholson.client_reservation_vol.présentation.présentateur.bienvenue

interface ContratVuePrésentateurBienvenue {
    interface IBienvenueVue {
        fun redirigerAListeReservation()
        fun redirigerARechercherUnVol()
    }

    interface IBienvenuePrésentateur {
        fun traiterDemandeRedirectionListeReservations()
        fun traiterDemandeRedirectionRechercherUnVol()
    }
}