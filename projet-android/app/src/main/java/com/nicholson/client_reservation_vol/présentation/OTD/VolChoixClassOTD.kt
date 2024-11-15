package com.nicholson.client_reservation_vol.présentation.OTD

data class VolChoixClassOTD(
    val dateDépart : String,
    val heureDépart: String,
    val heureArrivée : String,
    val prixÉconomique : String,
    val prixAffaire : String,
    val prixPremière : String,
    val nomVilleDépart : String,
    val nomVilleArrivée : String,
    val urlPhotoVilleArrivé : String,
    val codeAéroportArrivée : String,
    val codeAéroportDépart : String,
    val durée : String
)