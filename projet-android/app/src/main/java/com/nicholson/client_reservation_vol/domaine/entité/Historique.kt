package com.nicholson.client_reservation_vol.domaine.entité

import java.time.LocalDate

data class Historique(
    val villeDe:String,
    val villeVers:String,
    val aeroportDe:String,
    val aeroportVers:String,
    val dateDepart:LocalDate,
    val dateRetour:LocalDate,
    val nbrPassangers:Int
)
