package com.austinevick.noteapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE isArchived = 0 AND isLocked = 0 ORDER BY isPinned DESC")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE isLocked = 1")
    fun getLockedNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE isArchived = 1")
    fun getArchivedNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE isPinned = 1")
    fun getPinnedNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): Flow<NoteEntity>

    @Upsert(entity = NoteEntity::class)
    suspend fun insertNote(note: NoteEntity)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNote(id: Int)

    @Query("UPDATE notes SET isArchived = 1 WHERE id = :id")
    suspend fun archiveNote(id: Int)

    @Query("UPDATE notes SET isPinned = 1 WHERE id = :id")
    suspend fun pinNote(id: Int)

    @Query("UPDATE notes SET isPinned = 0 WHERE id = :id")
    suspend fun unpinNote(id: Int)

    @Query("UPDATE notes SET isArchived = 0 WHERE id = :id")
    suspend fun unarchiveNote(id: Int)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Query("SELECT * FROM notes ORDER BY isPinned DESC")
    fun getNotesSortedByPinned(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'")
    fun searchNotes(query: String): Flow<List<NoteEntity>>


}