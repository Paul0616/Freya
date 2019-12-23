package com.softtehnica.freya.fragments.lock

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.softtehnica.freya.BuildConfig
import com.softtehnica.freya.configs.MessagePopup
import com.softtehnica.freya.configs.MessagePopupType
import com.softtehnica.freya.databinding.LockFragmentBinding



class LockFragment : Fragment() {

    companion object {
        fun newInstance() = LockFragment()
    }

    private lateinit var viewModel: LockViewModel
    private lateinit var binding: LockFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LockFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LockViewModel::class.java)

        viewModel.timeText.observe(this, Observer {
            binding.timeTextVie.text = DateUtils.formatDateTime(context!!, it, DateUtils.FORMAT_SHOW_TIME)
            val flags = (DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
                    or DateUtils.FORMAT_SHOW_WEEKDAY)
            binding.dateTextView.text = DateUtils.formatDateTime(context!!, it, flags)
        })

        binding.versionTextView.text = BuildConfig.VERSION_NAME + "-" + BuildConfig.VERSION_CODE
        viewModel.getusers()

//        MessagePopup.show(context!!){
//            setTitle("EROARE1")
//            //setMessage("Mesaj de test")
//            setPositiveButton("OK") {dialogInterface, which->
//                Toast.makeText(context!!, "Ai apasat OK", Toast.LENGTH_SHORT).show()
//            }
//        }

    }

}
