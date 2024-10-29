package com.aopr.notes_presentation.di

import com.aopr.notes_domain.di.NotesDomainModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module([NotesDomainModule::class])
@ComponentScan("com.aopr.notes_presentation")
class NotesPresentationModule