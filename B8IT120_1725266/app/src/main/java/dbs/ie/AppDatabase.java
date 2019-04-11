package dbs.ie;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class}, version = 16, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    public abstract UserDAO userDAO();

    public static AppDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "UserDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

            //remove .allowMainThreadQueries() with single thread
        }

        return INSTANCE;
    }

//    thread = new Thread(new Runnable) {
//        @Override
//                public void () {
//            "Do your db operations here"} toString()});
//
//        }
//        }
//
//    }
    public static void destoryInstance(){
        INSTANCE = null;
    }
}

