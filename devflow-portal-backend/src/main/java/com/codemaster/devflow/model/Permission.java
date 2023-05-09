package com.codemaster.devflow.model;

import com.codemaster.devflow.model.generator.StringPrefixedSequenceIdGenerator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "app_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = -2969496952081727421L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perm_seq")
    @Column(name = "permission_id", length = 30)
    @GenericGenerator(
            name = "perm_seq",
            strategy = "com.codemaster.devflow.model.generator.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INITIAL_PARAM, value = "10000"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "perid"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d") })
    private String permissionId;

    @NotNull(message = "Permission name cannot be null")
    @Size(min=2, max=50, message="Permission name must be between 2 and 50 characters")
    @Column(name="permission_name",nullable = false, length = 50)
    private String permissionName;

    @Size(max = 255, message = "Description length cannot exceed 255 characters")
    @Column(name="description", length = 255)
    private String description;

    @NotNull(message = "Entity name cannot be null")
    @Size(min=2, max=50, message="Entity name must be between 2 and 50 characters")
    @Column(name="entity_name",nullable = false, length = 50)
    private String entityName;

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
