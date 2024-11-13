package com.aopr.taskscribe

import android.app.Application
import com.aopr.home.home_screen.di.HomeModule
import com.aopr.notes_data.di.NotesDataModule
import com.aopr.notes_domain.di.NotesDomainModule
import com.aopr.notes_presentation.di.NotesPresentationModule
import com.aopr.shared_data.di.SharedDataModule
import com.aopr.shared_domain.di.SharedDomainModule
import com.aopr.shared_ui.SharedUiModule
import com.aopr.tasks_data.di.TasksDataModule
import com.aopr.tasks_domain.di.TasksDomainModule
import com.aopr.tasks_presentation.di.TasksPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.module

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(
                HomeModule().module,
                NotesPresentationModule().module,
                NotesDomainModule().module,
                NotesDataModule().module,
                SharedUiModule().module,
                SharedDataModule().module,
                SharedDomainModule().module,
                TasksPresentationModule().module,
                TasksDataModule().module,
                TasksDomainModule().module,
            )

        }
    }
}