package dbs.ie;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Content {
    @PrimaryKey
    public final int Topic_ID;
    public String Topic_Name;
    public String TopicDescription;
    public String Sub_Topic_Name;
    public String Sub_TopicDescription;



    public Content(int Topic_ID, String Topic_Name, String TopicDescription, String Sub_Topic_Name, String Sub_TopicDescription){

        this.Topic_ID = Topic_ID;
        this.Topic_Name = Topic_Name;
        this.TopicDescription = TopicDescription;
        this.Sub_Topic_Name = Sub_Topic_Name;
        this.Sub_TopicDescription = Sub_TopicDescription;

    }

}
