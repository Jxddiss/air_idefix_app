package com.nicholson.client_reservation_vol.domaine.entité

data class Aeroport(
    val code : String,
    val nom : String,
    val ville : Ville,
    val pays : String
)

