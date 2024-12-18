package com.nicholson.client_reservation_vol.donnée.http.décodeur

import android.util.JsonReader
import com.nicholson.client_reservation_vol.domaine.entité.Aeroport
import com.nicholson.client_reservation_vol.domaine.entité.Ville
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import org.junit.Assert.*
import java.io.StringReader
import kotlin.test.Test
import kotlin.test.assertFailsWith

class DécodeurJSONAéroportTest{
    @Test
    fun `Étant donné un JSON valide avec une liste d'aéroports, lorsqu'on le décode, on obtient une liste d'objets Aéroport`() {
        val json = """
            [
                {
                    "id": 1,
                    "code": "CDG",
                    "nom": "Charles de Gaulle",
                    "ville": {
                        "id": 1,
                        "nom": "Paris",
                        "pays": "France",
                        "url_photo": "https://example.com/paris.jpg"
                    }
                },
                {
                    "id": 2,
                    "code": "JFK",
                    "nom": "John F. Kennedy",
                    "ville": {
                        "id": 2,
                        "nom": "New York",
                        "pays": "USA",
                        "url_photo": "https://example.com/nyc.jpg"
                    }
                }
            ]
        """
        val résultat_attendu = listOf(
            Aeroport(
                1,
                "CDG",
                "Charles de Gaulle",
                Ville(1,
                    "Paris",
                    "France",
                    "https://example.com/paris.jpg"),
                "France"),
            Aeroport(2,
                "JFK",
                "John F. Kennedy",
                Ville(2,
                    "New York",
                    "USA",
                    "https://example.com/nyc.jpg"),
                "USA")
        )

        val résultat_obtenu = DécodeurJSONAéroport.décoderListeAéroports(json)

        assertEquals(résultat_attendu, résultat_obtenu)
    }

    @Test
    fun `Étant donné un JSON invalide avec une liste d'aéroports, lorsqu'on le décode, on obtient une exception de type SourceDeDonnéesException`() {
        val json = """
            [
                {
                    "id": 1,
                    "code": "CDG",
                    "nom": "Charles de Gaulle",
                    "ville": {
                        "id": 1,
                        "nom": "Paris",
                        "pays": "France",
                        "url_photo": "https://example.com/paris.jpg"
                    }
                },
        """
        assertFailsWith<SourceDeDonnéesException>("Format JSON invalide") {
            DécodeurJSONAéroport.décoderListeAéroports(json)
        }
    }

    @Test
    fun `Étant donné un JSON valide pour un aéroport, lorsqu'on le décode, on obtient un objet Aéroport correct`() {
        val json = """
            {
                "id": 1,
                "code": "CDG",
                "nom": "Charles de Gaulle",
                "ville": {
                    "id": 1,
                    "nom": "Paris",
                    "pays": "France",
                    "url_photo": "https://example.com/paris.jpg"
                }
            }
        """
        val reader = JsonReader(StringReader(json))
        val résultat_attendu = Aeroport(
            1,
            "CDG", "Charles de Gaulle",
            Ville(1,
                "Paris",
                "France",
                "https://example.com/paris.jpg"),
            "France")

        val résultat_obtenu = DécodeurJSONAéroport.décoderAeroport(reader)

        assertEquals(résultat_attendu, résultat_obtenu)
    }

    @Test
    fun `Étant donné un JSON avec des champs supplémentaires pour un aéroport, lorsqu'on le décode, on obtient un objet Aéroport correct`() {
        val json = """
            {
                "id": 1,
                "code": "CDG",
                "nom": "Charles de Gaulle",
                "ville": {
                    "id": 1,
                    "nom": "Paris",
                    "pays": "France",
                    "url_photo": "https://example.com/paris.jpg"
                },
                "extra": "ignored"
            }
        """
        val reader = JsonReader(StringReader(json))
        val résultat_attendu = Aeroport(1,
            "CDG",
            "Charles de Gaulle",
            Ville(1,
                "Paris",
                "France",
                "https://example.com/paris.jpg"),
            "France")

        val résultat_obtenu = DécodeurJSONAéroport.décoderAeroport(reader)

        assertEquals(résultat_attendu, résultat_obtenu)
    }

    @Test
    fun `Étant donné un JSON invalide pour un aéroport, lorsqu'on le décode, on obtient une exception de type SourceDeDonnéesException`() {
        val json = """
            {
                "id": 1,
                "code": "CDG",
                "nom": "Charles de Gaulle",
                "ville": 
        """
        val reader = JsonReader(StringReader(json))

        assertFailsWith<SourceDeDonnéesException>("Format JSON invalide") {
            DécodeurJSONAéroport.décoderAeroport(reader)
        }
    }
}