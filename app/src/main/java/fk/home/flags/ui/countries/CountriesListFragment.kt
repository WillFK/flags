package fk.home.flags.ui.countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.Composable
import androidx.compose.Recomposer
import androidx.compose.remember
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
import androidx.ui.unit.dp
import fk.home.FetchCountriesQuery
import fk.home.flags.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber

class CountriesListFragment : Fragment(), CoroutineScope by MainScope() {

    private lateinit var viewModel: CountriesViewModel

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
            this.viewModel = viewModel
            launch {
                viewModel.intentChannel.send(CountriesIntent.Load)
            }
            //TODO I think I can move this call to somewhere else
            render()
        }
    }

    private fun render() {
        //TODO lots of stuff
        (view as? ViewGroup)?.let {
            it.setContent(Recomposer.current()) {
                NewStory()
            }
        }
    }

    @Composable
    fun NewStory() {
        MaterialTheme {

            viewModel.states.observeAsState().value?.let { state ->

                //To I really need this state? TODO
                val scaffoldState = remember { ScaffoldState() }

                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar(title = {
                            Text(text = "Countries")
                        })
                    },
                    bodyContent = {
                        state.data?.let { data ->
                            LazyColumnItems(items = data) {
                                RenderCountry(country = it)
                            }
                        }
                    },
                    bottomBar = {
                        // Nothing yet
                    }
                )
            }

        }
    }

    @Composable
    fun RenderCountry(country: FetchCountriesQuery.Country) {

        Card(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.fillMaxWidth() + Modifier.padding(10.dp) + Modifier.clickable(
                onClick = { onCardClick(country) }
            ),
            color = Color.LightGray
        ) {
            Row(modifier = Modifier.padding(10.dp)) {
                Column {
                    Text(
                        text = "${country.flag!!.emoji} ${country.name} - ${country.alpha2Code}",
                        style = MaterialTheme.typography.h6
                    )
                    Text(text = country.capital)
                }
            }
        }
    }

    private fun onCardClick(country: FetchCountriesQuery.Country) {
        Timber.tag("FKZ").d("click! ${country.name}")
        launch {
            viewModel.intentChannel.send(CountriesIntent.Search("br"))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}