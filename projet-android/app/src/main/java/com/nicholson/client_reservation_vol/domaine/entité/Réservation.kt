package com.nicholson.client_reservation_vol.domaine.entité

data class Réservation (
    var id : Int,
    var numéroRéservation: String,
    val idVol: Int,
    var client: Client?,
    val siège: Siège?,
    val classe: String = "Économique"
)
