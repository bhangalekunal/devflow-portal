package com.codemaster.devflow.model;

import com.codemaster.devflow.model.generator.StringPrefixedSequenceIdGenerator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "app_system_config")
public class SystemConfig implements Serializable {

    private static final long serialVersionUID = 4799778023108111440L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_config_seq")
    @Column(name = "config_id", length = 30)
    @GenericGenerator(
            name = "system_config_seq",
            strategy = "com.codemaster.devflow.model.generator.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INITIAL_PARAM, value = "10000"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "cfgId"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d") })
    private String configId;

    @NotNull(message = "Config Name cannot be null")
    @Size(min=2, max=50, message="Config name must be between 2 and 50 characters")
    @Column(name = "config_name", nullable = false, length = 50)
    private String configName;

    @NotNull(message = "Config Key cannot be null")
    @Size(min=2, max=50, message="Config key must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$")
    @Column(name = "config_key", nullable = false, unique = true, length = 50)
    private String configKey;

    @Column(name = "config_value", length = 2000)
    private String configValue;

    @NotNull(message = "Config Type cannot be null")
    @Size(min=2, max=50, message="Config type must be between 2 and 50 characters")
    @Column(name = "config_type", nullable = false, length = 50)
    private String configType;

    @Column(name = "config_description", length = 500)
    private String configDescription;

    @NotNull(message = "Is Encrypted cannot be null")
    @JsonProperty("isEncrypted")
    @Column(name = "is_encrypted", nullable = false, columnDefinition = "NUMBER(1,0) DEFAULT 0")
    private boolean isEncrypted;

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
