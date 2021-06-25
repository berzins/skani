package lv.zesloka.skani.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import lv.zesloka.skani.framework.Config

@Database(entities = [SongEntity::class], version = Config.DB_VERSION)
abstract class DatabaseService: RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "song.db"
        private var instance: DatabaseService? =  null

        private fun create(context: Context): DatabaseService =
            Room.databaseBuilder(context, DatabaseService::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()

        public fun getInstance(context: Context): DatabaseService =
            (instance
                ?: create(
                    context
                )
                    .also { instance = it })
    }

    abstract fun songDao(): SongDao


}