package fk.home.flags.data

import fk.home.FetchCountriesQuery

sealed class CountriesResult {

    sealed class Load : CountriesResult() {

        data class Fail(val error: Throwable) : Load()

        data class Success(val countries: List<FetchCountriesQuery.Country>) : Load()
    }

    sealed class Search : CountriesResult() {

        object Start : Search()

        data class Finish(val countries: List<FetchCountriesQuery.Country>): Search()

        //object Finish : CountriesResult()
    }
}