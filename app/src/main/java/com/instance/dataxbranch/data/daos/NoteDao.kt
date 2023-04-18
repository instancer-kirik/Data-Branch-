package com.instance.dataxbranch.data.daos

import android.content.Context
import androidx.room.*
import com.instance.dataxbranch.data.entities.NoteEntity

@Dao
abstract class NoteDao {
    @Insert(onConflict= OnConflictStrategy.REPLACE)
    abstract fun insert(vararg Note: NoteEntity)

    @Update
    abstract fun update(vararg Note: NoteEntity)

    @Delete
    abstract fun delete(vararg Note: NoteEntity)


    @Upsert
    abstract fun save(Note: NoteEntity):Long

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    abstract fun insertAll(abilities: List<NoteEntity>)

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    //abstract fun save(items: Iterable<NoteEntity>)
    /*@Query("SELECT * FROM abilities WHERE uid=:uid") not many to many here, no need
    abstract fun getAbilites(uid:Long): List<NoteEntity>*/
    @Query("SELECT * FROM notes")
    abstract fun getNotes(): Array<NoteEntity>
    @Query("SELECT * FROM notes WHERE uuid = :uuid")
    abstract fun getNotesByUuid(uuid:String): NoteEntity
    @Query("SELECT * FROM notes WHERE context = :context")
    abstract fun getNotesByContext(context: Context): NoteEntity
}