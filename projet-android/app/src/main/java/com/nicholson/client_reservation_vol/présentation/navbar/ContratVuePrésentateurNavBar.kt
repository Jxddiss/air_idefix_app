package com.nicholson.client_reservation_vol.présentation.navbar

import androidx.appcompat.app.AppCompatActivity

interface ContratVuePrésentateurNavBar {
    interface INavbarVue{
        fun miseEnPlace()
        fun redirigerÀMesRéservation()
        fun redirigerÀBienvenue()
        fun redirigerÀHistorique()
        fun obtenirPageCourrante() : String?
        fun afficherPagePrecedente()
    }

    interface INavBarPrésentateur {
        fun traiterDémarage()
        fun traiterRedirigerÀMesRéservation()
        fun traiterRedirigerÀBienvenue()
        fun traiterRedirigerÀHistorique()
        fun traiterRetour()
    }
}