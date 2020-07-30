package fk.home.flags.ui.countries

sealed class CountriesIntent {

    object Load : CountriesIntent()

    fun toAction(): CountriesAction =
        when(this) {
            Load -> CountriesAction.Load
        }
}