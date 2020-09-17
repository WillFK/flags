package fk.home.flags.ui.countries

sealed class CountriesIntent {

    object Load : CountriesIntent()

    //TODO sealed class
    object StartSearch : CountriesIntent()

    object FinishSearch : CountriesIntent()

    data class Search(val value: String) : CountriesIntent()

    fun toAction(): CountriesAction =
        when(this) {
            Load -> CountriesAction.Load
            StartSearch -> CountriesAction.StartSearch
            FinishSearch -> CountriesAction.FinishSearch
            is Search -> CountriesAction.Search(value = value)
        }
}
