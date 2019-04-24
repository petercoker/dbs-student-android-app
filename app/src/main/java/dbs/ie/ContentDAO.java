package dbs.ie;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ContentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addContent(Content content);

    @Query("SELECT * FROM Content")
    public List<Content> getAllContent();


    @Query("SELECT * FROM Content WHERE Topic_ID = :Topic_ID")
    public Content getContent(long Topic_ID);


    @Query("SELECT * FROM User LIMIT 1")
    public Content getContent();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateContent(Content content);


    @Query("DELETE FROM Content")
    void removeAllContent();
}