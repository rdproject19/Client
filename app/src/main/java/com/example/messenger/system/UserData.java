        package com.example.messenger.system;


        import android.content.Context;
        import android.content.SharedPreferences;



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
        handle.putString(key.getName(), value);
        handle.apply();
    }
    private void insert(Keys key, boolean value)
    {
        SharedPreferences.Editor handle = this.sp.edit();
        handle.putBoolean(key.getName(), value);
        handle.apply();
    }
    private void insert(Keys key, int val)
    {
        SharedPreferences.Editor handle = this.sp.edit();
        handle.putInt(key.getName(), val);
        handle.apply();
    }
    private void insert(Keys key, long val)
    {
        SharedPreferences.Editor handle = this.sp.edit();
        handle.putLong(key.getName(), val);
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
    public boolean setFullname(String fullname) {
        if (fullname.length() > 64 || fullname.length() < 6) {
            return false;
        }
        this.insert(Keys.FULLNAME, fullname);
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
     * Get preference that is of type String
     * @param key
     * @return stored value of this preference
     */
    public String getString(Keys key)
    {
        return this.sp.getString(key.getName(), null);
    }

    /**
     * Get preference that is of type String
     * @param key
     * @return stored value of this preference
     */
    public int getInt(Keys key)
    {
        return this.sp.getInt(key.getName(), 0);
    }

    /**
     * Get preference that is of type String
     * @param key
     * @return stored value of this preference
     */
    public boolean getBool(Keys key)
    {
        return this.sp.getBoolean(key.getName(), false);
    }

    /**
     * Get preference that is of type long
     * @param key
     * @return stored value of this preference
     */
    public long getLong(Keys key) { return this.sp.getLong(key.getName(), 0); }

    public void setString(Keys key, String string) {
        if(!key.hasConstraints()) {
            this.insert(key, string);
        } else {
            throw new UnsupportedOperationException("Key has restrictions.");
        }
    }

    public void setInt(Keys key, int val) {
        if(!key.hasConstraints()) {
            this.insert(key, val);
        } else {
            throw new UnsupportedOperationException("Key has restrictions.");
        }
    }

    public void setLong(Keys key, long val) {
        if(!key.hasConstraints()) {
            this.insert(key, val);
        } else {
            throw new UnsupportedOperationException("Key has restrictions.");
        }
    }

    public void setBoolean(Keys key, boolean bool) {
        if(!key.hasConstraints()) {
            this.insert(key, bool);
        } else {
            throw new UnsupportedOperationException("Key has restrictions.");
        }
    }

}