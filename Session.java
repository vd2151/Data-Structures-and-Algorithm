package project3;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * This is a representation of a user session with details on login and logout records.
 * Provides information about session duration and other related attributes.
 * author Vedant Desai.
 */
public class Session implements Comparable<Session> {

    private final Record login;  // Record corresponding to login event
    private final Record logout; // Record corresponding to logout event

    /**
     * Constructs a Session object using provided login and logout records.
     *
     * @param login  The login record.
     * @param logout The logout record.
     * @throws IllegalArgumentException If the login or logout records are invalid.
     */
    public Session(Record login, Record logout) {
        if (login == null) {
            throw new IllegalArgumentException("Login record cannot be null.");
        }
        if (logout != null && 
            (!login.getUsername().equals(logout.getUsername()) || 
             login.getTerminal() != logout.getTerminal() || 
             !login.isLogin() || 
             !logout.isLogout() || 
             login.getTime().compareTo(logout.getTime()) > 0)) {
            throw new IllegalArgumentException("Invalid login and logout records.");
        }

        this.login = login;
        this.logout = logout;
    }

    /**
     * @return The terminal ID where the session occurred.
     */
    public int getTerminal() {
        return login.getTerminal();
    }

    /**
     * @return The date and time of login.
     */
    public Date getLoginTime() {
        return login.getTime();
    }

    /**
     * @return The date and time of logout, or null if the session is still active.
     */
    public Date getLogoutTime() {
        return logout != null ? logout.getTime() : null;
    }

    /**
     * @return The username associated with this session.
     */
    public String getUsername() {
        return login.getUsername();
    }

    /**
     * @return The duration of the session in milliseconds, or -1 if the session is still active.
     */
    public long getDuration() {
        if (logout == null) {
            return -1;
        }
        return logout.getTime().getTime() - login.getTime().getTime();
    }

    /**
     * Provides a string representation of the session including the username, terminal, duration, and login/logout times.
     *
     * @return A descriptive string about the session.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        long duration = getDuration();

        sb.append(getUsername()).append(", terminal ").append(getTerminal()).append(", duration ");

        if (duration == -1) {
            sb.append("active session");
        } else {
            long days = TimeUnit.MILLISECONDS.toDays(duration);
            long hours = TimeUnit.MILLISECONDS.toHours(duration) - TimeUnit.DAYS.toHours(days);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) - 
                           TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
            long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - 
                           TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
            sb.append(days).append(" days, ").append(hours).append(" hours, ").append(minutes).append(" minutes, ").append(seconds).append(" seconds");
        }

        sb.append("\n  logged in: ").append(getLoginTime().toString());

        if (logout == null) {
            sb.append("\n  logged out: still logged in");
        } else {
            sb.append("\n  logged out: ").append(getLogoutTime().toString());
        }

        return sb.toString();
    }

    /**
     * Compares this session to another based on login times.
     *
     * @param other The session to compare against.
     * @return An integer representing the order of sessions.
     */
    @Override
    public int compareTo(Session other) {
        return this.login.compareTo(other.login);
    }

    /**
     * Checks if this session is equal to another object.
     *
     * @param obj The object to compare against.
     * @return True if they are equal, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Session otherSession = (Session) obj;
        return login.equals(otherSession.login) && 
               (logout == null ? otherSession.logout == null : logout.equals(otherSession.logout));
    }

    
}
