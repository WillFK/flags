package fk.home.flags.ui.countries

sealed class CountriesIntent {

    object Load : CountriesIntent()

    data class Search(val input: String) : CountriesIntent()

    fun toAction(): CountriesAction =
        when(this) {
            Load -> CountriesAction.Load
            is Search -> CountriesAction.Search(term = input)
        }
}