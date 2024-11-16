package com.nicholson.client_reservation_vol.présentation.choisirclasse

import com.nicholson.client_reservation_vol.présentation.OTD.VolChoixClassOTD

interface ContratVuePrésentateurChoisirClasse {
    interface IChoisirClasseVue{
        fun miseEnPlace( volChoixClassOTD : VolChoixClassOTD )
        fun obtenirChoixClasse() : String
        fun redirigerChoixInfo()
    }

    interface IChoisirClassePrésentateur{
        fun traiterDémarage()
        fun traiterContinuer()
    }
}