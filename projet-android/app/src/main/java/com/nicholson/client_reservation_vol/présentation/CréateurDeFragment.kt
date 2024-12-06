package com.nicholson.client_reservation_vol.présentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class CréateurDeFragment : FragmentFactory() {
    override fun instantiate( classLoader: ClassLoader, className: String ): Fragment {
        return when ( className ) {
            VueAuthentifié::class.java.name -> VueAuthentifié()
            else -> super.instantiate( classLoader, className )
        }
    }
}