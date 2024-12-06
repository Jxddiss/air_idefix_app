package com.nicholson.client_reservation_vol.présentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.nicholson.client_reservation_vol.R

open class VueAuthentifié : Fragment()  {
    private var audience : String?  = null
    private var scheme : String? = null
    lateinit var account : Auth0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        audience   = getString( R.string.com_auth0_audience )
        scheme  = getString( R.string.com_auth0_scheme )
        account = Auth0(
            getString( R.string.com_auth0_client_id ),
            getString( R.string.com_auth0_domain )
        )
    }

    fun seConnecter( réussite: ( String ) -> Unit, échec : ( String ) -> Unit ) {
        scheme?.let {
            audience?.let { aud ->
                WebAuthProvider.login( account )
                    .withScheme(it)
                    .withScope( "openid profile" )
                    .withAudience( aud )
                    .start( requireContext(), object : Callback<Credentials, AuthenticationException> {
                        override fun onFailure( error: AuthenticationException ) {
                            échec( "Erreur de connexion ${error.message}" )
                        }

                        override fun onSuccess( result: Credentials) {
                            réussite( result.accessToken )
                        }
                    })
            }
        }
    }

    fun seDéconnecter( réussite: () -> Unit, échec : ( String ) -> Unit ) {
        scheme?.let {
            WebAuthProvider.logout( account )
                .withScheme(it)
                .start( requireContext(), object : Callback<Void?, AuthenticationException> {
                    override fun onFailure( error: AuthenticationException ) {
                        échec( "Erreur de connexion ${error.message}" )
                    }

                    override fun onSuccess( result : Void? ) {
                        réussite()
                    }
                })
        }
    }
}