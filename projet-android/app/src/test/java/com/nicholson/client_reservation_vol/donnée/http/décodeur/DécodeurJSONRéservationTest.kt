package com.nicholson.client_reservation_vol.donnée.http.décodeur

import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.domaine.entité.Siège
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import org.junit.Assert.*
import kotlin.test.Test
import kotlin.test.assertFailsWith

class DécodeurJSONRéservationTest{
    @Test
    fun `Étant donné un JSON valide représentant une réservation, lorsqu'on le décode, on obtient une réservation avec les propriétés correspondantes`(){
        val json ="""
           {
                "id": 1,
                "numéroRéservation": "RES001",
                "idVol": 1,
                "client": {
                    "id": 1,
                    "nom": "Dubois",
                    "prénom": "Jean",
                    "adresse": "123 rue de Paris, Paris",
                    "numéroPasseport": "FR123456",
                    "email": "jean.dubois@email.com",
                    "numéroTéléphone": "+33123456789"
                },
                "siège": {
                    "id": 9,
                    "numéroSiège": "C2",
                    "classe": "économique",
                    "statut": "occupé"
                },
                "classe": "économique",
                "bagages": 1
            }
            """
        val résultat_attendu = Réservation(
            id = 1,
            numéroRéservation = "RES001",
            idVol = 1,
            client = Client(
                id = 1,
                nom = "Dubois",
                prénom = "Jean",
                adresse = "123 rue de Paris, Paris",
                numéroPasseport = "FR123456",
                email = "jean.dubois@email.com",
                numéroTéléphone = "+33123456789"
            ),
            siège = Siège(
                id = 9,
                numéro = "C2",
                classe = "économique",
                statut = "occupé",
            ),
            classe = "économique"
        )

        val résultat_obtenue = DécodeurJSONRéservation.décoderRéservation( json )

        assertEquals( résultat_attendu, résultat_obtenue )

    }

    @Test
    fun `Étant donné un JSON invalide, lorsqu'on le décode, on obtient une exception`() {
        val json = """
            { "id": 1, "numéroRéservation": "RES001",
        """
        assertFailsWith<SourceDeDonnéesException>("Format JSON invalide") {
            DécodeurJSONRéservation.décoderRéservation(json)
        }
    }

    @Test
    fun `Étant donné un JSON avec des champs manquants, lorsqu'on le décode, on obtient une exception`() {
        val json = """
            {
                "id": 1,
                "numéroRéservation": "RES001"
            }
        """
        assertFailsWith<UninitializedPropertyAccessException>("Champs manquants dans le JSON") {
            DécodeurJSONRéservation.décoderRéservation(json)
        }
    }

    @Test
    fun `Étant donné un JSON avec des champs supplémentaires, lorsqu'on le décode, on obtient une réservation correcte`() {
        val json = """
            {
                "id": 1,
                "numéroRéservation": "RES001",
                "idVol": 1,
                "client": {
                    "id": 1,
                    "nom": "Dubois",
                    "prénom": "Jean",
                    "adresse": "123 rue de Paris, Paris",
                    "numéroPasseport": "FR123456",
                    "email": "jean.dubois@email.com",
                    "numéroTéléphone": "+33123456789"
                },
                "siège": {
                    "id": 9,
                    "numéroSiège": "C2",
                    "classe": "économique",
                    "statut": "occupé"
                },
                "classe": "économique",
                "bagages": 1,
                "extra": "ignorer"
            }
        """
        val résultatAttendu = Réservation(
            id = 1,
            numéroRéservation = "RES001",
            idVol = 1,
            client = Client(
                id = 1,
                nom = "Dubois",
                prénom = "Jean",
                adresse = "123 rue de Paris, Paris",
                numéroPasseport = "FR123456",
                email = "jean.dubois@email.com",
                numéroTéléphone = "+33123456789"
            ),
            siège = Siège(
                id = 9,
                numéro = "C2",
                classe = "économique",
                statut = "occupé",
            ),
            classe = "économique"
        )

        val résultatObtenu = DécodeurJSONRéservation.décoderRéservation(json)

        assertEquals(résultatAttendu, résultatObtenu)
    }

    @Test
    fun `Étant donné un JSON avec un format incorrect pour un champ, lorsqu'on le décode, on obtient une exception`() {
        val json = """
            {
                "id": "abc",
                "numéroRéservation": "RES001",
                "idVol": 1,
                "client": {
                    "id": 1,
                    "nom": "Dubois",
                    "prénom": "Jean",
                    "adresse": "123 rue de Paris, Paris",
                    "numéroPasseport": "FR123456",
                    "email": "jean.dubois@email.com",
                    "numéroTéléphone": "+33123456789"
                },
                "siège": {
                    "id": 9,
                    "numéroSiège": "C2",
                    "classe": "économique",
                    "statut": "occupé"
                },
                "classe": "économique"
            }
        """
        assertFailsWith<SourceDeDonnéesException>("Format JSON invalide") {
            DécodeurJSONRéservation.décoderRéservation(json)
        }
    }

    @Test
    fun `Étant donné un JSON avec un siège partiellement défini, lorsqu'on le décode, on obtient une exception`() {
        val json = """
            {
                "id": 1,
                "numéroRéservation": "RES001",
                "idVol": 1,
                "client": {
                    "id": 1,
                    "nom": "Dubois",
                    "prénom": "Jean",
                    "adresse": "123 rue de Paris, Paris",
                    "numéroPasseport": "FR123456",
                    "email": "jean.dubois@email.com",
                    "numéroTéléphone": "+33123456789"
                },
                "siège": {
                    "id": 9
                },
                "classe": "économique"
            }
        """
        assertFailsWith<UninitializedPropertyAccessException>("Champs manquants dans l'objet Siège") {
            DécodeurJSONRéservation.décoderRéservation(json)
        }
    }

}