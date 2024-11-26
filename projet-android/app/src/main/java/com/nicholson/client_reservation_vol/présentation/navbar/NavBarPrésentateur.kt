package com.nicholson.client_reservation_vol.présentation.navbar

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.navbar.ContratVuePrésentateurNavBar.*


class NavBarPrésentateur(private val vue : INavbarVue = NavBarVue()) : INavBarPrésentateur {
    private val modèle = Modèle.obtenirInstance()

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

    override fun traiterRetour() {
        modèle.pageCourrante = vue.obtenirPageCourrante()
        if(modèle.pageCourrante == "fragment_liste_de_vols_vue" && !modèle.aller){
            modèle.aller = true
            modèle.volRetourExiste = true
        }
        else if(modèle.pageCourrante == "fragment_choisir_siege_vue" && !modèle.siegeVolAller){
            modèle.siegeVolAller = true
        }

        Log.d("page courrante dans le model", modèle.pageCourrante.toString())
        vue.afficherPagePrecedente()
    }


}