package com.codemaster.devflow.model;

import com.codemaster.devflow.model.generator.StringPrefixedSequenceIdGenerator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "app_role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @Column(name = "role_id", length = 30)
    @GenericGenerator(
            name = "role_seq",
            strategy = "com.codemaster.devflow.model.generator.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INITIAL_PARAM, value = "10000"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "rolid"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d") })
    private String roleId;

    @NotNull(message = "Role name cannot be null")
    @Size(min=2, max=50, message="Role name must be between 2 and 50 characters")
    @Column(name="role_name",nullable = false, length = 50)
    private String roleName;

    @Size(max = 255, message = "Description length cannot exceed 255 characters")
    @Column(name="description", length = 255)
    private String description;

    @NotNull(message = "Role type cannot be null")
    @Size(min=2, max=50, message="Role type must be between 2 and 50 characters")
    @Column(name="role_type",nullable = false, length = 50)
    private String roleType;

    @NotNull(message = "Is Active cannot be null")
    @JsonProperty("isActive")
    @Column(name="is_active", nullable = false, columnDefinition = "NUMBER(1,0) DEFAULT 0")
    private boolean isActive;

    @NotNull(message = "Created At cannot be null")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull(message = "Updated At cannot be null")
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinTable(name="role_permission_mapping",
            joinColumns = {@JoinColumn(name="role_id", referencedColumnName="role_id")},
            inverseJoinColumns = {@JoinColumn(name="permission_id", referencedColumnName="permission_id")}
    )
    private Set<Permission> permissions = new HashSet<>();

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
