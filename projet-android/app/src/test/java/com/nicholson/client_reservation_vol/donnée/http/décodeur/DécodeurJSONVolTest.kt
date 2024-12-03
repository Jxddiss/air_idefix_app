package com.nicholson.client_reservation_vol.donnée.http.décodeur

import com.nicholson.client_reservation_vol.domaine.entité.Aeroport
import com.nicholson.client_reservation_vol.domaine.entité.Avion
import com.nicholson.client_reservation_vol.domaine.entité.Ville
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.domaine.entité.VolStatut
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import org.junit.Assert.*
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.time.Duration

class DécodeurJSONVolTest{
    private val vol = Vol(
        id = 1,
        numeroVol = "AF123",
        aeroportDebut = Aeroport(
            id = 1, code = "CDG", nom = "Charles de Gaulle",
            ville = Ville(1, "Paris", "France", "https://example.com/paris.jpg"),
            pays = "France"
        ),
        aeroportFin = Aeroport(
            id = 2, code = "JFK", nom = "John F. Kennedy",
            ville = Ville(2, "New York", "USA", "https://example.com/nyc.jpg"),
            pays = "USA"
        ),
        dateDepart = LocalDateTime.parse("2024-12-01T10:00:00"),
        dateArrivee = LocalDateTime.parse("2024-12-01T14:00:00"),
        avion = Avion(1, "Commercial"),
        prixParClasse = mapOf("Économique" to 500.0, "Affaire" to 1000.0, "Première" to 2000.0),
        poidsMaxBag = 30,
        statutVol = listOf(VolStatut(1, "En vol", LocalTime.parse("11:00:00"))),
        durée = Duration.parse("PT4H")
    )

    @Test
    fun `Étant donné un JSON valide avec une liste de vols, lorsqu'on le décode, on obtient une liste d'objets Vol`() {
        val json = """
            [
                {
                    "id": 1,
                    "trajet": {
                        "numéroTrajet": "AF123",
                        "aéroportDébut": {
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
                        "aéroportFin": {
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
                    },
                    "dateDepart": "2024-12-01T10:00:00",
                    "dateArrivee": "2024-12-01T14:00:00",
                    "avion": {
                        "id": 1,
                        "type": "Commercial"
                    },
                    "prixParClasse": {
                        "économique": 500.0,
                        "affaire": 1000.0,
                        "première": 2000.0
                    },
                    "poidsMaxBag": 30,
                    "vol_statut": [
                        {
                            "idVol": 1,
                            "statut": "En vol",
                            "heure": "2024-12-01T11:00:00"
                        }
                    ],
                    "durée": "PT4H"
                }
            ]
        """

        val résultat_attendue = listOf( vol )

        val résultat_obtenu = DécodeurJSONVol.décodéListeVols(json)

        assertEquals(résultat_attendue, résultat_obtenu)
    }

    @Test
    fun `Étant donné un JSON invalide pour une liste de vols, lorsqu'on le décode, on obtient une exception de type SourceDeDonnéesException`() {
        val json = """
            [
                {
                    "id": 1,
                    "trajet": {
                        "numéroTrajet": "AF123"
                    }
        """
        assertFailsWith<SourceDeDonnéesException>("Format JSON invalide") {
            DécodeurJSONVol.décodéListeVols(json)
        }
    }

    @Test
    fun `Étant donné un JSON valide pour un vol unique, lorsqu'on le décode, on obtient un objet Vol`() {
        val json = """
            {
                "id": 1,
                "trajet": {
                    "numéroTrajet": "AF123",
                    "aéroportDébut": {
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
                    "aéroportFin": {
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
                },
                "dateDepart": "2024-12-01T10:00:00",
                "dateArrivee": "2024-12-01T14:00:00",
                "avion": {
                    "id": 1,
                    "type": "Commercial"
                },
                "prixParClasse": {
                    "économique": 500.0,
                    "affaire": 1000.0,
                    "première": 2000.0
                },
                "poidsMaxBag": 30,
                "vol_statut": [
                    {
                        "idVol": 1,
                        "statut": "En vol",
                        "heure": "2024-12-01T11:00:00"
                    }
                ],
                "durée": "PT4H"
            }
        """

        val résultat_attendue = vol

        val résultat_obtenu = DécodeurJSONVol.décodéVol(json)

        assertEquals(résultat_attendue, résultat_obtenu)
    }
}