package com.nicholson.client_reservation_vol.présentation.RechercheHistorique

import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheHistorique
import com.nicholson.client_reservation_vol.présentation.OTD.HistoriqueListItemOTD
import java.time.LocalDate

interface ContratVuePrésentateurHistorique {
    interface IListeDeHistoriqueVue  {
        fun afficherHistorique( listeDeHistorique : List<HistoriqueListItemOTD> )
    }

    interface IListeDeHistoriquePrésentateur {
        fun traiterObtenirHistorique(filtre: FiltreRechercheHistorique)
    }
}