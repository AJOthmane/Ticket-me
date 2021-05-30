package ma.ensias.ticket_me.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Event.class}, version = 1)
public abstract class DBHandler extends RoomDatabase {

    public abstract EventDao eventDao();

    private static DBHandler INSTANCE;

    public static DBHandler getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DBHandler.class, "DATABASE")
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }
}
