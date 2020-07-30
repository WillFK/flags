package fk.home.flags.ui.countries

import fk.home.flags.data.CountriesRepository

class CountriesProcessor {

    // TODO inject
    private val repository by lazy { CountriesRepository() }

    val processor: suspend (CountriesAction) -> CountriesResult = { countriesAction ->
        when (countriesAction) {
            CountriesAction.Load -> repository.getCountries()
        }
    }
}