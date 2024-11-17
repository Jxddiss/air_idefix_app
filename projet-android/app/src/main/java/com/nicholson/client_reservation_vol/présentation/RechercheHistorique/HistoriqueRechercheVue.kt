package com.nicholson.client_reservation_vol.présentation.RechercheHistorique

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.présentation.Modèle


class HistoriqueRechercheVue : Fragment() {
    private lateinit var modèle: Modèle  // Your existing model class
    private lateinit var recyclerView: RecyclerView
    private lateinit var rechercheHistoriqueAdapter: HistoriqueRechercheAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_historique_recherche_vue, container, false)


        return view
    }
}