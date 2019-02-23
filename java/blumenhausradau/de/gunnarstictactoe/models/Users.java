package blumenhausradau.de.gunnarstictactoe.models;

public class Users {
    private String username1, username2;
    private String userpoints1, userpoints2;

    //constructor, will be automatically created if there is not any one.
    public Users(String username1, String username2, String userpoints1, String userpoints2) {
        this.username1 = username1;
        this.username2 = username2;
        this.userpoints1 = userpoints1;
        this.userpoints2 = userpoints2;
    }
    //empty constructor
    public Users() {
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public String getUserpoints1() {
        return userpoints1;
    }

    public void setUserpoints1(String userpoints1) {
        this.userpoints1 = userpoints1;
    }

    public String getUserpoints2() {
        return userpoints2;
    }

    public void setUserpoints2(String userpoints2) {
        this.userpoints2 = userpoints2;
    }

    @Override
    public String toString() {
        return "Users{" +
                "username1='" + username1 + '\'' +
                ", username2='" + username2 + '\'' +
                ", userpoints1='" + userpoints1 + '\'' +
                ", userpoints2='" + userpoints2 + '\'' +
                '}';
    }
}
