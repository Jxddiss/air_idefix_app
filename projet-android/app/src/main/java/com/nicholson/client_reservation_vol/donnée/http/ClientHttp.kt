package com.nicholson.client_reservation_vol.donnée.http

import okhttp3.OkHttpClient


class ClientHttp : OkHttpClient() {
    companion object {
        @Volatile
        private var instance : ClientHttp? = null

        fun obtenirInstance() =
            instance ?: synchronized(this) {
                instance ?: ClientHttp().also { instance = it }
            }
    }
}