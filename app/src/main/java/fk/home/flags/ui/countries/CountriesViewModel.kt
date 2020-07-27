package fk.home.flags.ui.countries

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fk.home.flags.TempClient
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception

class CountriesViewModel : ViewModel() {

    // TODO injection
    private val processor = CountriesProcessor()
    private val reducer = CountriesReducer()

    val states = MutableLiveData<CountriesState>()

    // TODO implement support for effects
    //private val effects = MutableLiveData<CountriesState>()

    val intentChannel = Channel<CountriesIntent>()

    init {
        viewModelScope.launch {
            intentChannel.consumeAsFlow()
                .map { it.toAction() }
                .map(processor.processor)
                .scan(CountriesState(loading = true), { state, result ->
                    reducer.reduce(state, result)
                })
                .collect {
                    states.postValue(it)
                }
        }
        intentChannel.receiveAsFlow()
    }
}