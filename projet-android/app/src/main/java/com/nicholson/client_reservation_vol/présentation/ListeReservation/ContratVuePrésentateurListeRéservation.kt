package com.nicholson.client_reservation_vol.présentation.ListeReservation

import android.view.View
import com.nicholson.client_reservation_vol.présentation.OTD.RéservationListItemOTD
import com.nicholson.client_reservation_vol.présentation.OTD.VolListItemOTD


class ContratVuePrésentateurListeRéservation {
    interface IListeDeRéservationsVue {
        fun afficherRéservations( listeDeReservation : MutableList<RéservationListItemOTD>)//, listeDeVols : MutableList<VolListItemOTD>?, vue : View?)
        fun redirigerPageRéservationSpécifique();
        fun redirigerVueRechercherVol();
    }

    interface IListeDeRéservationsPrésentateur{
        fun traiterObtenirRéservation();
        fun traiterRéservationCliqué();
        fun traiterBtnRechercheVolCliqué();
    }
}