package com.softtehnica.freya.fragments.introServer

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.encorsa.fidelioseller.fragments.IntroServerModelFactory
import com.softtehnica.freya.apiManager.FreyaApiStatus
import com.softtehnica.freya.configs.MessagePopup
import com.softtehnica.freya.databinding.IntroServerFragmentBinding


class IntroServerFragment : Fragment() {

    companion object {
        fun newInstance() = IntroServerFragment()
    }

    private lateinit var viewModel: IntroServerViewModel
    private lateinit var binding: IntroServerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IntroServerFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFactory = IntroServerModelFactory(requireActivity().application)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(IntroServerViewModel::class.java)
        binding.viewModel = viewModel
        var keyboardOpen = false
        binding.progressBarHUD.visibility = View.INVISIBLE

//        binding.editTextURL.setOnFocusChangeListener(View.OnFocusChangeListener {view, hasFocus ->
//            if (hasFocus) {
//                view.layoutParams = ConstraintLayout.LayoutParams(
//                    ConstraintLayout.LayoutParams.MATCH_PARENT,
//                    ConstraintLayout.LayoutParams.MATCH_PARENT
//                )
//            }
//        })

        viewModel.status.observe(this, Observer {
            when (it) {
                FreyaApiStatus.LOADING -> {
                    binding.progressBarHUD.visibility = View.VISIBLE
                }
                FreyaApiStatus.DONE -> {
                    binding.progressBarHUD.visibility = View.INVISIBLE
                    findNavController().navigate(IntroServerFragmentDirections.actionIntroServerFragmentToMainFragment())
                }
                FreyaApiStatus.ERROR -> {
                    binding.progressBarHUD.visibility = View.INVISIBLE
                    viewModel.resetServerConnection()
                    MessagePopup.show(context!!) {
                        setTitle("EROARE")
                        setMessage("Credențialele cu serverul sunt incorecte!")
                        setCancelable(false)
                        setPositiveButton("OK") { dialogInterface, which ->
                        }
                    }
                }
                else -> binding.progressBarHUD.visibility = View.INVISIBLE
            }
        })

        viewModel.error.observe(this, Observer {
            if (null != it) {
                Log.i("ERROR", it)
                //viewModel.errorWasDisplayed()
            }
        })

        viewModel.isDemoMode.observe(this, Observer {
            Log.i("IntroServerFragmen", "demomode: $it")
            updateUI()
        })

        viewModel.apiKey.observe(this, Observer {
            Log.i("IntroServerFragment", "demomode: $it")
            updateUI()
        })

        viewModel.serverUrl.observe(this, Observer {
            Log.i("IntroServerFragment", "serverurl: $it")
            updateUI()
        })

        //binding.buttonContinue.isEnabled = false
//        binding.switchDemo.setOnCheckedChangeListener{compButton, checked ->
//            viewModel.setDemoMode(checked)
//        }

        binding.buttonContinue.setOnClickListener {
            if (binding.switchDemo.isChecked) {
                viewModel.serverConnection.apiKey = ""
                viewModel.serverConnection.serverUrl = ""
            } else {
                viewModel.serverConnection.apiKey = binding.editTextApiKey.text.toString()
                viewModel.serverConnection.serverUrl = binding.editTextURL.text.toString()
            }
            viewModel.serverConnection.isDemoMode = binding.switchDemo.isChecked
            Log.i(
                "BEFORECHECKCALL",
                "url: ${viewModel.serverConnection.serverUrl} apikey: ${viewModel.serverConnection.apiKey} demoMode: ${viewModel.serverConnection.isDemoMode} isConfigured: ${viewModel.serverConnection.isConfigured}"
            )
            viewModel.checkOwnerCredentials()
        }


//        binding.editTextURL.setOnFocusChangeListener(View.OnFocusChangeListener { view, hasFocus ->
        //            if (hasFocus) {
//                if (!keyboardOpen) {
//                    ObjectAnimator.ofFloat(binding.constraintLayout2, "translationY", -250f).apply {
//                        duration = 200
//                        start()
//                    }
//                    keyboardOpen = true
//                }
//            } else {
//
//            }

//            if (hasFocus)
//                viewModel.setKeyboardOpenFlag(true)
//        })

//        binding.editTextApiKey.setOnFocusChangeListener(View.OnFocusChangeListener { view, hasFocus ->
        //            if (!hasFocus) {
//                ObjectAnimator.ofFloat(binding.constraintLayout2, "translationY", 0f).apply {
//                    duration = 200
//                    start()
//                }
//            }
//            if (hasFocus) {
//                viewModel.setKeyboardOpenFlag(true)
//            }
//        })


        binding.editTextApiKey.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) { // do your stuff here
                viewModel.setKeyboardOpenFlag(false)
                keyboardOpen = false
                //binding.switchDemo.isFocusable = true
            }
            false
        })

        viewModel.keyboardIsOpen.observe(this, Observer {
            if (it != keyboardOpen)
                Log.i("KEYBOARD", "isOpen $it")
            else
                Log.i("KEYBOARD", "alreadyOpen")
            keyboardOpen = it
        })
    }

    private fun updateUI() {
        if (binding.switchDemo.isChecked)
            binding.buttonContinue.isEnabled = true
        else {
            if (!binding.editTextURL.text.isEmpty() && !binding.editTextApiKey.text.isEmpty())
                binding.buttonContinue.isEnabled = true
            else
                binding.buttonContinue.isEnabled = false
        }
    }


}
