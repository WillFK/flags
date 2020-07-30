package fk.home.flags.ui.countries

sealed class CountriesEffect {

    data class ShowError(val error: Throwable) : CountriesEffect()
}