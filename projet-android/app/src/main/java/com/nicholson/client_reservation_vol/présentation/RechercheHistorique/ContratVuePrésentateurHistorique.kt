package com.nicholson.client_reservation_vol.présentation.RechercheHistorique

import com.nicholson.client_reservation_vol.présentation.OTD.HistoriqueListItemOTD

interface ContratVuePrésentateurHistorique {
    interface IListeDeHistoriqueVue  {
        fun afficherHistorique( listeDeHistorique: List<HistoriqueListItemOTD> )
        fun redirigerVersRechercherUnVolVue()
    }

    interface IListeDeHistoriquePrésentateur {
        fun traiterObtenirHistorique()
        fun traiterHistoriqueCliqué(indice : Int)
    }
}