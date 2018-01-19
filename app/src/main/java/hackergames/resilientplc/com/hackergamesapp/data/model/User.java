package hackergames.resilientplc.com.hackergamesapp.data.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by eduar on 18/01/2018.
 */

public class User {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            MILITARY,
            PROFESSIONAL
    })
    public @interface UserType {}
    public static final String MILITARY = "military";
    public static final String PROFESSIONAL = "professional";

    String firstName;
    String lastName;
    String userId;
    @UserType String type;

    public User(String firstName, String lastName, String militaryId, @UserType String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = militaryId;
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
