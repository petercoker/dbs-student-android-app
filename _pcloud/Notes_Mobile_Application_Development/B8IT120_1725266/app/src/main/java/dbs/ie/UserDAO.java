package dbs.ie;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(User user);

    @Query("SELECT * FROM User")
    public List<User> getAllUsers();

    @Query("SELECT * FROM User WHERE User_ID = :User_ID")
    public User getUser(long User_ID);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUser(User user);

    @Query("DELETE FROM user")
    void removeAllUsers();

}
