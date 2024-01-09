package com.example.businessscout

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.businessscout.data.BussinessCollection
import com.example.businessscout.databinding.ActivityMainBinding
import com.example.businessscout.ui.home.CollectionsViewModel
import com.google.gson.Gson
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val collectionsViewModel: CollectionsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_collection, null)
            val dialogBuilder = AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Add Collection")
                .setPositiveButton("Add", DialogInterface.OnClickListener { dialog, id ->
                    // Get the name and color from dialogView and add to user.collections
                    val collectionName = dialogView.findViewById<EditText>(R.id.collectionName).text.toString()
                    // Assume collectionColor is a String or an int representing the color
                    val collectionColor = dialogView.findViewById<EditText>(R.id.collectionColor).text.toString()

                    // insert it into db

                   collectionsViewModel.insertCollection(BussinessCollection(collectionName, collectionColor))


                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

            val dialog = dialogBuilder.create()
            dialog.show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }





    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}