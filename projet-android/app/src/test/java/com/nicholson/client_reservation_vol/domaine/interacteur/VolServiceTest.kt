package com.nicholson.client_reservation_vol.domaine.interacteur

import com.nicholson.client_reservation_vol.donnée.SourceDeDonnées
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheVol
import kotlin.test.*
import org.mockito.Mockito
import java.time.LocalDateTime


class VolServiceTest{
    lateinit var mockSource : SourceDeDonnées

    @BeforeTest
    fun init_mock(){
        mockSource = Mockito.mock(SourceDeDonnées::class.java)
    }

    @Test
    fun `Étant donnée un service de vol nouvellement instancié, lorsqu'on appel la méthode obtenirListeVol, on obtient la même liste de vols que la source de données`(){
        Mockito.`when`( mockSource.obtenirListeVol() ).thenReturn( SourceDonnéesFictive.listVol )
        val résultat_attendue = SourceDonnéesFictive.listVol

        val cobaye = VolService( mockSource )
        val résultat_obtenue = cobaye.obtenirListeVol()

        assertEquals( résultat_attendue, résultat_obtenue )
    }

    @Test
    fun `Étant donnée un service de vol nouvellement instancié, lorsqu'on appel la méthode obtenirVolParId avec l'id 1, on obtient le vol avec l'id 1 `(){
        Mockito.`when`( mockSource.obtenirVolParId( 1 ) ).thenReturn( SourceDonnéesFictive.listVol.first { it.id == 1 } )
        val résultat_attendue = SourceDonnéesFictive.listVol.first { it.id == 1 }

        val cobaye = VolService( mockSource )
        val résultat_obtenue = cobaye.obtenirVolParId( 1 )

        assertEquals( résultat_attendue, résultat_obtenue )
    }

    @Test
    fun `Étant donnée un service de vol nouvellement instancié, lorsqu'on appel la méthode obtenirListeVolParFiltre avec un filtre ayant la date d'aujourd'hui, le code d'aeroport de départ YUL et le code de l'aéroport d'arrivée CDG, on obtient les vols en départ de YUL vers CDG `(){
        val filtre = FiltreRechercheVol(
            LocalDateTime.now(), "YUL", "CDG"
        )
        Mockito.`when`( mockSource.obtenirListeVolParFiltre( filtre ) ).thenReturn( SourceDonnéesFictive.listVol.filter {
            it.dateDepart >= filtre.dateDébut && it.dateDepart < filtre.dateDébut.plusDays(30)
                    && it.aeroportDebut.code == filtre.codeAéroportDébut
                    && it.aeroportFin.code == filtre.codeAéroportFin
        } )
        val résultat_attendue = SourceDonnéesFictive.listVol.filter {
            it.dateDepart >= filtre.dateDébut && it.dateDepart < filtre.dateDébut.plusDays(30)
                    && it.aeroportDebut.code == filtre.codeAéroportDébut
                    && it.aeroportFin.code == filtre.codeAéroportFin
        }

        val cobaye = VolService( mockSource )
        val résultat_obtenue = cobaye.obtenirListeVolParFiltre( filtre )

        assertEquals( résultat_attendue, résultat_obtenue )
    }
}