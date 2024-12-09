package com.nicholson.client_reservation_vol.donnée.http

import android.util.Log
import com.google.gson.stream.JsonWriter
import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonnéesRéservation
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import com.nicholson.client_reservation_vol.donnée.http.décodeur.DécodeurJSONRéservation
import com.nicholson.client_reservation_vol.donnée.http.décodeur.DécodeurJSONVol
import com.nicholson.client_reservation_vol.donnée.http.encodeur.EncodeurJSONClient
import com.nicholson.client_reservation_vol.donnée.http.encodeur.EncodeurJSONRéservation
import com.nicholson.client_reservation_vol.donnée.http.exception.AuthentificationException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter

class SourceDeDonnéesRéservationHttp( val urlApi : String ) : ISourceDeDonnéesRéservation {
    override suspend fun obtenirListeRéservation(): List<Réservation> {
        val urlRequête = "$urlApi/reservations"

        try{
            val client = ClientHttp.obtenirInstance()
            val requête = Request.Builder()
                .url( urlRequête )
                .get()
                .build()

            val réponse = client.newCall( requête ).execute()
            if ( réponse.code == 200 ) {
                return DécodeurJSONRéservation.décoderListeRéservation( réponse.body!!.string() )
            } else {
                throw SourceDeDonnéesException("Code : ${réponse.code}, url : $urlRequête")
            }
        }
        catch( ex : IOException ) {
            throw SourceDeDonnéesException("Erreur inconnue : ${ex.message}")
        }
    }

    override suspend fun obtenirRéservationParId(id: Int): Réservation {
        val urlRequête = "$urlApi/reservations/$id"

        try{
            val client = ClientHttp.obtenirInstance()
            val requête = Request.Builder()
                .url( urlRequête )
                .get()
                .build()

            val réponse = client.newCall( requête ).execute()
            if ( réponse.code == 200 ) {
                return DécodeurJSONRéservation.décoderRéservation( réponse.body!!.string() )
            } else {
                throw SourceDeDonnéesException("Code : ${réponse.code}, url : $urlRequête")
            }
        }
        catch( ex : IOException ) {
            throw SourceDeDonnéesException("Erreur inconnue : ${ex.message}")
        }
    }

    override suspend fun ajouterRéservation(réservation: Réservation) {
        val urlRequête = "$urlApi/reservations"

        try {
            val clientHttp = ClientHttp.obtenirInstance()

            val output = ByteArrayOutputStream()
            val writer = JsonWriter( OutputStreamWriter( output ) )

            EncodeurJSONRéservation.encoderRéservation( writer, réservation )
            writer.close()

            val corpsDeRequête = output
                .toString()
                .toRequestBody( "application/json".toMediaTypeOrNull() )

            val requête = Request.Builder()
                .url( urlRequête )
                .post( corpsDeRequête )
                .build()

            Log.d("corp de requet", output.toString())
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

}