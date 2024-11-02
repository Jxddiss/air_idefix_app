package com.nicholson.client_reservation_vol.présentation.bienvenue
import com.nicholson.client_reservation_vol.présentation.bienvenue.ContratVuePrésentateurBienvenue.*

class BienvenuePrésentateur( var vue : IBienvenueVue = BienvenueVue() ) : IBienvenuePrésentateur {
    override fun traiterDemandeRedirectionListeReservations() {
        vue.redirigerAListeReservation()
    }

    override fun traiterDemandeRedirectionRechercherUnVol() {
        vue.redirigerARechercherUnVol()
    }
}