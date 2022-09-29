package com.xanroid.endeavour

import android.app.Activity
import androidx.appcompat.app.AlertDialog

class LoadingDialog(private val activity: Activity) {

    private var alertDialog: AlertDialog? = null

    fun startLoading(){
        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_loading, null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogView).setCancelable(false)
        alertDialog = builder.create()
        alertDialog!!.show()

    }
    fun stopLoading(){
        alertDialog?.dismiss()
    }

}