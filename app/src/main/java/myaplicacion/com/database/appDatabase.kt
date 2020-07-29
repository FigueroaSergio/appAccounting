package myaplicacion.com.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Accounts::class], version = 1)
abstract class appDatabase : RoomDatabase() {
    abstract fun accountsDao(): AccountsDao

    companion object {
        private var INSTANCE: appDatabase? = null

        fun getInstance(context: Context): appDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    appDatabase::class.java, "database-name"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
    }


}