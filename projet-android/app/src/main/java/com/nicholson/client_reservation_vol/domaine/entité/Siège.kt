package com.nicholson.client_reservation_vol.domaine.entité

data class Siège(
    val id : Int,
    val numéro: String,
    val classe: String,
    val statut: String,
    var idRéservation: Int,
    var idVol : Int
)

