package fk.home.flags.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fk.home.flags.R
import fk.home.flags.ui.countries.CountriesListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUi()
    }

    private fun setupUi() {
        supportFragmentManager.beginTransaction()
            .add(R.id.rootView,
                CountriesListFragment()
            )
            .commitAllowingStateLoss()
    }
}
