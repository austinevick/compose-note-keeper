package com.austinevick.noteapp.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NoteRepository {
    fun getNotesSortedByPinned(): Flow<List<NoteEntity>>
    fun getNotes(): Flow<List<NoteEntity>>
    fun getLockedNotes(): Flow<List<NoteEntity>>
    fun getArchivedNotes(): Flow<List<NoteEntity>>
    fun getPinnedNotes(): Flow<List<NoteEntity>>
    fun getNoteById(id: Int): Flow<NoteEntity>
    fun searchNotes(query: String): Flow<List<NoteEntity>>
    suspend fun archiveNote(id: Int)
    suspend fun unarchiveNote(id: Int)
    suspend fun pinNote(id: Int)
    suspend fun unpinNote(id: Int)
    suspend fun insertNote(note: NoteEntity)
    suspend fun deleteNote(note: Int)
    suspend fun updateNote(note: NoteEntity)
}


class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : NoteRepository {
    override fun getNotesSortedByPinned(): Flow<List<NoteEntity>> {
        return noteDao.getNotesSortedByPinned()
    }

    override fun getNotes(): Flow<List<NoteEntity>> {
        return noteDao.getNotes()
    }

    override fun getLockedNotes(): Flow<List<NoteEntity>> {
        return noteDao.getLockedNotes()
    }

    override fun getArchivedNotes(): Flow<List<NoteEntity>> {
        return noteDao.getArchivedNotes()
    }

    override fun getPinnedNotes(): Flow<List<NoteEntity>> {
        return noteDao.getPinnedNotes()
    }

    override fun getNoteById(id: Int): Flow<NoteEntity> {
        return noteDao.getNoteById(id)
    }

    override fun searchNotes(query: String): Flow<List<NoteEntity>> {
        return noteDao.searchNotes(query)
    }

    override suspend fun archiveNote(id: Int) {
        noteDao.archiveNote(id)
    }

    override suspend fun unarchiveNote(id: Int) {
        noteDao.unarchiveNote(id)
    }

    override suspend fun pinNote(id: Int) {
        noteDao.pinNote(id)
    }

    override suspend fun unpinNote(id: Int) {
        noteDao.unpinNote(id)
    }

    override suspend fun insertNote(note: NoteEntity) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNote(id: Int) {
        noteDao.deleteNote(id)
    }

    override suspend fun updateNote(note: NoteEntity) {
        noteDao.updateNote(note)
    }

}