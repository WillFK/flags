package fk.home.flags.ui.countries

sealed class CountriesAction {

    object Load : CountriesAction()

    data class Search(val term: String): CountriesAction()
}
