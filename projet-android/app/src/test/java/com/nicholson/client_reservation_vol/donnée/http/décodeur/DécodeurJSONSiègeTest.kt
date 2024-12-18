package com.nicholson.client_reservation_vol.donnée.http.décodeur

import com.google.gson.stream.JsonReader
import com.nicholson.client_reservation_vol.domaine.entité.Siège
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import org.junit.Assert.assertEquals
import kotlin.test.Test
import kotlin.test.assertFailsWith
import java.io.StringReader

class DécodeurJSONSiègeTest {

    @Test
    fun `Étant donné un JSON valide, lorsqu'on le décode, on obtient un objet Siège correct`() {
        val json = """
            {
                "id": 1,
                "numéroSiège": "12A",
                "classe": "économique",
                "statut": "disponible"
            }
        """
        val résultatAttendu = Siège(1, "12A", "économique", "disponible")
        val reader = JsonReader(StringReader(json))

        val résultatObtenu = DécodeurJSONSiège.décoderSiege(reader)

        assertEquals(résultatAttendu, résultatObtenu)
    }

    @Test
    fun `Étant donné un JSON invalide, lorsqu'on le décode, on obtient une exception de type SourceDeDonnéesException `() {
        val json = """
            { "id": 1, "numéroSiège": "12A",
        """
        val reader = JsonReader(StringReader(json))

        assertFailsWith<SourceDeDonnéesException>("Format JSON invalide") {
            DécodeurJSONSiège.décoderSiege(reader)
        }
    }

    @Test
    fun `Étant donné un JSON avec un champ manquant, lorsqu'on le décode, on obtient une exception de type SourceDeDonnéesException `() {
        val json = """
            {
                "id": 1,
                "numéroSiège": "12A"
            }
        """
        val reader = JsonReader(StringReader(json))

        assertFailsWith<UninitializedPropertyAccessException>("Le champ 'classe' est manquant") {
            DécodeurJSONSiège.décoderSiege(reader)
        }
    }

    @Test
    fun `Étant donné un JSON avec des champs supplémentaires, lorsqu'on le décode, on obtient un objet Siège correct`() {
        val json = """
            {
                "id": 1,
                "numéroSiège": "12A",
                "classe": "économique",
                "statut": "disponible",
                "extra": "Ignoré"
            }
        """
        val résultatAttendu = Siège(1, "12A", "économique", "disponible")
        val reader = JsonReader(StringReader(json))

        val résultatObtenu = DécodeurJSONSiège.décoderSiege(reader)

        assertEquals(résultatAttendu, résultatObtenu)
    }
}
