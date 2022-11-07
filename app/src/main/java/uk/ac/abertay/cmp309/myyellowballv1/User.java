package uk.ac.abertay.cmp309.myyellowballv1;

public class User {
    private String lastName, firstName, email;
    private int clubID;

    public User() {
    }

    public User(String lastName, String firstName, String email, int clubID) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.clubID = clubID;
    }

    public String getLastName() {
        return lastName;
    }

    public int getClubID() {
        return clubID;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


