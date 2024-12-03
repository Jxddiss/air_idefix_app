package com.nicholson.client_reservation_vol.donnée

import com.nicholson.client_reservation_vol.domaine.entité.Aeroport

interface ISourceDeDonnéesAeroport {
    suspend fun obtenirListAéroports() : List<Aeroport>
}