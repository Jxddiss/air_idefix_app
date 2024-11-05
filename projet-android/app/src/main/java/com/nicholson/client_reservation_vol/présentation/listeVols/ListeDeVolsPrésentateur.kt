package com.nicholson.client_reservation_vol.présentation.listeVols
import com.nicholson.client_reservation_vol.présentation.listeVols.ContratVuePrésentateurListeVols.*

class ListeDeVolsPrésentateur (
    val vue : IListeDeVolsVue = ListeDeVolsVue(),
    val modèle : ListeDeVolsModèle = ListeDeVolsModèle()
) :  IListeDeVolsPrésentateur{
    override fun traiterObtenirVols() {
        vue.afficherVols( modèle.obtenirVols() )
    }
}