package com.softtehnica.freya.configs

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.softtehnica.freya.R

data class MessagePopupIcon(val type: MessagePopupType) {
    val icon: Int
        get() = when (type) {
            MessagePopupType.ERROR -> R.drawable.ic_error_popup
            MessagePopupType.INFO -> R.drawable.ic_info_popup
            MessagePopupType.WARNING -> R.drawable.ic_warning_popup
            MessagePopupType.NO_NETWORK_CONNECTION -> R.drawable.ic_no_network_connection_popup
        }

}

enum class MessagePopupType {
    ERROR,
    WARNING,
    INFO,
    NO_NETWORK_CONNECTION
    }

/*
    Use Message Popup:
    ------------------------------
    MessagePopup.show(context!!) {
        setTitle("Greet")
        setMessage("Welcome again, want coffee?")
        positiveButton("Yes") {
            showToast("Clicked on Yes")
        }

        negativeButton {
            showToast("Clicked on cancel")
        }
    }
    -------------------------------
 */
class MessagePopup() {
    companion object {
        fun show(context: Context, type: MessagePopupType = MessagePopupType.ERROR, dialogBuilder: AlertDialog.Builder.() -> Unit) {
            val builder = AlertDialog.Builder(context)
            builder.setIcon(MessagePopupIcon(type).icon)
            builder.setTitle(context.getString(R.string.error))
            builder.dialogBuilder()
            val dialog = builder.create()

            dialog.show()
        }

//        fun AlertDialog.Builder.positiveButton(text: String = "Continue", handleClick: (which: Int) -> Unit = {}) {
//            this.setPositiveButton(text, { dialogInterface, which-> handleClick(which) })
//        }
//
//        fun AlertDialog.Builder.negativeButton(text: String = "Cancel", handleClick: (which: Int) -> Unit = {}) {
//            this.setNegativeButton(text, { dialogInterface, which-> handleClick(which) })
//        }
    }
}