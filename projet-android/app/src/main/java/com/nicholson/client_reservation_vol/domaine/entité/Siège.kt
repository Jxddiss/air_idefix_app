package com.nicholson.client_reservation_vol.domaine.entité

data class Siège(
    val numéro: String,
    val classe: String,
    val statut: String,
    val avion_id: Int,
    val idRéservation: Int
)

