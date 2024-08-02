package io.coolinary.smacker.shared;

import java.time.Instant;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public class UpdatableEntity extends BaseEntity {

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    public Instant updateTimestamp;

}
