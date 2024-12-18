package com.nicholson.client_reservation_vol.présentation.bienvenue

interface ContratVuePrésentateurBienvenue {
    interface IBienvenueVue {
        fun redirigerAListeReservation()
        fun redirigerARechercherUnVol()
        fun afficherMessageErreur()
        fun montrerDéconnexion()
        fun cacherDéconnexion()
        fun seDéconnecter( réussite: () -> Unit, échec : ( String ) -> Unit )
        fun obtenirToken() : String?
    }

    interface IBienvenuePrésentateur {
        fun traiterDemandeRedirectionListeReservations()
        fun traiterDemandeRedirectionRechercherUnVol()
        fun traiterDémarage()
        fun traiterDéconnexion()
    }
}