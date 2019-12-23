package com.softtehnica.freya.fragments.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.encorsa.fidelioseller.utils.Prefs
import com.softtehnica.freya.configs.ServerConnection
import com.softtehnica.freya.databinding.MainFragmentBinding
import com.softtehnica.freya.sessionManager.SessionManager
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        val serverConnection = ServerConnection(context!!)
        Log.i(
            "MainFragment",
            "configured: ${serverConnection.isConfigured} demo mode: ${serverConnection.isDemoMode} serverurl: ${serverConnection.serverUrl} apikey: ${serverConnection.apiKey}"
        )
        val mainLooperHandler = Handler(Looper.getMainLooper())

        mainLooperHandler.postDelayed(Runnable {
            if (activity != null) {
                if (!serverConnection.isConfigured)
                    findNavController().navigate(MainFragmentDirections.actionMainFragmentToIntroServerFragment())
                else if (SessionManager.currentUser != null)
                    Log.i("MainFragment", "GO TO HOME SCREEN")
                else
                    findNavController().navigate(MainFragmentDirections.actionMainFragmentToLockFragment())
            }
        }, 2000)

        //TODO: trebuie scos
        imageViewLogo.setOnLongClickListener {
            val prefs = Prefs(context!!)
            prefs.resetPrefs()
            Log.i("MainFragment", "prefs was reset")
            false
        }
    }



}

