package demago.gamjappang.user.infrastructure.adapter.out.persistence;

import demago.gamjappang.user.domain.model.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(name = "is_verified", nullable = false)
    private boolean verified;

    @Column(name = "is_blocked", nullable = false)
    private boolean blocked;

    protected UserJpaEntity() {
    }

    public UserJpaEntity(Long id, String username, String email, String password, Role role, boolean verified, boolean blocked) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.verified = verified;
        this.blocked = blocked;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public boolean isVerified() { return verified; }
    public boolean isBlocked() { return blocked; }
}
