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
                listVille.first {
                    it.nom == "Paris"
                },
                "FR"
            ),
            Aeroport(
                2,
                "JFK",
                "John F. Kennedy International",
                listVille.first {
                    it.nom == "New York"
                },
                "US"
            ),
            Aeroport(
                3,
                "NRT",
                "Narita",
                listVille.first {
                    it.nom == "Tokyo"
                },
                "JP"
            ),
            Aeroport(
                4,
                "YUL",
                "Pierre-Elliot-Trudeau",
                listVille.first {
                    it.nom == "Montréal"
                },
                "CA"
            ),
        )

        val listAvion = mutableListOf(
            Avion(
                1,
                "Boeing 737"
            ),
            Avion(
                2,
                "Boeing 737"
            ),
            Avion(
                3,
                "Boeing 737"
            ),
            Avion(
                id = 4,
                type = "Boeing 737"
            )
        )


        val listSièges = MutableList((6..12).random()){
            val  idVolRes = (1..4).random()
            Siège(
                id = it,
                numéro = ('A'..'H').random().toString() + ((1..3).random()).toString(),
                classe = "Économique",
                statut = "occupée",
                idRéservation = idVolRes,
                idVol = idVolRes
            )
        }

        val listVol = mutableListOf(
            Vol(
                id = 1,
                numeroVol = "AD001",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[0],
                dateDepart = LocalDateTime.now().plusDays(5).plusHours(2),
                dateArrivee = LocalDateTime.now().plusDays(5).plusHours(10),
                avion = listAvion[0],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(150.10, 467.89),
                    "Affaire" to Random.nextDouble(550.56, 897.89),
                    "Première" to Random.nextDouble(1550.56, 2897.89)
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
                sièges = listSièges.filter {
                    it.idVol == 1
                }.toMutableList()
            ),
            Vol(
                id = 2,
                numeroVol = "AD002",
                aeroportDebut = listAeoroport[0],
                aeroportFin = listAeoroport[3],
                dateDepart = LocalDateTime.now().plusDays(17).plusHours(2),
                dateArrivee = LocalDateTime.now().plusDays(17).plusHours(10),
                avion = listAvion[1],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(150.10, 467.89),
                    "Affaire" to Random.nextDouble(550.56, 897.89),
                    "Première" to Random.nextDouble(1550.56, 2897.89)
                ),
                poidsMaxBag = 20,
                statutVol = listOf(
                    VolStatut(
                        idVol = 2,
                        statut = "en attente",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 8.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 2
                }.toMutableList()
            ),
            Vol(
                id = 3,
                numeroVol = "AD003",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[1],
                dateDepart = LocalDateTime.now().plusDays(10),
                dateArrivee = LocalDateTime.now().plusDays(10).plusHours(7),
                avion = listAvion[2],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(150.10, 467.89),
                    "Affaire" to Random.nextDouble(550.56, 897.89),
                    "Première" to Random.nextDouble(1550.56, 2897.89)
                ),
                poidsMaxBag = 20,
                statutVol = listOf(
                    VolStatut(
                        idVol = 3,
                        statut = "en attente",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 7.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 3
                }.toMutableList()
            ),
            Vol(
                id = 4,
                numeroVol = "AD004",
                aeroportDebut = listAeoroport[1],
                aeroportFin = listAeoroport[3],
                dateDepart = LocalDateTime.now().plusDays(20),
                dateArrivee = LocalDateTime.now().plusDays(20).plusHours(7),
                avion = listAvion[3],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(150.10, 467.89),
                    "Affaire" to Random.nextDouble(550.56, 897.89),
                    "Première" to Random.nextDouble(1550.56, 2897.89)
                ),
                poidsMaxBag = 20,
                statutVol = listOf(
                    VolStatut(
                        idVol = 4,
                        statut = "en attente",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 7.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 4
                }.toMutableList()
            ),
            Vol(
                id = 5,
                numeroVol = "AD005",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[1],
                dateDepart = LocalDateTime.now().plusHours(3),
                dateArrivee = LocalDateTime.now().plusHours(10),
                avion = listAvion[2],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(150.10, 467.89),
                    "Affaire" to Random.nextDouble(550.56, 897.89),
                    "Première" to Random.nextDouble(1550.56, 2897.89)
                ),
                poidsMaxBag = 20,
                statutVol = listOf(
                    VolStatut(
                        idVol = 5,
                        statut = "en attente",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 7.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 5
                }.toMutableList()
            ),
            Vol(
                id = 6,
                numeroVol = "AD006",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[1],
                dateDepart = LocalDateTime.now().plusDays(15),
                dateArrivee = LocalDateTime.now().plusDays(15).plusHours(7),
                avion = listAvion[3],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(150.10, 467.89),
                    "Affaire" to Random.nextDouble(550.56, 897.89),
                    "Première" to Random.nextDouble(1550.56, 2897.89)
                ),
                poidsMaxBag = 20,
                statutVol = listOf(
                    VolStatut(
                        idVol = 6,
                        statut = "en attente",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 7.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 6
                }.toMutableList()
            ),
            Vol(
                id = 7,
                numeroVol = "AD007",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[1],
                dateDepart = LocalDateTime.now().plusDays(25),
                dateArrivee = LocalDateTime.now().plusDays(25).plusHours(7),
                avion = listAvion[3],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(150.10, 467.89),
                    "Affaire" to Random.nextDouble(550.56, 897.89),
                    "Première" to Random.nextDouble(1550.56, 2897.89)
                ),
                poidsMaxBag = 20,
                statutVol = listOf(
                    VolStatut(
                        idVol = 7,
                        statut = "en attente",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 7.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 7
                }.toMutableList()
            ),
            Vol(
                id = 8,
                numeroVol = "AD008",
                aeroportDebut = listAeoroport[2],
                aeroportFin = listAeoroport[0],
                dateDepart = LocalDateTime.now().plusDays(7).plusHours(5),
                dateArrivee = LocalDateTime.now().plusDays(7).plusHours(12),
                avion = listAvion[0],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(120.00, 400.00),
                    "Affaire" to Random.nextDouble(600.00, 900.00),
                    "Première" to Random.nextDouble(1600.00, 3000.00)
                ),
                poidsMaxBag = 23,
                statutVol = listOf(
                    VolStatut(
                        idVol = 8,
                        statut = "confirmé",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 7.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 8
                }.toMutableList()
            ),
            Vol(
                id = 9,
                numeroVol = "AD009",
                aeroportDebut = listAeoroport[1],
                aeroportFin = listAeoroport[2],
                dateDepart = LocalDateTime.now().plusDays(12),
                dateArrivee = LocalDateTime.now().plusDays(12).plusHours(4),
                avion = listAvion[1],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(180.00, 500.00),
                    "Affaire" to Random.nextDouble(700.00, 1000.00),
                    "Première" to Random.nextDouble(1800.00, 3200.00)
                ),
                poidsMaxBag = 25,
                statutVol = listOf(
                    VolStatut(
                        idVol = 9,
                        statut = "en attente",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 4.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 9
                }.toMutableList()
            ),
            Vol(
                id = 10,
                numeroVol = "AD010",
                aeroportDebut = listAeoroport[0],
                aeroportFin = listAeoroport[2],
                dateDepart = LocalDateTime.now().plusDays(30).plusHours(6),
                dateArrivee = LocalDateTime.now().plusDays(30).plusHours(14),
                avion = listAvion[2],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(200.00, 450.00),
                    "Affaire" to Random.nextDouble(750.00, 1200.00),
                    "Première" to Random.nextDouble(2000.00, 3500.00)
                ),
                poidsMaxBag = 30,
                statutVol = listOf(
                    VolStatut(
                        idVol = 10,
                        statut = "confirmé",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 8.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 10
                }.toMutableList()
            ),
            Vol(
                id = 11,
                numeroVol = "AD011",
                aeroportDebut = listAeoroport[0],
                aeroportFin = listAeoroport[3],
                dateDepart = LocalDateTime.now().plusDays(3).plusHours(8),
                dateArrivee = LocalDateTime.now().plusDays(3).plusHours(15),
                avion = listAvion[1],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(250.00, 550.00),
                    "Affaire" to Random.nextDouble(700.00, 1200.00),
                    "Première" to Random.nextDouble(1800.00, 3200.00)
                ),
                poidsMaxBag = 25,
                statutVol = listOf(
                    VolStatut(
                        idVol = 11,
                        statut = "en attente",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 7.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 11
                }.toMutableList()
            ),
            Vol(
                id = 12,
                numeroVol = "AD012",
                aeroportDebut = listAeoroport[2],
                aeroportFin = listAeoroport[0],
                dateDepart = LocalDateTime.now().plusDays(9),
                dateArrivee = LocalDateTime.now().plusDays(9).plusHours(6),
                avion = listAvion[3],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(300.00, 600.00),
                    "Affaire" to Random.nextDouble(900.00, 1500.00),
                    "Première" to Random.nextDouble(2000.00, 3500.00)
                ),
                poidsMaxBag = 30,
                statutVol = listOf(
                    VolStatut(
                        idVol = 12,
                        statut = "confirmé",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 6.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 12
                }.toMutableList()
            ),
            Vol(
                id = 13,
                numeroVol = "AD013",
                aeroportDebut = listAeoroport[1],
                aeroportFin = listAeoroport[3],
                dateDepart = LocalDateTime.now().plusDays(20).plusHours(1),
                dateArrivee = LocalDateTime.now().plusDays(20).plusHours(5),
                avion = listAvion[0],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(120.00, 300.00),
                    "Affaire" to Random.nextDouble(600.00, 900.00),
                    "Première" to Random.nextDouble(1500.00, 2700.00)
                ),
                poidsMaxBag = 20,
                statutVol = listOf(
                    VolStatut(
                        idVol = 13,
                        statut = "annulé",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 4.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 13
                }.toMutableList()
            ),
            Vol(
                id = 14,
                numeroVol = "AD014",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[0],
                dateDepart = LocalDateTime.now().plusDays(14).plusHours(4),
                dateArrivee = LocalDateTime.now().plusDays(14).plusHours(9),
                avion = listAvion[2],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(200.00, 500.00),
                    "Affaire" to Random.nextDouble(800.00, 1200.00),
                    "Première" to Random.nextDouble(1800.00, 3000.00)
                ),
                poidsMaxBag = 22,
                statutVol = listOf(
                    VolStatut(
                        idVol = 14,
                        statut = "retardé",
                        heure = LocalDateTime.now().toLocalTime().plusHours(2)
                    )
                ),
                durée = 5.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 14
                }.toMutableList()
            ),
            Vol(
                id = 15,
                numeroVol = "AD015",
                aeroportDebut = listAeoroport[2],
                aeroportFin = listAeoroport[3],
                dateDepart = LocalDateTime.now().plusDays(30),
                dateArrivee = LocalDateTime.now().plusDays(30).plusHours(10),
                avion = listAvion[3],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(350.00, 600.00),
                    "Affaire" to Random.nextDouble(1000.00, 1500.00),
                    "Première" to Random.nextDouble(2500.00, 4000.00)
                ),
                poidsMaxBag = 30,
                statutVol = listOf(
                    VolStatut(
                        idVol = 15,
                        statut = "confirmé",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 10.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 15
                }.toMutableList()
            ),
            Vol(
                id = 16,
                numeroVol = "AD016",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[0],
                dateDepart = LocalDateTime.now().plusDays(2).plusHours(6),
                dateArrivee = LocalDateTime.now().plusDays(2).plusHours(14),
                avion = listAvion[1],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(200.00, 400.00),
                    "Affaire" to Random.nextDouble(700.00, 1200.00),
                    "Première" to Random.nextDouble(1800.00, 3000.00)
                ),
                poidsMaxBag = 25,
                statutVol = listOf(
                    VolStatut(
                        idVol = 16,
                        statut = "en attente",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 8.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 16
                }.toMutableList()
            ),
            Vol(
                id = 17,
                numeroVol = "AD017",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[1],
                dateDepart = LocalDateTime.now().plusDays(5),
                dateArrivee = LocalDateTime.now().plusDays(5).plusHours(9),
                avion = listAvion[2],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(150.00, 350.00),
                    "Affaire" to Random.nextDouble(600.00, 950.00),
                    "Première" to Random.nextDouble(1500.00, 2500.00)
                ),
                poidsMaxBag = 22,
                statutVol = listOf(
                    VolStatut(
                        idVol = 17,
                        statut = "confirmé",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 6.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 17
                }.toMutableList()
            ),
            Vol(
                id = 18,
                numeroVol = "AD018",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[0],
                dateDepart = LocalDateTime.now().plusDays(10).plusHours(3),
                dateArrivee = LocalDateTime.now().plusDays(10).plusHours(12),
                avion = listAvion[0],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(220.00, 450.00),
                    "Affaire" to Random.nextDouble(750.00, 1250.00),
                    "Première" to Random.nextDouble(1900.00, 3200.00)
                ),
                poidsMaxBag = 20,
                statutVol = listOf(
                    VolStatut(
                        idVol = 18,
                        statut = "retardé",
                        heure = LocalDateTime.now().toLocalTime().plusHours(1)
                    )
                ),
                durée = 9.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 18
                }.toMutableList()
            ),
            Vol(
                id = 19,
                numeroVol = "AD019",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[2],
                dateDepart = LocalDateTime.now().plusDays(15).plusHours(8),
                dateArrivee = LocalDateTime.now().plusDays(15).plusHours(14),
                avion = listAvion[3],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(250.00, 470.00),
                    "Affaire" to Random.nextDouble(850.00, 1350.00),
                    "Première" to Random.nextDouble(2000.00, 3400.00)
                ),
                poidsMaxBag = 30,
                statutVol = listOf(
                    VolStatut(
                        idVol = 19,
                        statut = "confirmé",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 6.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 19
                }.toMutableList()
            ),
            Vol(
                id = 20,
                numeroVol = "AD020",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[1],
                dateDepart = LocalDateTime.now().plusDays(20).plusHours(4),
                dateArrivee = LocalDateTime.now().plusDays(20).plusHours(11),
                avion = listAvion[1],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(300.00, 500.00),
                    "Affaire" to Random.nextDouble(1000.00, 1500.00),
                    "Première" to Random.nextDouble(2500.00, 4000.00)
                ),
                poidsMaxBag = 25,
                statutVol = listOf(
                    VolStatut(
                        idVol = 20,
                        statut = "en attente",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 7.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 20
                }.toMutableList()
            ),
            Vol(
                id = 21,
                numeroVol = "AD021",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[2],
                dateDepart = LocalDateTime.now().plusDays(3).plusHours(7),
                dateArrivee = LocalDateTime.now().plusDays(3).plusHours(15),
                avion = listAvion[0],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(180.00, 390.00),
                    "Affaire" to Random.nextDouble(580.00, 970.00),
                    "Première" to Random.nextDouble(1600.00, 2900.00)
                ),
                poidsMaxBag = 22,
                statutVol = listOf(
                    VolStatut(
                        idVol = 21,
                        statut = "confirmé",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 8.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 21
                }.toMutableList()
            ),
            Vol(
                id = 22,
                numeroVol = "AD022",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[2],
                dateDepart = LocalDateTime.now().plusDays(8).plusHours(10),
                dateArrivee = LocalDateTime.now().plusDays(8).plusHours(18),
                avion = listAvion[2],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(200.00, 420.00),
                    "Affaire" to Random.nextDouble(650.00, 1100.00),
                    "Première" to Random.nextDouble(1800.00, 3200.00)
                ),
                poidsMaxBag = 25,
                statutVol = listOf(
                    VolStatut(
                        idVol = 22,
                        statut = "en attente",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 8.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 22
                }.toMutableList()
            ),
            Vol(
                id = 23,
                numeroVol = "AD023",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[2],
                dateDepart = LocalDateTime.now().plusDays(14).plusHours(5),
                dateArrivee = LocalDateTime.now().plusDays(14).plusHours(13),
                avion = listAvion[1],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(210.00, 450.00),
                    "Affaire" to Random.nextDouble(700.00, 1200.00),
                    "Première" to Random.nextDouble(2000.00, 3500.00)
                ),
                poidsMaxBag = 20,
                statutVol = listOf(
                    VolStatut(
                        idVol = 23,
                        statut = "retardé",
                        heure = LocalDateTime.now().toLocalTime().plusHours(2)
                    )
                ),
                durée = 8.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 23
                }.toMutableList()
            ),
            Vol(
                id = 24,
                numeroVol = "AD024",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[2],
                dateDepart = LocalDateTime.now().plusDays(20).plusHours(3),
                dateArrivee = LocalDateTime.now().plusDays(20).plusHours(11),
                avion = listAvion[3],
                prixParClasse = mapOf(
                    "Économique" to Random.nextDouble(190.00, 410.00),
                    "Affaire" to Random.nextDouble(630.00, 1050.00),
                    "Première" to Random.nextDouble(1750.00, 3100.00)
                ),
                poidsMaxBag = 30,
                statutVol = listOf(
                    VolStatut(
                        idVol = 24,
                        statut = "confirmé",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 8.toDuration(DurationUnit.HOURS),
                sièges = listSièges.filter {
                    it.idVol == 24
                }.toMutableList()
            )
        )

        val listeRéservation = MutableList(4) { index ->
            Réservation(
                id = index + 1,
                numéroRéservation = "RES00${listVol[index].id}",
                idVol = listVol[index].id,
                clients = listClients,
                sièges = listSièges.filter{
                    it.idRéservation == index + 1
                }
            )
        }

        val listHistorique = mutableListOf(
            Historique(
                villeDe="Montreal",
                villeVers="Cancun",
                aeroportDe = "YUL",
                aeroportVers = "BEN",
                dateDepart = LocalDate.of(2024, 11, 15),
                dateRetour = LocalDate.of(2024, 11, 22)

            ),
            Historique(
                villeDe = "Toronto",
                villeVers = "Paris",
                aeroportDe = "YYZ",
                aeroportVers = "CDG",
                dateDepart = LocalDate.of(2024, 11, 10),
                dateRetour = LocalDate.of(2024, 11, 17)
            ),
            Historique(
                villeDe = "Toronto",
                villeVers = "Paris",
                aeroportDe = "YYZ",
                aeroportVers = "CDG",
                dateDepart = LocalDate.of(2025, 1, 10),
                dateRetour = LocalDate.of(2025, 1, 17)
            ),
        )



    }

    override fun obtenirListeVol(): List<Vol> = listVol.sortedBy { it.dateDepart }

    override fun obtenirListeVolParFiltre(filtre: FiltreRechercheVol): List<Vol> =
        listVol.filter {
            it.dateDepart >= filtre.dateDébut && it.dateDepart < filtre.dateDébut.plusDays(30)
                    && it.aeroportDebut.code == filtre.codeAéroportDébut
                    && it.aeroportFin.code == filtre.codeAéroportFin
        }.sortedBy { it.dateDepart }


    override fun obtenirVolParId(id: Int): Vol =
        listVol.single {
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
        réservation.sièges.forEach {
            it.idRéservation = réservation.id
            it.idVol = réservation.idVol
            obtenirVolParId( réservation.idVol ).sièges.add( it )
        }
        listeRéservation.add( réservation )
    }


    override fun ajouterClient( client : Client) {
        listClients.add(client)
    }

    override fun obtenirListeClient(): MutableList<Client> {
        return listClients
    }

    override fun obtenirListHistorique():List<Historique> = listHistorique

    override fun ajouterHistorique(historique: Historique) {
        listHistorique.add(historique)
    }

    override fun obtenirListAéroports(): List<Aeroport> {
        return  listAeoroport
    }
}

