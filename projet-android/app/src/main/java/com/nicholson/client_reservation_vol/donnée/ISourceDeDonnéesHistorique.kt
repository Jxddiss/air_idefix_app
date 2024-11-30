package com.nicholson.client_reservation_vol.donnée

import com.nicholson.client_reservation_vol.domaine.entité.Historique

interface ISourceDeDonnéesHistorique {
    fun obtenirListHistorique():List<Historique>
    fun ajouterHistorique(historique: Historique)
}