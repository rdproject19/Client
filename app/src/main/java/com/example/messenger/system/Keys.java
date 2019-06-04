package com.example.messenger.system;

/**
 * Auxiliary enum for consistent keys.
 */
public enum Keys
{
    USERNAME("Username", String.class, false),
    FULLNAME("Fullname", String.class, true),
    TOKEN("Token", int.class, false),
    COUNTER("TokenCount", long.class, true),
    SEED("TokenSeed", String.class, false),
    REMEMBER("Remember", boolean.class, false);


    String key;
    Class<?> type;
    boolean hasConstraints;

    Keys(String key, Class<?> type, boolean hasConstraints)
    {
        this.key = key;
        this.type = type;
        this.hasConstraints = hasConstraints;
    }


    public String getName()
    {
        return this.key;
    }

    public Class<?> getType() { return this.type; }

    public boolean hasConstraints() {
        return this.hasConstraints;
    }

}