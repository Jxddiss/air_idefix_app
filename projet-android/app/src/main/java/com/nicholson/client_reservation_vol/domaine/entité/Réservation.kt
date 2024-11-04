package com.nicholson.client_reservation_vol.domaine.entité

data class Réservation (
    val idRéservation : Int,
    val numéroRéservation: String,
    val idVol: String,
    val clients: List<Client>,
    val sièges: List<Siège>,
)
