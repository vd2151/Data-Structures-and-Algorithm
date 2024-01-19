package project3;

import java.util.Date;

/**
 * This is a representation of a record of a user login or logout event on a specific terminal.
 * Contains details about the terminal, type of event (login/logout), username, and time of the event.
 * author Vedant Desai
 */
public class Record implements Comparable<Record> {
    
    private final int terminal;       // Terminal number where the event occurred
    private final boolean login;      // Indicates whether the event is a login (true) or logout (false)
    private final String username;    // Username associated with the event
    private final Date time;          // Time when the event took place

    /**
     * Constructs a Record object using provided terminal number, event type, username, and event time.
     *
     * @param terminal Terminal number where the event occurred.
     * @param login    True if it's a login event, false for logout.
     * @param username Username associated with the event.
     * @param time     Time of the event.
     * @throws IllegalArgumentException If the terminal number is not a positive integer.
     */
    public Record(int terminal, boolean login, String username, Date time) {
        if (terminal <= 0) {
            throw new IllegalArgumentException("Terminal number must be a positive integer.");
        }
        
        this.terminal = terminal;
        this.login = login;
        this.username = username;
        this.time = new Date(time.getTime());  // Copying to ensure immutability of the Date object
    }

    /**
     * @return The terminal number where the event occurred.
     */
    public int getTerminal() {
        return terminal;
    }

    /**
     * @return True if the event is a login, otherwise false.
     */
    public boolean isLogin() {
        return login;
    }

    /**
     * @return True if the event is a logout, otherwise false.
     */
    public boolean isLogout() {
        return !login;
    }

    /**
     * @return The username associated with this record.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return The time of the event. A new Date object is returned to ensure immutability.
     */
    public Date getTime() {
        return new Date(time.getTime());  // Return a copy to ensure immutability
    }

    /**
     * Compares this record to another based on the event times.
     *
     * @param other The record to compare against.
     * @return An integer representing the order of records.
     */
    @Override
    public int compareTo(Record other) {
        return this.time.compareTo(other.getTime());
    }

    /**
     * Checks if this record is equal to another object.
     *
     * @param obj The object to compare against.
     * @return True if they are equal, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        // Special case for string comparison to the username
        if (obj instanceof String) {
            return this.username.equals(obj);
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Record otherRecord = (Record) obj;
        return terminal == otherRecord.getTerminal() &&
               login == otherRecord.isLogin() &&
               username.equals(otherRecord.getUsername()) &&
               time.equals(otherRecord.getTime());
    }

  
}
