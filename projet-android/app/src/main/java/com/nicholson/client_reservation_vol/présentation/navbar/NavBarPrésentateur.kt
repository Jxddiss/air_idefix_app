package com.nicholson.client_reservation_vol.présentation.navbar

import com.nicholson.client_reservation_vol.présentation.navbar.ContratVuePrésentateurNavBar.*


class NavBarPrésentateur(private val vue : INavbarVue = NavBarVue() ) : INavBarPrésentateur {
    override fun traiterDémarage() {
        vue.miseEnPlace()
    }

    override fun traiterRedirigerÀMesRéservation() {
        vue.redirigerÀMesRéservation()
    }

    override fun traiterRedirigerÀBienvenue() {
        vue.redirigerÀBienvenue()
    }

    override fun traiterRedirigerÀHistorique() {
        vue.redirigerÀHistorique()
    }

}