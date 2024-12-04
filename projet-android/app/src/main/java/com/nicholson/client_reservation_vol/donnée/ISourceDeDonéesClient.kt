package com.nicholson.client_reservation_vol.donnée

import com.nicholson.client_reservation_vol.domaine.entité.Client

interface ISourceDeDonéesClient {
    suspend fun ajouterClient( client: Client )
    suspend fun obtenirClient( id : Int ) : Client?
    suspend fun obtenirClientCourrant() : Client
}