package com.nicholson.client_reservation_vol.domaine.entité

import java.time.LocalDateTime
import kotlin.time.Duration


data class Vol(
    val id : Int,
    val numeroVol: String,
    val aeroportDebut: Aeroport,
    val aeroportFin: Aeroport,
    val dateDepart: LocalDateTime,
    val dateArrivee: LocalDateTime,
    val avion: Avion,
    val prixParClasse: Map<String, Double>,
    val poidsMaxBag: Int,
    val statutVol: List<VolStatut>,
    val durée: Duration,
    val sièges : List<Siège> = mutableListOf()
)
