package com.nicholson.client_reservation_vol.domaine.entité

import java.time.LocalTime

data class VolStatut(
    val idVol : Int,
    val statut : String,
    val heure : LocalTime
)

