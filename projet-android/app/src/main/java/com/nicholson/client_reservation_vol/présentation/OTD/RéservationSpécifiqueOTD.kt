package com.nicholson.client_reservation_vol.présentation.OTD


data class RéservationSpécifiqueOTD(
    val tempsRestant : String,
    val tempsUnite : String,
    val nomVille: String,
    val numéroRéservation: String,
    val heureDepart : String,
    val dateDepart : String,
    val heureArrivée : String,
    val dateArrivée : String,
    val url_photo : String,
    val classe : String,
    val siège : String,
    val durée : String,
    val barProgres : String
){}
