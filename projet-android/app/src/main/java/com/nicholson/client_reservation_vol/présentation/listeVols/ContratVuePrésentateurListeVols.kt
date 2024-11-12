package com.nicholson.client_reservation_vol.présentation.listeVols

import com.nicholson.client_reservation_vol.présentation.OTD.VolListItemOTD

class ContratVuePrésentateurListeVols {
    interface IListeDeVolsVue {
        fun afficherVols( listeDeVols : List<VolListItemOTD> )
        fun afficherInfoDestination( nomVille: String, urlImage: String )
    }

    interface IListeDeVolsPrésentateur {
        fun traiterObtenirVols()
    }
}