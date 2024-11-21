package com.nicholson.client_reservation_vol.présentation.listeVols

import com.nicholson.client_reservation_vol.présentation.OTD.VolListItemOTD

interface ContratVuePrésentateurListeVols {
    interface IListeDeVolsVue {
        fun afficherVols( listeDeVols : List<VolListItemOTD> )
        fun afficherInfoDestination( nomVille: String, urlImage: String )
        fun afficherMessagePasDeVol()
        fun redirigerVersChoixClasse()
    }

    interface IListeDeVolsPrésentateur {
        fun traiterObtenirVols()
        fun traiterVolCliqué( index : Int )
    }
}