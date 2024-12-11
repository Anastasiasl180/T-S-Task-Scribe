package com.aopr.taskscribe

import android.app.Application
import com.aopr.home.home_screen.di.HomePresentationModule
import com.aopr.notes_data.di.NotesDataModule
import com.aopr.notes_domain.di.NotesDomainModule
import com.aopr.notes_presentation.di.NotesPresentationModule
import com.aopr.shared_data.di.SharedDataModule
import com.aopr.shared_domain.di.SharedDomainModule
import com.aopr.shared_ui.di.SharedUiModule
import com.aopr.tasks_data.di.TasksDataModule
import com.aopr.tasks_domain.di.TasksDomainModule
import com.aopr.tasks_presentation.di.TasksPresentationModule
import com.example.bookmarks_data.di.BookmarksDataModule
import com.example.bookmarks_domain.di.BookmarksDomainModule
import com.example.bookmarks_presentation.di.BookmarksPresentationModule
import com.example.home_data.impl.di.HomeDataModule
import com.example.home_domain.di.HomeDomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.module

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(
                HomePresentationModule().module,
                HomeDataModule().module,
                HomeDomainModule().module,
                NotesPresentationModule().module,
                NotesDomainModule().module,
                NotesDataModule().module,
                SharedUiModule().module,
                SharedDataModule().module,
                SharedDomainModule().module,
                TasksPresentationModule().module,
                TasksDataModule().module,
                TasksDomainModule().module,
                BookmarksDomainModule().module,
                BookmarksDataModule().module,
                BookmarksPresentationModule().module
            )

        }
    }
}