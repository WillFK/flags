package fk.home.flags.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fk.home.flags.R
import fk.home.flags.TempClient
import kotlinx.android.synthetic.main.fragment_countries_list.*

class CountriesListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_countries_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setupUi()
        tempKickStart()
    }

    private fun setupUi() {
        TODO()
    }

    private fun tempKickStart() {
        TempClient.runStuff().let { list ->
            responseTxt.text = list.toString()
        }
    }
}