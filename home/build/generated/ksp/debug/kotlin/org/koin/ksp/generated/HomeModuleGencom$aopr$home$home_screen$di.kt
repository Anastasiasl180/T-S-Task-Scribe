package org.koin.ksp.generated

import org.koin.core.module.Module
import org.koin.dsl.*
import org.koin.androidx.viewmodel.dsl.viewModel

public val com_aopr_home_home_screen_di_HomeModule : Module get() = module {
	viewModel() { _ -> com.aopr.home.home_screen.viewModel.HomeViewModel() } 
}
public val com.aopr.home.home_screen.di.HomeModule.module : org.koin.core.module.Module get() = com_aopr_home_home_screen_di_HomeModule
