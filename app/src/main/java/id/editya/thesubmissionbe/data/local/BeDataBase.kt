package id.editya.thesubmissionbe.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import id.editya.thesubmissionbe.model.ModelMovie
import id.editya.thesubmissionbe.model.ModelTVShow

@Database(entities = [ModelMovie::class, ModelTVShow::class],version = 1)
abstract class BeDataBase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
    abstract fun tvshowDao() : TvshowDao

    companion object{
        private var INSTANCE : BeDataBase? = null
        private val sLock = Object()

        fun getInstance(context: Context): BeDataBase {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BeDataBase::class.java, "BeData.db"
                    )
                         .build()
                }
                return INSTANCE!!
            }
        }
    }
}