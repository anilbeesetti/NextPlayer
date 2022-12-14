package com.arcticoss.nextplayer.core.database.daos

import androidx.room.*
import com.arcticoss.nextplayer.core.database.entities.MediaEntity
import com.arcticoss.nextplayer.core.database.relations.MediaInfoRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mediaEntity: MediaEntity): Long

    @Delete
    suspend fun delete(mediaEntity: MediaEntity)

    @Update
    suspend fun update(mediaEntity: MediaEntity)

    @Query("SELECT * FROM media WHERE id = :id")
    suspend fun get(id: Long): MediaEntity

    @Query("SELECT * FROM media WHERE path = :path")
    suspend fun get(path: String): MediaEntity

    @Transaction
    @Query("SELECT * FROM media WHERE path = :path")
    suspend fun getWithInfo(path: String): MediaInfoRelation

    @Query("SELECT EXISTS(SELECT * FROM media WHERE path = :path )")
    suspend fun isExist(path: String): Boolean

    @Query("SELECT * FROM media")
    fun getMediaEntities(): List<MediaEntity>

    @Transaction
    @Query("SELECT * FROM media")
    fun getMediaWithInfoListStream(): Flow<List<MediaInfoRelation>>

}