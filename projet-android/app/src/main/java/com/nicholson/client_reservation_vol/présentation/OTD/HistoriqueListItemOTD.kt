package com.nicholson.client_reservation_vol.présentation.OTD

import java.time.LocalDate

data class HistoriqueListItemOTD(

    val villeDe:String,
    val villeVers:String,
    val aeroportDe:String,
    val aeroportVers:String,
    val dateDepart: LocalDate,
    val dateRetour: LocalDate?,
    val nbrPassangers:Int
)
