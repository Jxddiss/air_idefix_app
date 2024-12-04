package com.nicholson.client_reservation_vol.donnée.http

import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonéesClient
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import com.nicholson.client_reservation_vol.donnée.http.décodeur.DécodeurJSONClient
import okhttp3.Request
import okio.IOException

class SourceDeDonnéesClientHttp( val urlApi : String ) : ISourceDeDonéesClient {
    override suspend fun ajouterClient( client: Client ) {
        TODO("Not yet implemented")
    }

    override suspend fun obtenirClient( id: Int ): Client {
        val urlRequête = "$urlApi/clients/$id"

        try {
            val client = ClientHttp.obtenirInstance()
            val requête = Request.Builder()
                .url( urlRequête )
                .get()
                .build()

            val réponse = client.newCall( requête ).execute()
            if ( réponse.code == 200 ) {
                val corpsDeRéponse = réponse.body?.string()

                if ( corpsDeRéponse != null ) {
                    return DécodeurJSONClient.décoderClient( corpsDeRéponse )
                } else {
                    throw SourceDeDonnéesException( "Corps de réponse vide" )
                }
            } else {
                throw SourceDeDonnéesException( "Code : ${réponse.code}" )
            }
        } catch( ex : IOException ) {
            throw SourceDeDonnéesException( "Erreur inconnue : ${ex.message}" )
        }
    }

    override suspend fun obtenirClientCourrant(): Client {
        val urlRequête = "$urlApi/clients/profile"

        try {
            val client = ClientHttp.obtenirInstance()
            val requête = Request.Builder()
                .url( urlRequête )
                .get()
                .build()

            val réponse = client.newCall( requête ).execute()
            if ( réponse.code == 200 ) {
                val corpsDeRéponse = réponse.body?.string()

                if ( corpsDeRéponse != null ) {
                    return DécodeurJSONClient.décoderClient( corpsDeRéponse )
                } else {
                    throw SourceDeDonnéesException( "Corps de réponse vide" )
                }
            } else {
                throw SourceDeDonnéesException( "Code : ${réponse.code}" )
            }
        } catch( ex : IOException ) {
            throw SourceDeDonnéesException( "Erreur inconnue : ${ex.message}" )
        }
    }
}