package com.nicholson.client_reservation_vol.domaine.interacteur

import com.nicholson.client_reservation_vol.donnée.SourceDeDonnées
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive
import org.mockito.Mockito
import kotlin.test.*

class RéservationServiceTest {
    lateinit var mockSource: SourceDeDonnées

    @BeforeTest
    fun init_mock(){
        mockSource = Mockito.mock(SourceDeDonnées::class.java)
    }

    @Test
    fun`Étant donée un service de réservation nouvellement instancier, lorsqu'on appel la méthode obtenirRéservationParId avec l'id 1, on obtient la réservation avec l'id 1 `(){
        Mockito.`when`( mockSource.obtenirRéservationParId(1) ).thenReturn( SourceDonnéesFictive.listeRéservation.first {it.id == 1})
        val résultat_attendue = SourceDonnéesFictive.listeRéservation.first {it.id == 1}

        val cobaye = RéservationService( mockSource )
        val résultat_obtenue = cobaye.obtenirRéservationParid(1)

        assertEquals( résultat_attendue, résultat_obtenue )
    }

    @Test
    fun `Étant donnée un service de réservation nouvellement instancier, lorsqu'on appel la méthode obtenirListeRéservation, on obtient la même liste de réservation que la source de données`(){
        Mockito.`when`( mockSource.obtenirListeRéservation() ).thenReturn( SourceDonnéesFictive.listeRéservation )
        val résultat_attendue = SourceDonnéesFictive.listeRéservation

        val cobaye = RéservationService( mockSource )
        val résultat_obtenue = cobaye.obtenirListeRéservation()

        assertEquals( résultat_attendue, résultat_obtenue )
    }
}