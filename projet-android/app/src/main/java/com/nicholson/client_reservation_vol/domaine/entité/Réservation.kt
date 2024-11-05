package com.nicholson.client_reservation_vol.domaine.entité

data class Réservation (
    val id : Int,
    val numéroRéservation: String,
    val idVol: Int,
    val clients: List<Client>,
    val sièges: List<Siège>,
)
