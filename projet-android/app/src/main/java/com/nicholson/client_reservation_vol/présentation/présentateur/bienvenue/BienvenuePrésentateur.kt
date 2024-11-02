package com.nicholson.client_reservation_vol.présentation.présentateur.bienvenue
import com.nicholson.client_reservation_vol.présentation.présentateur.bienvenue.ContratVuePrésentateurBienvenue.*
import com.nicholson.client_reservation_vol.présentation.vue.BienvenueVue

class BienvenuePrésentateur( var vue : IBienvenueVue = BienvenueVue() ) : IBienvenuePrésentateur {
    override fun traiterDemandeRedirectionListeReservations() {
        vue.redirigerAListeReservation()
    }

    override fun traiterDemandeRedirectionRechercherUnVol() {
        vue.redirigerARechercherUnVol()
    }
}