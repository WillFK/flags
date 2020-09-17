package fk.home.flags.ui.countries

import androidx.ui.input.TextFieldValue
import fk.home.flags.data.CountriesRepository
import fk.home.flags.data.GetCountryResult

class CountriesProcessor {

    // TODO inject
    private val repository by lazy { CountriesRepository() }

    val processor: suspend (CountriesAction) -> CountriesResult = { action ->
        when (action) {
            CountriesAction.Load -> repository.getCountries().toLoadResult()
            CountriesAction.StartSearch -> CountriesResult.Search.Start
            CountriesAction.FinishSearch -> repository.getCountries().toSearchResult()
            is CountriesAction.Search -> repository.getCountries(action.value).toLoadResult()
        }
    }

    private fun GetCountryResult.toLoadResult() =
        when (this) {
            is GetCountryResult.Success -> CountriesResult.Load.Success(this.data)
            is GetCountryResult.Fail -> CountriesResult.Load.Fail(this.error)
        }

    private fun GetCountryResult.toSearchResult() =
        when (this) {
            is GetCountryResult.Success -> CountriesResult.Search.Finish(this.data)
            is GetCountryResult.Fail -> CountriesResult.Load.Fail(this.error)
        }
}