package com.nicholson.client_reservation_vol.donnée.http

import com.nicholson.client_reservation_vol.domaine.entité.Aeroport
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonnéesAeroport
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import com.nicholson.client_reservation_vol.donnée.http.décodeur.DécodeurJSONAéroport
import okhttp3.Request
import okio.IOException

class SourceDeDonnéesAeroportHttp( val urlApi : String ) : ISourceDeDonnéesAeroport {
    override suspend fun obtenirListAéroports(): List<Aeroport> {
        val urlRequête = "$urlApi/aeroports"

        try {
            val client = ClientHttp.obtenirInstance()
            val requête = Request.Builder()
                .url( urlRequête )
                .get()
                .build()

            val réponse = client.newCall( requête ).execute()
            if ( réponse.code == 200 ) {
                return DécodeurJSONAéroport.décoderListeAéroports( réponse.body!!.string() )
            } else {
                throw SourceDeDonnéesException("Code : ${réponse.code}")
            }
        } catch( ex : IOException ) {
            throw SourceDeDonnéesException("Erreur inconnue : ${ex.message}")
        }
    }
}