package com.nicholson.client_reservation_vol.présentation

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.nicholson.client_reservation_vol.R

open class VueAuthentifié : Fragment()  {
    private lateinit var audience : String
    private lateinit var scheme : String
    private lateinit var account : Auth0
    lateinit var préférences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        val masterKey = MasterKey.Builder(requireContext())
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        super.onCreate(savedInstanceState)
        audience   = getString( R.string.com_auth0_audience )
        scheme  = getString( R.string.com_auth0_scheme )
        account = Auth0( requireContext() )
        préférences = EncryptedSharedPreferences.create(
            requireContext(),
            "auth_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun seConnecter( réussite: ( String ) -> Unit, échec : ( String ) -> Unit ) {
        WebAuthProvider.login( account )
            .withScheme(scheme)
            .withScope( "openid profile" )
            .withAudience( audience )
            .start( requireContext(), object : Callback<Credentials, AuthenticationException> {
                override fun onFailure( error: AuthenticationException ) {
                    échec( "Erreur de connexion ${error.message}" )
                }

                override fun onSuccess( result: Credentials) {
                    préférences.edit().putString( "token", result.accessToken ).apply()
                    réussite( result.accessToken )
                }
            })
    }

    fun seDéconnecter( réussite: () -> Unit, échec : ( String ) -> Unit ) {
        WebAuthProvider.logout( account )
            .withScheme( scheme )
            .start( requireContext(), object : Callback<Void?, AuthenticationException> {
                override fun onFailure( error: AuthenticationException ) {
                    préférences.edit().remove( "token" ).apply()
                    échec( "Erreur de connexion ${error.message}" )
                }

                override fun onSuccess( result : Void? ) {
                    préférences.edit().remove( "token" ).apply()
                    réussite()
                }
            })
    }
}