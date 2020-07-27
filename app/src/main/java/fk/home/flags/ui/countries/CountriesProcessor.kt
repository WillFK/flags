package fk.home.flags.ui.countries

import fk.home.flags.TempClient

class CountriesProcessor {

    val processor: suspend (CountriesAction) -> CountriesResult = { countriesAction ->

        when (countriesAction) {
            CountriesAction.Load -> TempClient.runStuff()
        }
    }
}