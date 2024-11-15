package com.nicholson.client_reservation_vol.présentation.choisirclasse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive

class ChoisirClasseVue: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choisir_classe_vue, container, false)
        val imageViewVillechoisirInformation: ImageView = view.findViewById(R.id.imageViewVillechoisirInformation)
        Glide.with(requireContext())
            .load(SourceDonnéesFictive.listVille[0].url_photo)
            .into(imageViewVillechoisirInformation)

        return view
    }
}