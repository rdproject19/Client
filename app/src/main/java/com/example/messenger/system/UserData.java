        package com.example.messenger.system;


        import android.content.Context;
        import android.content.SharedPreferences;

/**
 * Auxiliary enum for consistent keys.
 */
enum Keys
{
    USERNAME("Username"),
    FULLNAME("Fullname"),
    TOKEN("Token"),
    REMEMBER("Remember");


    String key;

    Keys(String key)
    {
        this.key = key;
    }

    public String get()
    {
        return this.key;
    }

}

/**
 * Store and access user data
 *
 * @author Karim Abdulahi
 */
public class UserData
{

    private Context context;
    private SharedPreferences sp;



    public UserData(Context context)
    {
        this.context = context;
        this.sp = context.getSharedPreferences("preferences", 0);
    }

    private void insert(Keys key, String value)
    {
        SharedPreferences.Editor handle = this.sp.edit();
        handle.putString(key.get(), value);
        handle.apply();
    }
    private void insert(Keys key, boolean value)
    {
        SharedPreferences.Editor handle = this.sp.edit();
        handle.putBoolean(key.get(), value);
        handle.apply();
    }

    public boolean setUsername(String username)
    {
        if(username.length() > 24 || username.length() < 3) { return false; }
        this.insert(Keys.USERNAME, username);
        return true;
    }

    public boolean setFullname(String fullname)
    {
        if(fullname.length() > 64 || fullname.length() < 6) { return false; }
        this.insert(Keys.FULLNAME, fullname);
        return true;
    }

    public boolean setToken(String token)
    {
        if(token.length() != 128) return false;
        this.insert(Keys.TOKEN, token);
        return true;
    }

    public void rememberMe(boolean r)
    {
        this.insert(Keys.REMEMBER, r);
    }

    public String getUsername() { return this.sp.getString(Keys.USERNAME.get(), null); }
    public String getFullname() { return this.sp.getString(Keys.FULLNAME.get(), null); }
    public String getToken()    { return this.sp.getString(Keys.TOKEN.get(), null); }
    public String getRememberMe()    { return this.sp.getString(Keys.REMEMBER.get(), null); }


}