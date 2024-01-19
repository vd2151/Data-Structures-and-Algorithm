package project3;

import java.util.NoSuchElementException;

/**
 * This is a representation of a list of records (login/logout) and provides methods to retrieve 
 * sessions for specific users.
 * @author Vedant Desai
 */
public class RecordList extends SortedLinkedList<Record> {

    /**
     * This retrieves the first session for the specified user.
     *
     * @param user The user whose session is to be fetched.
     * @return The first session of the user.
     * @throws NoSuchElementException if no session found for the user.
     */
    public Session getFirstSession(String user) {
        validateUserArgument(user);

        Session firstSession = null;
        Node current = getHead();
        while (current != null) {
            Record loginRecord = current.data;
            if (loginRecord.getUsername().equals(user) && loginRecord.isLogin()) {
                Record logoutRecord = findMatchingLogoutRecord(current, user);
                if (logoutRecord != null) {
                    firstSession = new Session(loginRecord, logoutRecord);
                    break;
                }
            }
            current = current.next;
        }

        if (firstSession == null) {
            throw new NoSuchElementException("No matching record for user: " + user);
        }
        return firstSession;
    }

    /**
     * This retrieves the last session for the specified user.
     *
     * @param user The user whose session is to be fetched.
     * @return The last session of the user.
     * @throws NoSuchElementException if no session found for the user.
     */
    public Session getLastSession(String user) {
        validateUserArgument(user);

        Session lastSession = null;
        Node current = getHead();
        Record lastLoginRecord = null;
        Record lastLogoutRecord = null;
        while (current != null) {
            Record record = current.data;
            if (record.getUsername().equals(user) && record.isLogin()) {
                lastLoginRecord = record;
                lastLogoutRecord = findMatchingLogoutRecord(current, user);
            }
            current = current.next;
        }

        if (lastLoginRecord != null) {
            lastSession = new Session(lastLoginRecord, lastLogoutRecord);
        } else {
            throw new NoSuchElementException("No matching record for user: " + user);
        }

        return lastSession;
    }

    /**
     * This Calculates the total time the specified user was logged in.
     *
     * @param user The user for whom the total login time is calculated.
     * @return The total login time of the user in milliseconds.
     */
    public long getTotalTime(String user) {
        validateUserArgument(user);

        long totalTime = 0;
        Node current = getHead();
        while (current != null) {
            Record loginRecord = current.data;
            if (loginRecord.getUsername().equals(user) && loginRecord.isLogin()) {
                Record logoutRecord = findMatchingLogoutRecord(current, user);
                if (logoutRecord != null) {
                    totalTime += logoutRecord.getTime().getTime() - loginRecord.getTime().getTime();
                }
            }
            current = current.next;
        }
        
        return totalTime;
    }

    /**
     * This retrieves all sessions of the specified user.
     *
     * @param user The user whose sessions are to be fetched.
     * @return A list of all sessions of the user.
     * @throws NoSuchElementException if no records found for the user.
     */
    public SortedLinkedList<Session> getAllSessions(String user) {
        validateUserArgument(user);

        SortedLinkedList<Session> allSessions = new SortedLinkedList<>();
        Node current = getHead();
        boolean userFound = false;

        while (current != null) {
            Record loginRecord = current.data;
            if (loginRecord.getUsername().equals(user)) {
                userFound = true;
                if (loginRecord.isLogin()) {
                    Record logoutRecord = findMatchingLogoutRecord(current, user);
                    if (logoutRecord != null) {
                        allSessions.add(new Session(loginRecord, logoutRecord));
                    } else {
                        allSessions.add(new Session(loginRecord, null));
                    }
                }
            }
            current = current.next;
        }

        if (!userFound) {
            throw new NoSuchElementException("No records found for user: " + user);
        }

        return allSessions;
    }

    /**
     * Searches for a matching logout record for the specified login record.
     *
     * @param startNode The node containing the login record.
     * @param user The user for whom the matching logout record is to be fetched.
     * @return The matching logout record, or null if not found.
     */
    Record findMatchingLogoutRecord(Node startNode, String user) {
        Node current = startNode.next;
        while (current != null) {
            Record record = current.data;
            if (record.getUsername().equals(user) && record.isLogout() && record.getTerminal() == startNode.data.getTerminal()) {
                return record;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Validates the user argument to ensure it's not null or empty.
     *
     * @param user The user argument to validate.
     * @throws IllegalArgumentException if user is null or empty.
     */
    private void validateUserArgument(String user) {
        if (user == null || user.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid user argument.");
        }
    }

    /**
     * Accesses the head node of the list.
     *
     * @return The head node of the list.
     */
    protected Node getHead() {
        return this.head;
    }
}
