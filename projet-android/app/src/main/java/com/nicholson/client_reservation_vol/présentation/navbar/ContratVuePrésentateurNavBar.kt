package com.nicholson.client_reservation_vol.présentation.navbar

interface ContratVuePrésentateurNavBar {
    interface INavbarVue{
        fun miseEnPlace()
        fun redirigerÀMesRéservation()
        fun redirigerÀBienvenue()
        fun redirigerÀHistorique()
    }

    interface INavBarPrésentateur {
        fun traiterDémarage()
        fun traiterRedirigerÀMesRéservation()
        fun traiterRedirigerÀBienvenue()
        fun traiterRedirigerÀHistorique()
    }
}