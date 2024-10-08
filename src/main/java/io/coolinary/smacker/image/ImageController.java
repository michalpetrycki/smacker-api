package io.coolinary.smacker.image;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;
import io.coolinary.smacker.shared.Routes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    private final ImageService imageService;
    private final Logger logger = LoggerFactory.getLogger(ImageController.class);

    ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(Routes.IMAGES)
    ResponseEntity<List<ImageEntity>> getAll() {
        List<ImageEntity> images = this.imageService.getAll();
        return new ResponseEntity<List<ImageEntity>>(images.stream().collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(Routes.IMAGES + Routes.ID)
    public ResponseEntity<ImageEntity> getImage(@PathVariable("id") Long id) {
        return new ResponseEntity<ImageEntity>(this.imageService.getById(id), HttpStatus.OK);
    }

    @PostMapping(Routes.IMAGES)
    public ResponseEntity<ImageEntity> newImage(@RequestBody ImageEntity newImage) {

        try {
            return new ResponseEntity<ImageEntity>(this.imageService.createImage(newImage), HttpStatus.CREATED);
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(Routes.IMAGES + Routes.ID)
    public ResponseEntity<ImageEntity> replaceImage(@RequestBody ImageEntity newImage, @PathVariable("id") Long id) {

        ImageEntity imageToUpdate = this.imageService.getById(id);
        if (imageToUpdate == null) {
            return new ResponseEntity<ImageEntity>(HttpStatus.NOT_FOUND);
        }

        imageToUpdate.setImageName(newImage.getImageName());
        return new ResponseEntity<ImageEntity>(this.imageService.updateImage(id, imageToUpdate), HttpStatus.OK);
    }

    @DeleteMapping(Routes.IMAGES + Routes.ID)
    public ResponseEntity<Boolean> deleteImage(@PathVariable("id") Long id) {
        return new ResponseEntity<Boolean>(this.imageService.deleteImage(id), HttpStatus.OK);
    }

    @DeleteMapping(Routes.IMAGES)
    public ResponseEntity<Boolean> deleteAllImages() {
        return new ResponseEntity<Boolean>(this.imageService.deleteAllImages(), HttpStatus.OK);
    }

}
