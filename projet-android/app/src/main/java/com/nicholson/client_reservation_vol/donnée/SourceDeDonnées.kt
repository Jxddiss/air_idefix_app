package com.nicholson.client_reservation_vol.donnée

import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.domaine.entité.Vol

interface SourceDeDonnées {
    fun getListeVol() : List<Vol>
    fun getListRéservation() : MutableList<Réservation>
}