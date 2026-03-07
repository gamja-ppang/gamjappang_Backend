package demago.gamjappang.user.domain.model;

import demago.gamjappang.global.error.GlobalErrorCode;
import demago.gamjappang.global.error.exception.GamjaException;

public class User {

    private final Long id;
    private final String username;
    private final String email;
    private final String password;
    private final Role role;
    private boolean verified;
    private boolean blocked;

    private User(Long id, String username, String email, String password, Role role, boolean verified, boolean blocked) {
        validateUsername(username);
        validateEmail(email);
        validatePassword(password);
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role == null ? Role.USER : role;
        this.verified = verified;
        this.blocked = blocked;
    }

    public static User create(String username, String email, String encodedPassword) {
        return new User(null, username, email, encodedPassword, Role.USER, false, false);
    }

    public static User restore(Long id, String username, String email, String password, Role role, boolean verified, boolean blocked) {
        return new User(id, username, email, password, role, verified, blocked);
    }

    public void verify() {
        this.verified = true;
    }

    public void block() {
        this.blocked = true;
    }

    public void unblock() {
        this.blocked = false;
    }

    private static void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new GamjaException(GlobalErrorCode.INVALID_REQUEST);
        }
    }

    private static void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new GamjaException(GlobalErrorCode.INVALID_REQUEST);
        }
    }

    private static void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new GamjaException(GlobalErrorCode.INVALID_REQUEST);
        }
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public boolean isVerified() {
        return verified;
    }

    public boolean isBlocked() {
        return blocked;
    }
}
