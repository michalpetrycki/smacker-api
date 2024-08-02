package io.coolinary.smacker.image;

import java.time.Instant;
import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Image {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    @Column(name = "image_name")
    private String imageName;
    @Column(name = "image_url")
    private String url;
    @Column(name = "creation_timestamp")
    private Instant creationTimestamp;
    @Column(name = "image_data", length = 1000)
    private byte[] data;

    Image() {
    }

    Image(String imageName, String url, Instant creationTimestamp) {
        this.imageName = imageName;
        this.url = url;
        this.creationTimestamp = creationTimestamp;
    }

    Image(String imageName, Instant creationTimestamp, byte[] data) {
        this.imageName = imageName;
        this.creationTimestamp = creationTimestamp;
        this.data = data;
    }

    Image(String imageName, String url, Instant creationTimestamp, byte[] data) {
        this.imageName = imageName;
        this.url = url;
        this.creationTimestamp = creationTimestamp;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Instant creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

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
        Image other = (Image) obj;
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
