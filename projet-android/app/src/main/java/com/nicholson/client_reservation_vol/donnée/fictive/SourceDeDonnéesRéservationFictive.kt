package com.nicholson.client_reservation_vol.donnée.fictive

import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonnéesRéservation
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive.Companion.listeRéservation

class SourceDeDonnéesRéservationFictive: ISourceDeDonnéesRéservation {
    private val sourceFictive = SourceDonnéesFictive()

    override suspend fun obtenirListeRéservation(): MutableList<Réservation> {
        return listeRéservation
    }

    override suspend fun obtenirRéservationParId(id : Int): Réservation =
        listeRéservation.single {
            it.id == id
        }

    override suspend fun ajouterRéservation(réservation: Réservation) {
        réservation.id = listeRéservation.size + 1
        réservation.numéroRéservation = "RES00${listeRéservation.size + 1}"
        réservation.siège.let {
            it?.idRéservation = réservation.id
            it?.idVol = réservation.idVol
            if (it != null) {
                sourceFictive.obtenirVolParId( réservation.idVol )?.sièges?.add( it )
            }
        }
        listeRéservation.add( réservation )
    }

}