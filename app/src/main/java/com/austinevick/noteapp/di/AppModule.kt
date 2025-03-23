package com.austinevick.noteapp.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.austinevick.noteapp.BiometricPromptManager
import com.austinevick.noteapp.data.NoteDao
import com.austinevick.noteapp.data.NoteDatabase
import com.austinevick.noteapp.data.NoteRepository
import com.austinevick.noteapp.data.NoteRepositoryImpl
import com.austinevick.noteapp.preference.NotePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            NoteDatabase::class.java, "notes_database"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase) = noteDatabase.noteDao()

    @Singleton
    @Provides
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(noteDao)
    }

    @Singleton
    @Provides
    fun provideNotePreferences(@ApplicationContext context: Context) = NotePreferences(context)


    @Singleton
    @Provides
    fun provideBiometricPromptManager(context: AppCompatActivity) =
        BiometricPromptManager(context)

}


