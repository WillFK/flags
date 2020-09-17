package fk.home.flags.ui.countries

import androidx.ui.input.TextFieldValue
import fk.home.FetchCountriesQuery

data class CountriesState(
    val searching: Boolean,
    val loading: Boolean = false,
    val data: List<FetchCountriesQuery.Country>? = null,
    val error: Throwable? = null
)