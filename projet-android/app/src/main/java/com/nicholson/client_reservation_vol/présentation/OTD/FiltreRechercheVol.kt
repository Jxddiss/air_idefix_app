package com.nicholson.client_reservation_vol.présentation.OTD

import com.nicholson.client_reservation_vol.domaine.entité.Aeroport
import java.time.LocalDateTime

data class FiltreRechercheVol(
    val dateDébut : LocalDateTime,
    val aéroportDébut : Aeroport,
    val aéroportFin : Aeroport
)