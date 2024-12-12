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
                val corpsDeRéponse = réponse.body?.string()
                réponse.body?.close()
                if( corpsDeRéponse != null ){
                    return DécodeurJSONAéroport.décoderListeAéroports( corpsDeRéponse )
                } else {
                    throw SourceDeDonnéesException( "Corps de réponse vide" )
                }
            } else {
                throw SourceDeDonnéesException("Code : ${réponse.code}")
            }
        } catch( ex : IOException ) {
            throw SourceDeDonnéesException("Erreur inconnue : ${ex.message}")
        }
    }
}