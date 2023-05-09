package com.codemaster.devflow.model;

import com.codemaster.devflow.model.generator.StringPrefixedSequenceIdGenerator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "app_user")
public class User implements Serializable {
    private static final long serialVersionUID = -4330776843465387565L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "user_id", length = 30)
    @GenericGenerator(
            name = "user_seq",
            strategy = "com.codemaster.devflow.model.generator.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INITIAL_PARAM, value = "10000"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "usrid"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d") })
    private String userId;


    @NotNull(message = "First name cannot be null")
    @Size(min = 2, max = 50, message = "First name should be between 2 and 50 characters")
    @Column(name="first_name",nullable = false,length = 50)
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, max = 50, message = "Last name should be between 2 and 50 characters")
    @Column(name="last_name",nullable = false,length = 50)
    private String lastName;

    @NotNull(message = "Username cannot be null")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username must only contain letters, numbers, and underscores")
    @Column(name="user_name",nullable = false, unique = true,length = 20)
    private String userName;

    @NotNull(message = "Email cannot be null")
    @Size(min = 15, max = 50, message = "Email must be between 15 and 50 characters")
    @Email(message = "Invalid email address")
    @Column(name="email",nullable = false,unique = true,length = 50)
    private String email;

    @NotNull(message = "Password cannot be null")
    @Column(name="password", nullable = false)
    private String password;

    @NotNull(message = "Is Active cannot be null")
    @JsonProperty("isActive")
    @Column(name="is_active", nullable = false, columnDefinition = "NUMBER(1,0) DEFAULT 0")
    private boolean isActive;

    @NotNull(message = "Is ResetPassword cannot be null")
    @JsonProperty("isResetPassword")
    @Column(name="is_reset_password", nullable = false, columnDefinition = "NUMBER(1,0) DEFAULT 0")
    private boolean isResetPassword;

    @NotNull(message = "Created At cannot be null")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull(message = "Updated At cannot be null")
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PastOrPresent(message = "Last login must be in the past or present")
    @Column(name="last_login")
    private LocalDateTime lastLogin;

    @NotNull(message = "Last password change date cannot be null")
    @PastOrPresent(message = "Last password change date must be in the past or present")
    @Column(name="last_password_change")
    private LocalDateTime lastPasswordChange;

    @NotNull(message = "Expiration date cannot be null")
    @Future(message = "Expiration date must be in the future")
    @Column(name="expiration_date")
    private LocalDateTime expirationDate;

    @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinTable(name="user_role_mapping",
            joinColumns = {@JoinColumn(name="user_id", referencedColumnName="user_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="role_id")}
    )
    private Set<Role> roles = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
