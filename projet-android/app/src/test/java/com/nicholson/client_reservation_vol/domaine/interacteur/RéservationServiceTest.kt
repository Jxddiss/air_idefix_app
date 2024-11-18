package com.nicholson.client_reservation_vol.domaine.interacteur

import com.nicholson.client_reservation_vol.donnée.SourceDeDonnées
import org.mockito.Mockito
import kotlin.test.*

class RéservationServiceTest {
    lateinit var mockSource: SourceDeDonnées

    @BeforeTest
    fun init_mock(){
        mockSource = Mockito.mock(SourceDeDonnées::class.java)
    }

    @Test
    fun`Étant donée un service de réservation nouvellement instancier, lorsqu'on appel la méthode obtenirReservationParId avec l'id 1, on obtient la réservation avec l'id 1 `(){



    }
}