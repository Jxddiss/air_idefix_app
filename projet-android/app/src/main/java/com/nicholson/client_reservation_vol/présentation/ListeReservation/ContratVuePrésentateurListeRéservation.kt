package com.nicholson.client_reservation_vol.présentation.ListeReservation

import android.view.View
import com.nicholson.client_reservation_vol.présentation.OTD.RéservationListItemOTD
import com.nicholson.client_reservation_vol.présentation.OTD.VolListItemOTD


interface ContratVuePrésentateurListeRéservation {
    interface IListeDeRéservationsVue {
        fun afficherRéservations( listeDeReservation : MutableList<RéservationListItemOTD>)//, listeDeVols : MutableList<VolListItemOTD>?, vue : View?)
        fun redirigerPageRéservationSpécifique();
        fun redirigerVueRechercherVol();
        fun seConnecter( réussite: ( String ) -> Unit, échec : ( String ) -> Unit );
        fun montrerChargement()
        fun masquerChargement()
        fun redirigerBienvenueErreur()
    }

    interface IListeDeRéservationsPrésentateur{
        fun traiterObtenirRéservation();
        fun traiterRéservationCliqué( index : Int );
        fun traiterBtnRechercheVolCliqué();
    }
}