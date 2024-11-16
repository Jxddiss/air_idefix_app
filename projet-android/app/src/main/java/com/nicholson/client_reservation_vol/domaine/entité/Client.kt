package com.nicholson.client_reservation_vol.domaine.entité

data class Client(
    val id : Int,
    var nom : String,
    var prénom : String,
    var adresse : String,
    val numéroPasseport : String,
    var email : String?,
    var numéroTéléphone : String?,
)

