package com.nicholson.client_reservation_vol.présentation.OTD


data class RéservationListItemOTD(
    val tempsRestant : String,
    val tempsUnite : String,
    val destination: String,
    val dateDepart : String,
    val url_photo : String,
    val barProgres : String
){}
