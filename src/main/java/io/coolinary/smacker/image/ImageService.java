package io.coolinary.smacker.image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    List<ImageEntity> getAll() {
        return this.imageRepository.findAll();
    }

    ImageEntity getById(Long id) {
        final Optional<ImageEntity> retrievedImage = this.imageRepository.findById(id);

        ImageEntity.ImageEntityBuilder img = ImageEntity.builder();

        retrievedImage.ifPresent(image -> {
            img.imageName(image.getImageName());
            img.data(ImageService.compressBytes(image.getData()));
        });

        return img.build();

    }

    ImageEntity createImage(ImageEntity image) {
        ImageEntity img = ImageEntity.builder().imageName(image.getImageName())
                .data(ImageService.compressBytes(image.getData())).build();
        return this.imageRepository.save(img);
    }

    ImageEntity updateImage(Long id, ImageEntity image) {
        return this.imageRepository.save(image);
    }

    Boolean deleteImage(Long id) {
        try {
            this.imageRepository.deleteById(id);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }

    }

    Boolean deleteAllImages() {
        this.imageRepository.deleteAll();
        return true;
    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }

}
