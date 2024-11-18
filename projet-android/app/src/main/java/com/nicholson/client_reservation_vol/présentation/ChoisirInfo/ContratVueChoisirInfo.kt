package com.nicholson.client_reservation_vol.présentation.ChoisirInfo

import com.nicholson.client_reservation_vol.domaine.entité.Client

interface ContratVueChoisirInfo {
    interface IChoisirInfoVue {
        fun redirigerAChoisirSiege()
    }

    interface IChoisirInfoPrésentateur {
        fun traiterDemandeRedirectionChoisirSiege()
    }
}
