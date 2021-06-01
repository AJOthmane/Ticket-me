package ma.ensias.ticket_me.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Event {

    @PrimaryKey
    public int id_event;

    @ColumnInfo(name = "name_event")
    public String name_event;

    public int getId() {
        return id_event;
    }

    public String getName() {
        return name_event;
    }
}
