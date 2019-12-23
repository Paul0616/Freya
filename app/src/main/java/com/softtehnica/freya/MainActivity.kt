package com.softtehnica.freya


import android.graphics.Color
import android.os.Bundle

import android.view.Gravity
import android.view.View

import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.softtehnica.freya.conectivity.ConnectionLiveData



class MainActivity : AppCompatActivity(){ //, ConnectivityReceiver.ConnectivityReceiverListener

    private var isUIHiden: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        SessionManager.token = "test"
//        val claims = listOf<String>("1", "5", "7")
//        SessionManager.loadClaims(claims)
//        Log.i("MainActivity", "${SessionManager.token}")
//        Log.i("MainActivity", "${SessionManager.claims}")

        val connectionLiveData = ConnectionLiveData(applicationContext)
        connectionLiveData.observe(this, Observer { isConnected ->
            isConnected?.let {
                showNetworkMessage(it)
            }
        })
//        val navController = this.findNavController(R.id.nav_host)
//        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host)
        return navController.navigateUp()
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
//        mainContainer.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
//            if (isUIHiden)
//                showSystemUI()
//            else
//                hideSystemUI()
//            false
//        })
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (//View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
        isUIHiden = true
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        isUIHiden = false

    }

    private var snackBar: Snackbar? = null
    private fun showNetworkMessage(isConnected: Boolean) {

        if (!isConnected) {
            snackBar = Snackbar.make(
                findViewById(R.id.rootLayout),
                "Nu există conexiune la internet",
                Snackbar.LENGTH_LONG
            ) //Assume "rootLayout" as the root layout of every activity.
            val view = snackBar?.view as Snackbar.SnackbarLayout
            view.setBackgroundColor(Color.RED)
            val textView = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.gravity = Gravity.CENTER_HORIZONTAL
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            textView.textSize = 20.0f
            val params = view.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            params.width = FrameLayout.LayoutParams.MATCH_PARENT
            view.layoutParams = params
            snackBar?.duration = BaseTransientBottomBar.LENGTH_INDEFINITE
            snackBar?.show()
        } else {
            snackBar?.dismiss()
        }
    }

    fun showAlertDialog(dialogBuilder: AlertDialog.Builder.() -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.dialogBuilder()
        val dialog = builder.create()

        dialog.show()
    }

    fun AlertDialog.Builder.positiveButton(text: String = "Okay", handleClick: (which: Int) -> Unit = {}) {
        this.setPositiveButton(text, { dialogInterface, which-> handleClick(which) })
    }

    fun AlertDialog.Builder.negativeButton(text: String = "Cancel", handleClick: (which: Int) -> Unit = {}) {
        this.setNegativeButton(text, { dialogInterface, which-> handleClick(which) })
    }



}
