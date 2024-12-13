package com.nicholson.client_reservation_vol.donnée.fictive

import com.nicholson.client_reservation_vol.domaine.entité.*
import com.nicholson.client_reservation_vol.donnée.SourceDeDonnées
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheHistorique
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheVol
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import kotlin.random.Random
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class SourceDonnéesFictive : SourceDeDonnées {
    companion object {
        val listClients = mutableListOf(
            Client(
                1,
                "Doe",
                "John",
                "123 Main St",
                "A12345678",
                "john.doe@example.com",
                "123-456-7890"
            ),
            Client(
                2,
                "Smith",
                "Emily",
                "456 Oak Avenue",
                "B87654321",
                "emily.smith@example.com",
                "987-654-3210"
            )
        )

        val listVille = mutableListOf(
            Ville(
                id = 1,
                nom = "Paris",
                pays = "FR",
                url_photo = "https://plus.unsplash.com/premium_photo-1661919210043-fd847a58522d?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            ),
            Ville(
                id = 2,
                nom = "New York",
                pays = "US",
                url_photo = "https://images.unsplash.com/photo-1516893842880-5d8aada7ac05?q=80&w=1964&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            ),
            Ville(
                id = 3,
                nom = "Tokyo",
                pays = "JP",
                url_photo = "https://images.unsplash.com/photo-1554797589-7241bb691973?q=80&w=1936&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            ),
            Ville(
                id = 4,
                nom = "Montréal",
                pays = "CN",
                url_photo = "https://images.unsplash.com/photo-1715191307694-ee4e57b473d6?q=80&w=2127&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            )
        )

        val listAeoroport = mutableListOf(
            Aeroport(
                1,
                "CDG",
                "Charles de Gaulle",
                listVille[0],
                "FR"
            ),
            Aeroport(
                2,
                "JFK",
                "John F. Kennedy International",
                listVille[1],
                "US"
            ),
            Aeroport(
                3,
                "NRT",
                "Narita",
                listVille[2],
                "JP"
            ),
            Aeroport(
                4,
                "YUL",
                "Pierre-Elliot-Trudeau",
                listVille[3],
                "CA"
            )
        )

        val listAvion = mutableListOf(
            Avion(1, "Boeing 737"),
            Avion(2, "Boeing 737"),
            Avion(3, "Airbus A320")
        )

        val listSièges = mutableListOf(
            Siège(
                id = 1,
                numéro = "A1",
                classe = "Économique",
                statut = "occupée",
                idRéservation = 1,
                idVol = 1
            ),
            Siège(
                id = 2,
                numéro = "B2",
                classe = "Économique",
                statut = "libre",
                idRéservation = 0,
                idVol = 1
            ),
            Siège(
                id = 3,
                numéro = "C3",
                classe = "Affaire",
                statut = "libre",
                idRéservation = 0,
                idVol = 2
            ),
            Siège(
                id = 4,
                numéro = "D4",
                classe = "Première",
                statut = "occupée",
                idRéservation = 2,
                idVol = 2
            ),
            Siège(
                id = 5,
                numéro = "C3",
                classe = "Affaire",
                statut = "libre",
                idRéservation = 0,
                idVol = 3
            ),
            Siège(
                id = 6,
                numéro = "D4",
                classe = "Première",
                statut = "occupée",
                idRéservation = 2,
                idVol = 3
            ),
            Siège(
                id = 7,
                numéro = "C3",
                classe = "Affaire",
                statut = "libre",
                idRéservation = 0,
                idVol = 4
            ),
            Siège(
                id = 8,
                numéro = "D4",
                classe = "Première",
                statut = "occupée",
                idRéservation = 2,
                idVol = 4
            ),
            Siège(
                id = 9,
                numéro = "C3",
                classe = "Affaire",
                statut = "libre",
                idRéservation = 0,
                idVol = 5
            ),
            Siège(
                id = 10,
                numéro = "D4",
                classe = "Première",
                statut = "occupée",
                idRéservation = 2,
                idVol = 5
            )
        )

        val listVol = mutableListOf(
            Vol(
                id = 1,
                numeroVol = "AD001",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[1],
                dateDepart = LocalDateTime.now().plusDays(5).plusHours(2),
                dateArrivee = LocalDateTime.now().plusDays(5).plusHours(10),
                avion = listAvion[0],
                prixParClasse = mapOf(
                    "Économique" to 250.50,
                    "Affaire" to 750.75,
                    "Première" to 1500.00
                ),
                poidsMaxBag = 20,
                statutVol = listOf(
                    VolStatut(
                        idVol = 1,
                        statut = "en attente",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 8.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter { it.idVol == 1 }.toMutableList()
            ),
            Vol(
                id = 2,
                numeroVol = "AD002",
                aeroportDebut = listAeoroport[1],
                aeroportFin = listAeoroport[2],
                dateDepart = LocalDateTime.now().plusDays(7).plusHours(3),
                dateArrivee = LocalDateTime.now().plusDays(7).plusHours(12),
                avion = listAvion[1],
                prixParClasse = mapOf(
                    "Économique" to 300.75,
                    "Affaire" to 850.25,
                    "Première" to 1750.00
                ),
                poidsMaxBag = 25,
                statutVol = listOf(
                    VolStatut(
                        idVol = 2,
                        statut = "confirmé",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 9.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter { it.idVol == 2 }.toMutableList()
            ),
            Vol(
                id = 3,
                numeroVol = "AD002",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[2],
                dateDepart = LocalDateTime.now().plusDays(1).plusHours(3),
                dateArrivee = LocalDateTime.now().plusDays(1).plusHours(12),
                avion = listAvion[1],
                prixParClasse = mapOf(
                    "Économique" to 300.75,
                    "Affaire" to 850.25,
                    "Première" to 1750.00
                ),
                poidsMaxBag = 25,
                statutVol = listOf(
                    VolStatut(
                        idVol = 2,
                        statut = "confirmé",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 9.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter { it.idVol == 3 }.toMutableList()
            ),
            Vol(
                id = 4,
                numeroVol = "AD002",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[2],
                dateDepart = LocalDateTime.now().plusDays(1).plusHours(3),
                dateArrivee = LocalDateTime.now().plusDays(1).plusHours(12),
                avion = listAvion[1],
                prixParClasse = mapOf(
                    "Économique" to 300.75,
                    "Affaire" to 850.25,
                    "Première" to 1750.00
                ),
                poidsMaxBag = 25,
                statutVol = listOf(
                    VolStatut(
                        idVol = 2,
                        statut = "confirmé",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 9.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter { it.idVol == 4 }.toMutableList()
            ),
            Vol(
                id = 5,
                numeroVol = "AD002",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[2],
                dateDepart = LocalDateTime.now().plusDays(1).plusHours(3),
                dateArrivee = LocalDateTime.now().plusDays(1).plusHours(12),
                avion = listAvion[1],
                prixParClasse = mapOf(
                    "Économique" to 300.75,
                    "Affaire" to 850.25,
                    "Première" to 1750.00
                ),
                poidsMaxBag = 25,
                statutVol = listOf(
                    VolStatut(
                        idVol = 2,
                        statut = "confirmé",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 9.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter { it.idVol == 5 }.toMutableList()
            )
        )

        val listeRéservation = mutableListOf(
            Réservation(
                id = 1,
                numéroRéservation = "RES001",
                idVol = 1,
                client = listClients[0],
                siège = listSièges[0],
                classe = "Économique"
            ),
            Réservation(
                id = 2,
                numéroRéservation = "RES002",
                idVol = 2,
                client = listClients[1],
                siège = listSièges[3],
                classe = "Première"
            )
        )
    }

    fun obtenirVolParId(id: Int): Vol? =
        listVol.singleOrNull {
            it.id == id
        }

    override fun obtenirListeRéservation(): MutableList<Réservation> {
        return listeRéservation
    }

    override fun obtenirRéservationParId(id : Int): Réservation =
        listeRéservation.single {
            it.id == id
        }

    override fun ajouterRéservation(réservation: Réservation) {
        réservation.id = listeRéservation.size + 1
        réservation.numéroRéservation = "RES00${listeRéservation.size + 1}"
        réservation.siège.let {
            it?.idRéservation = réservation.id
            it?.idVol = réservation.idVol
            if (it != null) {
                obtenirVolParId( réservation.idVol )?.sièges?.add( it )
            }
        }
        listeRéservation.add( réservation )
    }


    override fun ajouterClient( client : Client) {
        listClients.add(client)
    }

    override fun obtenirListeClient(): MutableList<Client> {
        return listClients
    }

    override fun obtenirSiegeParId(id: Int): Siège =
        listSièges.single {
            it.id == id
        }


}

