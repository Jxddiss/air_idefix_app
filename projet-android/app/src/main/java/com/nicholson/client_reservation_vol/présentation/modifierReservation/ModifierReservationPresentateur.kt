package com.nicholson.client_reservation_vol.présentation.modifierReservation

import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.ClientOTD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ModifierReservationPresentateur(
    var vue: ContratVueModifierReservation.IModifierReservationVue = ModifierReservationVue(),
    private val iocontext : CoroutineContext = Dispatchers.IO
) : ContratVueModifierReservation.IModifierReservationPrésentateur {
    private val model = Modèle.obtenirInstance()
    private var job : Job? = null

    override fun traiterDémarage() {
        job = CoroutineScope( iocontext ).launch {
            val client = model.obtenirReservationCourrante().client
            val clientOTD = ClientOTD(
                nom = client?.nom ?: "",
                prénom = client?.prénom ?: "",
                adresse = client?.adresse ?: "",
                numéroPasseport = client?.numéroPasseport ?: "",
                email = client?.email ?: "",
                téléphone = client?.numéroTéléphone ?: ""
            )
            CoroutineScope( Dispatchers.Main ).launch{
                vue.miseEnPlace(clientOTD)
            }
        }
    }
}
