package fk.home.flags.data

import com.apollographql.apollo.coroutines.toDeferred
import fk.home.FetchCountriesQuery

class CountriesRepository {

    private var cache: List<FetchCountriesQuery.Country>? = null

    suspend fun getCountries(filter: String? = null): GetCountryResult {

        return try {
            loadCountries().filterCountries(filter).let {
                GetCountryResult.Success(it)
            }
        } catch (error: Throwable) {
            GetCountryResult.Fail(error)
        }
    }

    private fun List<FetchCountriesQuery.Country>.filterCountries(filter: String?): List<FetchCountriesQuery.Country> =
        if (filter.isNullOrEmpty())
            this
        else
            this.filter { it.name.contains(filter, ignoreCase = true) }

    private suspend fun loadCountries(): List<FetchCountriesQuery.Country> {
        return cache.let {
            it
                ?: ApolloHelper.client.query(FetchCountriesQuery()).toDeferred().await()
                    .let { response ->
                        response.errors?.let {
                            throw ApolloError(it)
                        }
                        response.data!!.country!!.mapNotNull { it }.apply {
                            cache = this
                        }
                    }
        }
    }
}

sealed class GetCountryResult {

    data class Success(val data: List<FetchCountriesQuery.Country>) : GetCountryResult()

    data class Fail(val error: Throwable) : GetCountryResult()
}

