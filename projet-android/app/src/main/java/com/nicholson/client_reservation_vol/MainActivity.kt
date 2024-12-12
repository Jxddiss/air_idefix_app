package com.nicholson.client_reservation_vol

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.nicholson.client_reservation_vol.domaine.interacteur.ModifierClient
import com.nicholson.client_reservation_vol.domaine.interacteur.ObtenirAéroport
import com.nicholson.client_reservation_vol.domaine.interacteur.ObtenirClient
import com.nicholson.client_reservation_vol.domaine.interacteur.RechercherVol
import com.nicholson.client_reservation_vol.donnée.DataBase.SourceDeDonnéesLocalImpl
import com.nicholson.client_reservation_vol.donnée.http.SourceDeDonnéesAeroportHttp
import com.nicholson.client_reservation_vol.donnée.http.SourceDeDonnéesClientHttp
import com.nicholson.client_reservation_vol.donnée.http.SourceDeDonnéesVolsHttp
import com.nicholson.client_reservation_vol.présentation.CréateurDeFragment
import com.nicholson.client_reservation_vol.présentation.Modèle
import okhttp3.OkHttpClient
import java.util.logging.Level
import java.util.logging.Logger

class  MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize la sourceDeDonnées
        val modèle = Modèle.obtenirInstance()
        val sourceDeDonnéesHistorique = SourceDeDonnéesLocalImpl( applicationContext )
        modèle.initialiserSourceDeDonnées( sourceDeDonnéesHistorique )

        RechercherVol.sourceDeDonnéesVol = SourceDeDonnéesVolsHttp( getString( R.string.api_url ) )
        ObtenirAéroport.sourceDeDonnées = SourceDeDonnéesAeroportHttp( getString( R.string.api_url ) )
        val sourceClient = SourceDeDonnéesClientHttp( getString( R.string.api_url ) )
        ObtenirClient.sourceDeDonnées = sourceClient
        ModifierClient.sourceDeDonnées = sourceClient
        Logger.getLogger(OkHttpClient::class.java.name).level = Level.FINE
        supportFragmentManager.fragmentFactory = CréateurDeFragment()
    }
}