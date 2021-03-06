package ma.ensias.ticket_me.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventDao {

    @Query("SELECT * FROM event")
    List<Event> getEventList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEvent(Event... events);
}
