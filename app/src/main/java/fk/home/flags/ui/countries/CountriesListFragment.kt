package fk.home.flags.ui.countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.Composable
import androidx.compose.Recomposer
import androidx.compose.remember
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.*
import androidx.ui.unit.dp
import fk.home.FetchCountriesQuery
import fk.home.flags.R
import kotlinx.coroutines.*

class CountriesListFragment : Fragment(), CoroutineScope by MainScope() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_countries_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewModelProvider(this).get(CountriesViewModel::class.java).let { viewModel ->
            viewModel.states.observe(this@CountriesListFragment.viewLifecycleOwner,
                Observer<CountriesState> { state -> state?.let(::render) })

            launch {
                //delay(2000)
                viewModel.intentChannel.send(CountriesIntent.Load)
            }
        }
    }

    private fun render(state: CountriesState) {
        //TODO lots of stuff
        (view as? ViewGroup)?.let {
            if (state.data != null)
                it.setContent(Recomposer.current()) {
                    NewStory(state.data)
                }
        }
    }

    @Composable
    fun NewStory(countries: List<FetchCountriesQuery.Country>) {
        MaterialTheme {

            val scaffoldState = remember { ScaffoldState() }

            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    TopAppBar(title = {
                        Text(text = "Countries")
                    })
                },
                bodyContent = {
                    LazyColumnItems(items = countries) {
                        RenderCountry(country = it)
                    }
                },
                bottomBar = {
                    // Nothing yet
                }
            )
        }
    }

    @Composable
    fun RenderCountry(country: FetchCountriesQuery.Country) {

        Card(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.fillMaxWidth() + Modifier.padding(10.dp),
            color = Color.LightGray
        ) {

            Row(modifier = Modifier.padding(10.dp)) {

                Column {

                    Text(
                        text = "${country.flag!!.emoji} ${country.name} - ${country.alpha2Code}",
                        style = MaterialTheme.typography.h6)
                    Text(text = country.capital)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}