package com.nicholson.client_reservation_vol.donnée.http.décodeur

import android.util.JsonReader
import com.nicholson.client_reservation_vol.domaine.entité.Ville
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import com.nicholson.client_reservation_vol.donnée.http.décodeur.DécodeurJSONVille.Companion.décoderVille
import org.junit.Assert.*
import java.io.StringReader
import kotlin.test.Test
import kotlin.test.assertFailsWith

class DécodeurJSONVilleTest{
    @Test
    fun `Étant donné un JSON valide, lorsqu'on le décode, on obtient un objet Ville correct`() {
        val json = """
            {
                "id": 1,
                "nom": "Paris",
                "pays": "France",
                "url_photo": "https://example.com/paris.jpg"
            }
        """
        val résultat_attendu = Ville(1, "Paris", "France", "https://example.com/paris.jpg")
        val reader = JsonReader(StringReader(json))

        val résultat_obtenu = décoderVille(reader)

        assertEquals(résultat_attendu, résultat_obtenu)
    }

    @Test
    fun `Étant donné un JSON invalide, lorsqu'on le décode, on obtient une exception de type SourceDeDonnéesException`() {
        val json = """
            { "id": 1, "nom": "Paris", "pays": "France", "url_photo":
        """
        val reader = JsonReader(StringReader(json))

        assertFailsWith<SourceDeDonnéesException>("Format JSON invalide") {
            décoderVille(reader)
        }
    }

    @Test
    fun `Étant donné un JSON avec un champ manquant, lorsqu'on le décode, on obtient un objet Ville partiel`() {
        val json = """
            {
                "id": 1,
                "nom": "Paris",
                "pays": "France"
            }
        """
        val résultat_attendu = Ville(1, "Paris", "France", "")
        val reader = JsonReader(StringReader(json))

        val résultat_obtenu = décoderVille(reader)

        assertEquals(résultat_attendu, résultat_obtenu)
    }

    @Test
    fun `Étant donné un JSON avec des champs supplémentaires, lorsqu'on le décode, on obtient un objet Ville correct`() {
        val json = """
            {
                "id": 1,
                "nom": "Paris",
                "pays": "France",
                "url_photo": "https://example.com/paris.jpg",
                "extra": "ignoré"
            }
        """
        val résultat_attendu = Ville(1, "Paris", "France", "https://example.com/paris.jpg")
        val reader = JsonReader(StringReader(json))

        val résultat_obtenu = décoderVille(reader)

        assertEquals(résultat_attendu, résultat_obtenu)
    }
}