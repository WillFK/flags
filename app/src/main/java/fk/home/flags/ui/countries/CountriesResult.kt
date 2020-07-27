package fk.home.flags.ui.countries

import fk.home.FetchCountriesQuery
import java.lang.Exception

sealed class CountriesResult {

    data class Error(val error: Throwable) : CountriesResult()

    data class Success(val countries: List<FetchCountriesQuery.Country>) : CountriesResult()
}