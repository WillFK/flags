package fk.home.flags.ui.countries

import fk.home.flags.data.CountriesRepository

class CountriesProcessor {

    // TODO inject
    private val repository by lazy { CountriesRepository() }

    val processor: suspend (CountriesAction) -> CountriesResult = { action ->
        when (action) {
            CountriesAction.Load -> repository.getCountries()
            is CountriesAction.Search -> repository.getCountries(action.term)
        }
    }
}