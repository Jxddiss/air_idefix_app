package com.nicholson.client_reservation_vol.domaine.interacteur

import com.nicholson.client_reservation_vol.donnée.ISourceDeDonnéesVols
import com.nicholson.client_reservation_vol.donnée.SourceDeDonnées
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheVol
import kotlinx.coroutines.test.runTest
import kotlin.test.*
import org.mockito.Mockito
import java.time.LocalDateTime


class RechercherVolTest{
    lateinit var mockSource : ISourceDeDonnéesVols

    @BeforeTest
    fun init_mock(){
        mockSource = Mockito.mock(ISourceDeDonnéesVols::class.java)
    }

    @Test
    fun `Etant donnee une source de vols, lorsqu'on appel la méthode obtenirDétailVol de l'interacteur RechercherVol avec l'id 1, on obtient le vol avec l'id 1 `() = runTest {
        Mockito.`when`( mockSource.obtenirVolParId( 1 ) ).thenReturn( SourceDonnéesFictive.listVol.first { it.id == 1 } )
        val résultat_attendue = SourceDonnéesFictive.listVol.first { it.id == 1 }

        RechercherVol.sourceDeDonnéesVol = mockSource
        val résultat_obtenue = RechercherVol.obtenirDétailVol( 1 )

        assertEquals( résultat_attendue, résultat_obtenue )
    }

    @Test
    fun `Étant donnée un une source de vols, lorsqu'on rechercherVolParFiltre avec un filtre ayant la date d'aujourd'hui, le code d'aeroport YUL et le code CDG, on obtient les vols en départ de YUL vers CDG `()  = runTest {
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

        RechercherVol.sourceDeDonnéesVol = mockSource
        val résultat_obtenue = RechercherVol.rechercherVolParFiltre( filtre )

        assertEquals( résultat_attendue, résultat_obtenue )
    }
}