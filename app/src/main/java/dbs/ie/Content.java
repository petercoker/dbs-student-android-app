package dbs.ie;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Content {
    @PrimaryKey
    public final int Topic_ID;
    public String Topic_Name;
    public String TopicDescription;


    public Content(int Topic_ID, String Topic_Name, String TopicDescription){

        this.Topic_ID = Topic_ID;
        this.Topic_Name = Topic_Name;
        this.TopicDescription = TopicDescription;

    }

}
