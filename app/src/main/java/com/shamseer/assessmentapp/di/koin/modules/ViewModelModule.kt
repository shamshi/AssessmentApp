package com.shamseer.assessmentapp.di.koin.modules

import com.shamseer.assessmentapp.ui.activities.details.DetailsViewModel
import com.shamseer.assessmentapp.ui.base.BaseViewModel
import com.shamseer.assessmentapp.ui.activities.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Shamseer on 5/29/20.
 */

/** Koin dependency injection framework
  * View Model Module declaration, a space to gather all view model definitions */
val viewModelModule = module {

    // Base view model definition
    viewModel {
        BaseViewModel()
    }

    // Main view model definition
    viewModel {
        MainViewModel()
    }

    // Details view model definition
    viewModel {
        DetailsViewModel()
    }
}