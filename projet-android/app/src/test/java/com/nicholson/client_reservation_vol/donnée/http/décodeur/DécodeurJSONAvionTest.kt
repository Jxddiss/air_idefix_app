package com.nicholson.client_reservation_vol.donnée.http.décodeur

import com.google.gson.stream.JsonReader
import com.nicholson.client_reservation_vol.domaine.entité.Avion
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import com.nicholson.client_reservation_vol.donnée.http.décodeur.DécodeurJSONAvion.Companion.décoderAvion
import org.junit.Assert.*
import java.io.StringReader
import kotlin.test.Test
import kotlin.test.assertFailsWith

class DécodeurJSONAvionTest {

    @Test
    fun `Étant donné un JSON valide, lorsqu'on le décode, on obtient un objet Avion correct`() {
        val json = """
            {
                "id": 42,
                "type": "Commercial"
            }
        """
        val résultat_attendu = Avion( 42, "Commercial" )
        val reader = JsonReader( StringReader( json ) )

        val résultat_obtenue = décoderAvion( reader )

        assertEquals( résultat_attendu, résultat_obtenue )
    }

    @Test
    fun `Étant donné un JSON invalide, lorsqu'on le décode, on obtient une exception`() {

        val json = """
            { "id": 42, "type": "Commercial",
        """
        val reader = JsonReader( StringReader( json ) )

        assertFailsWith<SourceDeDonnéesException>("Format JSON invalide") {
            décoderAvion(reader)
        }
    }

    @Test
    fun `Étant donné un JSON avec un champ manquant, lorsqu'on le décode, on obtient un objet Avion partiel`() {
        val json = """
            {
                "id": 42
            }
        """
        val résultat_attendu = Avion( 42, "" )
        val reader = JsonReader( StringReader( json ) )

        val résultat_obtenue = décoderAvion( reader )

        assertEquals( résultat_attendu, résultat_obtenue )
    }

    @Test
    fun `Étant donné un JSON avec des champs supplémentaires, lorsqu'on le décode, on obtient un objet Avion correct`() {
        val json = """
            {
                "id": 42,
                "type": "Commercial",
                "extra": "ignoré"
            }
        """
        val résultat_attendu = Avion( 42, "Commercial" )
        val reader = JsonReader( StringReader( json ) )

        val résultat_obtenue = décoderAvion(reader)

        assertEquals( résultat_attendu, résultat_obtenue )
    }
}