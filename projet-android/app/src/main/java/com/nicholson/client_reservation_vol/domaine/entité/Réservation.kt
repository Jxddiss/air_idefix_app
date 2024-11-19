package com.nicholson.client_reservation_vol.domaine.entité

data class Réservation (
    var id : Int,
    var numéroRéservation: String,
    val idVol: Int,
    val clients: List<Client>,
    val sièges: List<Siège>,
)
