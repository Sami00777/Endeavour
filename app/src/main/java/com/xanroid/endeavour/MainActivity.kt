package com.xanroid.endeavour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragmentContainerView)
        bottomNavigationView.setupWithNavController(navController)


    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val dialogExit = View.inflate(this, R.layout.dialog_exit, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogExit)
        val dialog = builder.create()
        val btnCancel = dialogExit.findViewById<Button>(R.id.buttonCancel)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        val btnYes = dialogExit.findViewById<Button>(R.id.buttonYes)
        btnYes.setOnClickListener {
            finishAffinity()
            dialog.dismiss()
        }
        dialog.setCancelable(true)
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

}