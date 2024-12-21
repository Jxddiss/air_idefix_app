package com.nicholson.client_reservation_vol.donnée.fictive

import com.nicholson.client_reservation_vol.domaine.entité.Historique
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonnéesHistorique
import java.time.LocalDate

class SourceDeDonnéesHistoriqueFictive : ISourceDeDonnéesHistorique {
    companion object{
        val listHistorique = mutableListOf(
            Historique(
                villeDe="Montreal",
                villeVers="Cancun",
                aeroportDe = "YUL",
                aeroportVers = "BEN",
                dateDepart = LocalDate.of(2024, 11, 15),
                dateRetour = LocalDate.of(2024, 11, 22)

            ),
            Historique(
                villeDe = "Toronto",
                villeVers = "Paris",
                aeroportDe = "YYZ",
                aeroportVers = "CDG",
                dateDepart = LocalDate.of(2024, 11, 10),
                dateRetour = LocalDate.of(2024, 11, 17)
            ),
            Historique(
                villeDe = "Toronto",
                villeVers = "Paris",
                aeroportDe = "YYZ",
                aeroportVers = "CDG",
                dateDepart = LocalDate.of(2025, 1, 10),
                dateRetour = LocalDate.of(2025, 1, 17)
            ),
        )
    }

    override fun obtenirListHistorique():List<Historique> = listHistorique

    override fun ajouterHistorique(historique: Historique) {
        listHistorique.add(historique)
    }

    override fun supprimerHistorique(historique: Historique) {
        listHistorique.remove(historique)
    }

}