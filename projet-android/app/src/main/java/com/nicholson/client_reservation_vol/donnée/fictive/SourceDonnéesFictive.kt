package com.nicholson.client_reservation_vol.donnée.fictive

import com.nicholson.client_reservation_vol.domaine.entité.*
import com.nicholson.client_reservation_vol.donnée.SourceDeDonnées
import java.time.LocalDateTime
import kotlin.random.Random
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class SourceDonnéesFictive : SourceDeDonnées {
    companion object{

        val client = Client(
            1,
            "Doe",
            "John",
            "123 Main St",
            "A12345678",
            "john.doe@example.com",
            "123-456-7890"
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
                "Boeing 737",
                List(36){
                    Siège(
                        id = it,
                        numéro = "S00$it",
                        classe = Classe.ÉCONOMIQUE.toString(),
                        statut = "occupée",
                        idAvion = 1,
                        idRéservation = 1
                    )
                },
                1,
            ),
            Avion(
                2,
                "Boeing 737",
                List(36){
                    Siège(
                        id = it,
                        numéro = "S00$it",
                        classe = Classe.ÉCONOMIQUE.toString(),
                        statut = "occupée",
                        idAvion = 2,
                        idRéservation = 2
                    )
                },
                2,
            ),
            Avion(
                3,
                "Boeing 737",
                List(36){
                    Siège(
                        id = it,
                        numéro = "S00$it",
                        classe = Classe.ÉCONOMIQUE.toString(),
                        statut = "occupée",
                        idAvion = 3,
                        idRéservation = 3
                    )
                },
                3,
            ),
            Avion(
                id = 4,
                type = "Boeing 737",
                sièges = List(36){
                    Siège(
                        id = it,
                        numéro = "S00$it",
                        classe = Classe.ÉCONOMIQUE.toString(),
                        statut = "occupée",
                        idAvion = 4,
                        idRéservation = 4
                    )
                },
                idVol = 4,
            )
        )

        val listVol = mutableListOf(
            Vol(
                id = 1,
                numeroVol = "AD001",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[0],
                dateDepart = LocalDateTime.now().plusDays(5).plusHours(2),
                dateArrivee = LocalDateTime.now().plusDays(5).plusHours(10),
                avion = listAvion[0],
                prixParClasse = mapOf("Économique" to Random.nextDouble( 150.10, 467.89 ),
                    "Business" to Random.nextDouble(550.56, 897.89)),
                poidsMaxBag = 20,
                statutVol = listOf(
                    VolStatut(
                        idVol = 3,
                        statut = "en attente",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 8.toDuration(DurationUnit.HOURS)
            ),
            Vol(
                id = 2,
                numeroVol = "AD002",
                aeroportDebut = listAeoroport[0],
                aeroportFin = listAeoroport[3],
                dateDepart = LocalDateTime.now().plusDays(17).plusHours(2),
                dateArrivee = LocalDateTime.now().plusDays(17).plusHours(10),
                avion = listAvion[1],
                prixParClasse = mapOf("Économique" to Random.nextDouble( 150.10, 467.89 ),
                    "Business" to Random.nextDouble(550.56, 897.89)),
                poidsMaxBag = 20,
                statutVol = listOf(
                    VolStatut(
                        idVol = 3,
                        statut = "en attente",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 8.toDuration(DurationUnit.HOURS)
            ),
            Vol(
                id = 3,
                numeroVol = "AD003",
                aeroportDebut = listAeoroport[3],
                aeroportFin = listAeoroport[1],
                dateDepart = LocalDateTime.now().plusDays(10),
                dateArrivee = LocalDateTime.now().plusDays(10).plusHours(7),
                avion = listAvion[2],
                prixParClasse = mapOf("Économique" to Random.nextDouble( 150.10, 467.89 ),
                    "Business" to Random.nextDouble(550.56, 897.89)),
                poidsMaxBag = 20,
                statutVol = listOf(
                    VolStatut(
                        idVol = 3,
                        statut = "en attente",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 7.toDuration(DurationUnit.HOURS)
            ),
            Vol(
                id = 4,
                numeroVol = "AD004",
                aeroportDebut = listAeoroport[1],
                aeroportFin = listAeoroport[3],
                dateDepart = LocalDateTime.now().plusDays(20),
                dateArrivee = LocalDateTime.now().plusDays(20).plusHours(7),
                avion = listAvion[3],
                prixParClasse = mapOf("Économique" to Random.nextDouble( 150.10, 467.89 ),
                    "Business" to Random.nextDouble(550.56, 897.89)),
                poidsMaxBag = 20,
                statutVol = listOf(
                    VolStatut(
                        idVol = 3,
                        statut = "en attente",
                        heure = LocalDateTime.now().toLocalTime()
                    )
                ),
                durée = 7.toDuration(DurationUnit.HOURS)
            )
        )

        val listeRéservation = MutableList(4) {index ->
            Réservation(
                id = listVol[index].id,
                numéroRéservation = "RES00${listVol[index].id}",
                idVol = listVol[index].id,
                clients = listOf(client),
                sièges = listVol[index].avion.sièges.filter {
                    it.idRéservation == listVol[index].id
                }
            )
        }
    }

    override fun getListeVol(): List<Vol> {
        return listVol
    }
}