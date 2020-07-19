package fk.home.flags

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import fk.home.FetchCountriesQuery
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

object TempClient: CoroutineScope  {

    override val coroutineContext: CoroutineContext =
        Dispatchers.Default + Job()

    fun runStuff() : List<FetchCountriesQuery.Country> {
        return runBlocking {
            val apolloClient: ApolloClient = getApolloClient()
            val query = FetchCountriesQuery()
            val response = apolloClient.query(query).toDeferred().await()
            response.data!!.country!!.mapNotNull { it }
        }
    }

    private fun getApolloClient(): ApolloClient {
        return ApolloClient.builder()
            .serverUrl("https://countries-274616.ew.r.appspot.com/")
            .build()
    }
}