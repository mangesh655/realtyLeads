package com.example.reltyleads.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.reltyleads.persistence.database.converter.CalendarConverter
import com.example.reltyleads.persistence.database.dao.BookingDao
import com.example.reltyleads.persistence.database.dao.ProjectDao
import com.example.reltyleads.persistence.database.dao.TowerDao
import com.example.reltyleads.persistence.database.dao.UnitDao
import com.example.reltyleads.persistence.database.entity.ApplicantDb
import com.example.reltyleads.persistence.database.entity.ApplicantDocumentDb
import com.example.reltyleads.persistence.database.entity.BookingDb
import com.example.reltyleads.persistence.database.entity.ProjectDb
import com.example.reltyleads.persistence.database.entity.TowerDb
import com.example.reltyleads.persistence.database.entity.UnitDb

/**
 * The Room database for this app.
 */
@Database(
    entities = [
        ProjectDb::class,
        TowerDb::class,
        UnitDb::class,
        ApplicantDb::class,
        ApplicantDocumentDb::class,
        BookingDb::class
    ],
    version = AppDatabase.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(CalendarConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun projectDao() : ProjectDao
    abstract fun towerDao() : TowerDao
    abstract fun unitDao() : UnitDao
    abstract fun bookingDao() : BookingDao

    companion object {
        private val DATABASE_NAME = "realty-db"
        const val DATABASE_VERSION = 21

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}