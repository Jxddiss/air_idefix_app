package com.nicholson.client_reservation_vol.donnée.http

import com.google.gson.stream.JsonWriter
import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonéesClient
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import com.nicholson.client_reservation_vol.donnée.http.décodeur.DécodeurJSONClient
import com.nicholson.client_reservation_vol.donnée.http.encodeur.EncodeurJSONClient
import com.nicholson.client_reservation_vol.donnée.http.exception.AuthentificationException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter

class SourceDeDonnéesClientHttp( val urlApi : String ) : ISourceDeDonéesClient {
    override suspend fun modifierClient( client: Client ) {
        val urlRequête = "$urlApi/clients/${client.id}"

        try {
            val clientHttp = ClientHttp.obtenirInstance()

            val output = ByteArrayOutputStream()
            val writer = JsonWriter( OutputStreamWriter( output ) )

            EncodeurJSONClient.encoderClient( writer, client )
            writer.close()

            val corpsDeRequête = output.toString()
                .toRequestBody( "application/json".toMediaTypeOrNull() )

            val requête = Request.Builder()
                .url( urlRequête )
                .put( corpsDeRequête )
                .build()

            val réponse = clientHttp.newCall( requête ).execute()

            if ( réponse.code == 401 ){
                throw AuthentificationException("Vous n'ète pas connecté")
            } else if ( réponse.code !in 200..299 ) {
                throw SourceDeDonnéesException( "Code : ${réponse.code}" )
            }
        } catch ( ex : IOException ) {
            throw SourceDeDonnéesException( "Erreur inconnue : ${ex.message}" )
        }
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
            } else if ( réponse.code == 401 ){
                throw AuthentificationException("Vous n'ète pas connecté")
            }  else {
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
            } else if ( réponse.code == 401 ){
                throw AuthentificationException("Vous n'ète pas connecté")
            } else {
                throw SourceDeDonnéesException( "Code : ${réponse.code}" )
            }
        } catch( ex : IOException ) {
            throw SourceDeDonnéesException( "Erreur inconnue : ${ex.message}" )
        }
    }
}