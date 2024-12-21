package com.nicholson.client_reservation_vol.présentation.RechercheHistorique

import com.nicholson.client_reservation_vol.présentation.OTD.HistoriqueListItemOTD

interface ContratVuePrésentateurHistorique {
    interface IListeDeHistoriqueVue  {
        fun afficherHistorique( listeDeHistorique: List<HistoriqueListItemOTD> )
        fun redirigerVersRechercherUnVolVue()
        fun supprimerHistorique(indice: Int)
    }

    interface IListeDeHistoriquePrésentateur {
        fun traiterObtenirHistorique()
        fun traiterHistoriqueCliqué(indice : Int)
        fun traiterSupprimerHistorique(indice: Int)
    }
}