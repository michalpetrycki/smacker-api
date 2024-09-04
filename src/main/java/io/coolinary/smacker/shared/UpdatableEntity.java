package io.coolinary.smacker.shared;

import java.time.Instant;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
public class UpdatableEntity extends BaseEntity {

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private Instant updateTimestamp;

}
