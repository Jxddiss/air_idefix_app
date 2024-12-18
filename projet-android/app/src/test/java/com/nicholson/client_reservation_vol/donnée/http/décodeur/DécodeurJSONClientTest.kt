package com.nicholson.client_reservation_vol.donnée.http.décodeur

import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import org.junit.Assert.*
import kotlin.test.Test
import kotlin.test.assertFailsWith

class DécodeurJSONClientTest{
    @Test
    fun `Étant donné un JSON valide représentant un client, lorsqu'on le décode, on obtient un Client avec les propriétés correspondantes`() {
        val json = """
            {
                "id": 1,
                "nom": "Dubois",
                "prénom": "Jean",
                "adresse": "123 rue de Paris, Paris",
                "numéroPasseport": "FR123456",
                "email": "jean.dubois@email.com",
                "numéroTéléphone": "+33123456789"
            }
        """
        val résultat_attendu = Client(
            id = 1,
            nom = "Dubois",
            prénom = "Jean",
            adresse = "123 rue de Paris, Paris",
            numéroPasseport = "FR123456",
            email = "jean.dubois@email.com",
            numéroTéléphone = "+33123456789"
        )

        val résultat_obtenue = DécodeurJSONClient.décoderClient( json )

        assertEquals( résultat_attendu, résultat_obtenue )
    }

    @Test
    fun `Étant donné un JSON valide représentant un client avec des champs optionnels manquants, lorsqu'on le décode, on obtient un Client avec les propriétés partiellement renseignées`() {
        val json = """
            {
                "id": 1,
                "nom": "Dubois",
                "prénom": "Jean",
                "adresse": "123 rue de Paris, Paris",
                "numéroPasseport": "FR123456",
                "email": "jean.dubois@email.com"
            }
        """
        val résultat_attendu = Client(
            id = 1,
            nom = "Dubois",
            prénom = "Jean",
            adresse = "123 rue de Paris, Paris",
            numéroPasseport = "FR123456",
            email = "jean.dubois@email.com",
            numéroTéléphone = null
        )

        val résultat_obtenue = DécodeurJSONClient.décoderClient( json )

        assertEquals( résultat_attendu, résultat_obtenue )
    }

    @Test
    fun `Étant donné un JSON invalide, lorsqu'on tente de le décoder, on obtient une exception de type SourceDeDonnéesException indiquant un format JSON invalide`() {
        val json = """
            { "id": 1, "nom": "Dubois", "prénom": "Jean", "adresse": "123 rue de Paris, Paris", "numéroPasseport": "FR123456", "email": "jean.dubois@email.com"
        """

        assertFailsWith<SourceDeDonnéesException>("Format JSON invalide") {
            DécodeurJSONClient.décoderClient(json)
        }
    }
}