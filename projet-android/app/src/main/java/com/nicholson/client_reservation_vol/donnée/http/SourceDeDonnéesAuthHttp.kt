package com.nicholson.client_reservation_vol.donnée.http

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonneeAuth
import com.nicholson.client_reservation_vol.donnée.http.exception.AuthentificationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class SourceDeDonnéesAuthHttp( val context: Context, clientId : String,
                               domain : String, val audience : String,
                               val scheme : String ) : ISourceDeDonneeAuth {

    val account = Auth0(
        clientId,
        domain
    )

    override suspend fun obtenirToken(): String {
        return suspendCancellableCoroutine { continuation ->
            WebAuthProvider.login( account )
                .withScheme( scheme )
                .withScope( "openid profile" )
                .withAudience( audience )
                .start( context, object : Callback<Credentials, AuthenticationException> {
                    override fun onFailure( error: AuthenticationException ) {
                        continuation.resumeWithException(
                            AuthentificationException("Erreur de connexion ${error.message}")
                        )
                    }

                    override fun onSuccess( result: Credentials ) {
                        continuation.resume( result.accessToken )
                    }
                })
        }
    }

    override suspend fun seDeconnecter() : Boolean {
        return suspendCancellableCoroutine { continuation ->
            WebAuthProvider.logout( account )
                .withScheme( scheme )
                .start( context, object : Callback<Void?, AuthenticationException> {
                    override fun onFailure( error: AuthenticationException ) {
                        continuation.resumeWithException(
                            AuthentificationException("Erreur de connexion ${error.message}")
                        )
                    }

                    override fun onSuccess( result : Void? ) {
                        continuation.resume( true )
                    }
                })
        }
    }
}