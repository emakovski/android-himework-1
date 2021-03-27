package by.it.academy.homework_7_rx_java.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.it.academy.homework_7_rx_java.data.CarInfo
import by.it.academy.homework_7_rx_java.data.WorkInfo


@Database(entities = [CarInfo::class, WorkInfo::class], version = 2, exportSchema = false)
abstract class DataBaseCarInfo : RoomDatabase() {
    abstract fun getCarInfoDAO(): CarInfoDAO
    abstract fun getWorkInfoDAO(): WorkInfoDAO

    companion object {
        private var INSTANCE: DataBaseCarInfo? = null
        fun getDataBase(context: Context): DataBaseCarInfo {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DataBaseCarInfo::class.java,
                        "database")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return INSTANCE as DataBaseCarInfo
        }
    }
}