package fk.home.flags.data

import com.apollographql.apollo.coroutines.toDeferred
import fk.home.FetchCountriesQuery
import fk.home.flags.ui.countries.CountriesResult

class CountriesRepository {

    suspend fun getCountries(): CountriesResult {
        return try {
            ApolloHelper.client.query(FetchCountriesQuery()).toDeferred().await().let { response ->
                response.errors?.let {
                    throw ApolloError(it)
                }
                CountriesResult.Success(response.data!!.country!!.mapNotNull { it })
            }
        } catch (error: Throwable) {
            CountriesResult.Error(error)
        }
    }
}

