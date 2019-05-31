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
    COUNTER("TokenCount"),
    SEED("TokenSeed"),
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


    /**
     * Insert value
     * @param key Accessor key
     * @param value Value to store
     */
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
    private void insert(Keys key, int val)
    {
        SharedPreferences.Editor handle = this.sp.edit();
        handle.putInt(key.get(), val);
        handle.apply();
    }


    /**
     * Set username, length 3 < strlen < 24
     * @param username
     * @return false on failure
     */
    public boolean setUsername(String username)
    {
        if(username.length() > 24 || username.length() < 3) { return false; }
        this.insert(Keys.USERNAME, username);
        return true;
    }

    /**
     * Set fullname, length 6 < strlen < 64
     * @param fullname
     * @return false on failure
     */
    public boolean setFullname(String fullname)
    {
        if(fullname.length() > 64 || fullname.length() < 6) { return false; }
        this.insert(Keys.FULLNAME, fullname);
        return true;
    }


    /**
     * Set last token, must be 128 char long
     * @param token
     * @return true on success
     */
    public boolean setToken(String token)
    {
        if(token.length() != 128) return false;
        this.insert(Keys.TOKEN, token);
        return true;
    }

    /**
     * Set token seed
     * @param token
     * @return true on success
     */
    public boolean setSeed(String token)
    {
        if(token.length() != 128) return false;
        this.insert(Keys.SEED, token);
        return true;
    }

    /**
     * Set token counter (must be > 0).
     * @param i
     * @return true on success
     */
    public boolean setCounter(int i)
    {
        if(i < 0) return false;
        this.insert(Keys.COUNTER, i);
        return true;
    }

    /**
     * Store preference to remember or not
     * @param r
     */
    public void rememberMe(boolean r)
    {
        this.insert(Keys.REMEMBER, r);
    }


    /**
     * Get preference that is of type String
     * @param key
     * @return stored value of this preference
     */
    public String getString(Keys key)
    {
        return this.sp.getString(key.get(), null);
    }

    /**
     * Get preference that is of type String
     * @param key
     * @return stored value of this preference
     */
    public int getInt(Keys key)
    {
        return this.sp.getInt(key.get(), 0);
    }

    /**
     * Get preference that is of type String
     * @param key
     * @return stored value of this preference
     */
    public boolean getBool(Keys key)
    {
        return this.sp.getBoolean(key.get(), false);
    }



    public String getUsername() { return this.sp.getString(Keys.USERNAME.get(), null); }
    public String getFullname() { return this.sp.getString(Keys.FULLNAME.get(), null); }
    public String getToken()    { return this.sp.getString(Keys.TOKEN.get(), null); }
    public String getRememberMe()    { return this.sp.getString(Keys.REMEMBER.get(), null); }


}