package com.nicholson.client_reservation_vol.domaine.entité

data class Avion (
    val id: Int,
    val type: String,
    val sièges: List<Siège>,
    val idVol: Int
)

