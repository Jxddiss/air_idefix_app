package com.nicholson.client_reservation_vol.donnée.fictive

import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonnéesVols
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheVol

class SourceDonnéeVolsFictive : ISourceDeDonnéesVols {
    override fun obtenirListeVolParFiltre(filtre: FiltreRechercheVol): List<Vol>  =
        SourceDonnéesFictive.listVol.filter {
            it.dateDepart >= filtre.dateDébut && it.dateDepart < filtre.dateDébut.plusDays(30)
                    && it.aeroportDebut.code == filtre.codeAéroportDébut
                    && it.aeroportFin.code == filtre.codeAéroportFin
        }.sortedBy { it.dateDepart }

    override fun obtenirVolParId(id: Int): Vol =
        SourceDonnéesFictive.listVol.single {
            it.id == id
        }
}