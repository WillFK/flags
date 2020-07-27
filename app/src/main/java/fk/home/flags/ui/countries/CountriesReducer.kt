package fk.home.flags.ui.countries

class CountriesReducer {

    val reduce: (CountriesState, CountriesResult) -> CountriesState = { oldState, result ->
        when (result) {
            is CountriesResult.Success -> {
                oldState.copy(
                    loading = false,
                    data = result.countries,
                    error = null
                )
            }

            is CountriesResult.Error -> {
                oldState.copy(
                    loading = false,
                    data = null,
                    error = null
                )
            }
        }
    }
}