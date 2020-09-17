package fk.home.flags.ui.countries

sealed class CountriesAction {

    object Load : CountriesAction()

    //TODO sealed class
    object StartSearch : CountriesAction()

    data class Search(val value: String): CountriesAction()

    object FinishSearch : CountriesAction()
}
