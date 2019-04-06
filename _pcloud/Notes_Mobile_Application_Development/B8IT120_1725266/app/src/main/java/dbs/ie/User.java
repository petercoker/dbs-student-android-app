package dbs.ie;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public final int User_ID;
    public String FullName;
    public String Email;
    public String Username;
    public String Password;
    public String User_Type;
    public String Avater;
    public String DateCreated;
    public String LastLogin;
    public int Active;

    public User(int User_ID, String FullName, String Email, String Username, String Password, String User_Type, String Avater, String DateCreated, String LastLogin, int Active){
        this.User_ID = User_ID;
        this.FullName = FullName;
        this.Email = Email;
        this.Username = Username;
        this.Password = Password;
        this.User_Type = User_Type;
        this.Avater = Avater;
        this.DateCreated = DateCreated;
        this.LastLogin = LastLogin;
        this.Active = Active;
    }
}
