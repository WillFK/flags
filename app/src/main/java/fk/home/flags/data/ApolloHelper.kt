package fk.home.flags.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Error
import java.lang.RuntimeException

object ApolloHelper {

    val client: ApolloClient by lazy { ApolloClient.builder()
        .serverUrl("https://countries-274616.ew.r.appspot.com/")
        .build() }
}

class ApolloError(errors: List<Error>): RuntimeException()