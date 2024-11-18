package com.nicholson.client_reservation_vol.présentation.modifierReservation

import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.ClientOTD

class ModifierReservationPresentateur(
    var vue: ContratVueModifierReservation.IModifierReservationVue = ModifierReservationVue()
) : ContratVueModifierReservation.IModifierReservationPrésentateur {
    private val model = Modèle.obtenirInstance()
    override fun traiterDémarage() {
        val client = model.obtenirReservationCourrante().clients[0]
        val clientOTD = ClientOTD(
            nom = client.nom,
            prénom = client.prénom,
            adresse = client.adresse,
            numéroPasseport = client.numéroPasseport,
            email = client.email ?: "",
            téléphone = client.numéroTéléphone ?: ""
        )
        vue.miseEnPlace(clientOTD)
    }

}
