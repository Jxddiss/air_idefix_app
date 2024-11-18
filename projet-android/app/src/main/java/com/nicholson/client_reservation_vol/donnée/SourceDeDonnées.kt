package com.nicholson.client_reservation_vol.donnée

import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheVol

interface SourceDeDonnées {
    fun obtenirListeVol() : List<Vol>
    fun obtenirListeVolParFiltre( filtre : FiltreRechercheVol ) : List<Vol>
    fun obtenirVolParId( id : Int ) : Vol
    fun getListRéservation() : MutableList<Réservation>
    fun ajouterClient(client: Client)
    fun obtenirListeClient():MutableList<Client>
}
