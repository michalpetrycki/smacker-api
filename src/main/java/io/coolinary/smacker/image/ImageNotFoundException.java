package io.coolinary.smacker.image;

public class ImageNotFoundException extends RuntimeException {
    ImageNotFoundException(Long id) {
        super("Could not find image: " + id);
    }
}
