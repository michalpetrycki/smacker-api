package io.coolinary.smacker.image;

import java.time.Instant;
import java.util.Arrays;

import io.coolinary.smacker.shared.UpdatableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "image", uniqueConstraints = {@UniqueConstraint(columnNames = "image_url"), @UniqueConstraint(columnNames = "public_identifier")})
public class ImageEntity extends UpdatableEntity {

    @Column(name = "image_name")
    private String imageName;
    @Column(name = "image_url")
    private String url;
    @Column(name = "creation_timestamp")
    private Instant creationTimestamp;
    @Lob
    @Column(name = "image_data", length = 1000)
    private byte[] data;
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((imageName == null) ? 0 : imageName.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + ((creationTimestamp == null) ? 0 : creationTimestamp.hashCode());
        result = prime * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ImageEntity other = (ImageEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (imageName == null) {
            if (other.imageName != null)
                return false;
        } else if (!imageName.equals(other.imageName))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        if (creationTimestamp == null) {
            if (other.creationTimestamp != null)
                return false;
        } else if (!creationTimestamp.equals(other.creationTimestamp))
            return false;
        if (!Arrays.equals(data, other.data))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Image [id=" + id + ", imageName=" + imageName + ", url=" + url + ", creationTimestamp="
                + creationTimestamp + ", data=" + Arrays.toString(data) + "]";
    }

}
