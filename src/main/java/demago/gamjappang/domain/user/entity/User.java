package demago.gamjappang.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role = Role.USER;

    private boolean isVerified;

    private boolean isBlocked;

    public static User of(
            String username,
            String email,
            String encodedPassword
    ) {
        return User.builder()
                .username(username)
                .email(email)
                .password(encodedPassword)
                .role(Role.USER)
                .isVerified(false)
                .isBlocked(false)
                .build();
    }

    public void verify() {
        this.isVerified = true;
    }

    public void block() {
        this.isBlocked = true;
    }
}
