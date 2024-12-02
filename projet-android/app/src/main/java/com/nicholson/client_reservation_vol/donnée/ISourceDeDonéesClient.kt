package com.nicholson.client_reservation_vol.donnée

import com.nicholson.client_reservation_vol.domaine.entité.Client

interface ISourceDeDonéesClient {
    fun ajouterClient( client: Client )
    fun obtenirListeClient() : MutableList<Client>
    fun obtenirClient( id : Int ) : Client?
    fun obtenirClientCourrant() : Client
}