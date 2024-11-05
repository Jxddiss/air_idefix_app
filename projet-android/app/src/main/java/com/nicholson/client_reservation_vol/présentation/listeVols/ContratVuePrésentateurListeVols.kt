package com.nicholson.client_reservation_vol.présentation.listeVols

import android.view.View
import com.nicholson.client_reservation_vol.domaine.entité.Vol

class ContratVuePrésentateurListeVols {
    interface IListeDeVolsVue {
        fun afficherVols( listeDeVols : MutableList<Vol> )
    }

    interface IListeDeVolsPrésentateur {
        fun traiterObtenirVols()
    }
}