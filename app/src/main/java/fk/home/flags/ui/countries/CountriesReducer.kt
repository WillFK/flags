package fk.home.flags.ui.countries

import fk.home.flags.data.CountriesResult

class CountriesReducer {

    val reduce: (CountriesState, CountriesResult) -> CountriesState = { oldState, result ->
        when (result) {
            is CountriesResult.Load.Success -> {
                oldState.copy(
                    loading = false,
                    data = result.countries,
                    error = null
                )
            }

            is CountriesResult.Load.Fail -> {
                oldState.copy(
                    loading = false,
                    data = null,
                    error = null
                )
            }

            CountriesResult.Search.Start -> {
                oldState.copy(
                    searching = true,
                    loading = false
                )
            }

            is CountriesResult.Search.Finish -> {
                oldState.copy(
                    searching = false,
                    data = result.countries
                )
            }
        }
    }
}