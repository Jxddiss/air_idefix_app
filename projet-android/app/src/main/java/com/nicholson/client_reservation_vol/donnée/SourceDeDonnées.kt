package com.nicholson.client_reservation_vol.donnée

import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheVol

interface SourceDeDonnées {
    fun obtenirListeVol() : List<Vol>
    fun obtenirListeVolParFiltre( filtre : FiltreRechercheVol ) : List<Vol>
    fun obtenirVolParId( id : Int ) : Vol
    fun obtenirListRéservation() : MutableList<Réservation>
    fun obtenirReservationParId( id : Int): Réservation
}