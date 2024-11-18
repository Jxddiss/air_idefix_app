package com.nicholson.client_reservation_vol.domaine.interacteur

import com.nicholson.client_reservation_vol.domaine.entité.Historique
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.donnée.SourceDeDonnées
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheHistorique
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheVol

class VolService( private val sourceDeDonnées: SourceDeDonnées = SourceDonnéesFictive() ) {
    fun obtenirListeVol() = sourceDeDonnées.obtenirListeVol()

    fun obtenirListeVolParFiltre( filtre : FiltreRechercheVol ) : List<Vol> =
        sourceDeDonnées.obtenirListeVolParFiltre( filtre )

    fun obtenirVolParId( id : Int ) =
        sourceDeDonnées.obtenirVolParId( id )

}