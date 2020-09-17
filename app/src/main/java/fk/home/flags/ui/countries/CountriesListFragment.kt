package fk.home.flags.ui.countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.ui.core.ContentScale
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextField
import androidx.ui.foundation.clickable
import androidx.ui.foundation.gestures.DragDirection
import androidx.ui.foundation.gestures.draggable
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
import androidx.ui.res.vectorResource
import androidx.ui.savedinstancestate.savedInstanceState
import androidx.ui.unit.dp
import fk.home.FetchCountriesQuery
import fk.home.flags.R
import kotlinx.coroutines.*
import timber.log.Timber

@ExperimentalCoroutinesApi
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
                        TopBar(state = state)
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
    fun TopBar(state: CountriesState) {
        if (state.searching) {

            // TODO should this be part of uiModel? I tried to put it there but ended up with some glitches
            val searchState = savedInstanceState(saver = TextFieldValue.Saver) { TextFieldValue() }

            TopAppBar(
                title = {
                    TextField(
                        value = searchState.value,
                        onValueChange = {
                            searchState.value = it
                            performSearch(it.text)
                        }
                    )
                },
                actions = {
                    Image(
                        asset = vectorResource(id = R.drawable.ic_close_action),
                        modifier = Modifier.width(24.dp) + Modifier.height(24.dp) + Modifier.clickable(
                            onClick = {
                                searchState.value = TextFieldValue()
                                finishSearch()
                            }
                        ),
                        contentScale = ContentScale.Fit
                    )
                }
            )
        } else {
            TopAppBar(
                title = { Text(text = "Countries") },
                actions = {
                    Image(
                        asset = vectorResource(id = R.drawable.ic_search_action),
                        modifier = Modifier.width(24.dp) + Modifier.height(24.dp) + Modifier.clickable(
                            onClick = { startSearch() }
                        ),
                        contentScale = ContentScale.Fit
                    )
                }
            )
        }
    }

    @Composable
    fun RenderCountry(country: FetchCountriesQuery.Country) {

        val max = 300.dp
        val min = 0.dp
        val (minPx, maxPx) = with(DensityAmbient.current) {
            min.toPx() to max.toPx()
        }

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

    onpa

    private fun startSearch() {
        launch {
            viewModel.intentChannel.send(CountriesIntent.StartSearch)
        }
    }

    private fun performSearch(value: String) {
        launch {
            viewModel.intentChannel.send(CountriesIntent.Search(value))
        }
    }

    private fun finishSearch() {
        launch {
            viewModel.intentChannel.send(CountriesIntent.FinishSearch)
        }
    }

    private fun onCardClick(country: FetchCountriesQuery.Country) {
        Timber.tag("FKZ").d("click! ${country.name}")
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}