package com.nicholson.client_reservation_vol.présentation.OTD

import java.time.LocalDateTime

data class FiltreRechercheVol(
    val dateDébut : LocalDateTime,
    val codeAéroportDébut : String,
    val codeAéroportFin : String
)