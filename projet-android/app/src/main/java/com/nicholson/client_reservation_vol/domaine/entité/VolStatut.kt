package com.nicholson.client_reservation_vol.domaine.entité

import java.time.LocalTime

data class VolStatut(
    val numéroVol : String,
    val Statut : String,
    val heure : LocalTime
)

