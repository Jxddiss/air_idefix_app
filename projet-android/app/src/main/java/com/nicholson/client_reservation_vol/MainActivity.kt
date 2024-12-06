package com.nicholson.client_reservation_vol

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nicholson.client_reservation_vol.domaine.interacteur.ModifierClient
import com.nicholson.client_reservation_vol.domaine.interacteur.ObtenirAéroport
import com.nicholson.client_reservation_vol.domaine.interacteur.ObtenirClient
import com.nicholson.client_reservation_vol.domaine.interacteur.RechercherVol
import com.nicholson.client_reservation_vol.donnée.DataBase.SourceDeDonnéesLocalImpl
import com.nicholson.client_reservation_vol.donnée.http.ClientHttp
import com.nicholson.client_reservation_vol.donnée.http.SourceDeDonnéesAeroportHttp
import com.nicholson.client_reservation_vol.donnée.http.SourceDeDonnéesClientHttp
import com.nicholson.client_reservation_vol.donnée.http.SourceDeDonnéesVolsHttp
import com.nicholson.client_reservation_vol.présentation.Modèle

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

        ClientHttp.ajouterToken( "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImxpUkhvcXVOWElHUXJMaUNweWJKSyJ9.eyJodHRwczovL2Fpci5pZMOpZml4LmNvbS8vcm9sZXMiOlsiRW1wbG95w6kgQWlyIElkw6lmaXgiXSwiY291cnJpZWwiOiJwaWVycmUuYmVybmFyZEBlbWFpbC5jb20iLCJpc3MiOiJodHRwczovL2Rldi03bGQ4MXBic3MzcGs3c2ljLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2NzUyNWFkMTdhYWQ2ODY2NWNhNDE2OWIiLCJhdWQiOlsiaHR0cHM6Ly9haXIuaWTDqWZpeC5jb20vIiwiaHR0cHM6Ly9kZXYtN2xkODFwYnNzM3BrN3NpYy51cy5hdXRoMC5jb20vdXNlcmluZm8iXSwiaWF0IjoxNzMzNDUyNzk3LCJleHAiOjE3MzM1MzkxOTcsInNjb3BlIjoib3BlbmlkIHByb2ZpbGUiLCJndHkiOiJwYXNzd29yZCIsImF6cCI6ImhOTWtpcUZuclBmRjZqYTd1SE0xOXlodEdjT2c5V2JhIiwicGVybWlzc2lvbnMiOlsiY29uc3VsdGVyOmNsaWVudHMiLCJjcsOpZXI6Y2xpZW50cyIsImNyw6llcjp2b2xzIiwibW9kaWZpZXI6Y2xpZW50cyIsIm1vZGlmaWVyOnZvbHMiLCJzdXBwcmltZXI6Y2xpZW50cyJdfQ.SUcDXUGZfIM1gH_Uq3ndvlY6ckWJXg7AIDYc0nirgijh56RV-vHBMjDbrW07UtDjkWSIY8EPImXtoEyXt6PHw8sjHPNTccPwPEAqNoJ_DOtRtUMxRK_ok9ZOPbd8_cTQoxnJ7VEcrCpDtvW0vS03c97L0S7CJFfs27GLLwGlG4e2olwZjlAARL0OyOMLM5pldnF-By-JhVn_5HoXNIKe1VqAfEuvJqUOgGDWTuPPbqYSG_8WGz79n3z3vTdwa6JGOHpYXsY2E0XMZMImMwYcutPP9bHRD7M4X3rL69tm64zvtzCgC4UVm7ezEw-AOcJGdOwppVmpMznMVqdRuS_NRw" )
    }
}