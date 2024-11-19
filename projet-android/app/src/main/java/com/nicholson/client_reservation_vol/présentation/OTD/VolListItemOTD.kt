package com.nicholson.client_reservation_vol.présentation.OTD

import java.time.LocalDateTime

data class VolListItemOTD (
    val dateDépart : String,
    val heureDépart: String,
    val heureArrivée : String,
    val prixÉconomique : String,
    val nomVilleDépart : String,
    val nomVilleArrivée : String,
    val codeAéroportArrivée : String,
    val codeAéroportDépart : String,
    val durée : String
){
}