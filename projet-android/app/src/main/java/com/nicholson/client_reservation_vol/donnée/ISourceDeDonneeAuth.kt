package com.nicholson.client_reservation_vol.donnée

interface ISourceDeDonneeAuth {
    suspend fun obtenirToken() : String
    suspend fun seDeconnecter() : Boolean
}