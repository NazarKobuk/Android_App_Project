package com.zhaldak.myapplication

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation

import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 * The class implements the main screen of the application
 Is the starting point of run the application
 *
 */
 
class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController

/**
 *
 * Activity creation processing
 *
 */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
//        navController.navigate(R.id.contactGroupsFragment)

    }

/**
 *
 * Create an optional menu in the ActionBar.
 *
 * @param menu is a menu object that will hold information
 * from an xml file
 */
 
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

/**
 *Handling a menu item. <br/>
 * @param item MenuItem object with item information
 * in the Menu list <br/>
 * The object stores a link to the View instance <br/>
 * As well as information about the position in the list and more
 */

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
