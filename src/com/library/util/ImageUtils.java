package com.library.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.library.exceptions.ImageProcessingException;
import com.library.models.Book;

public class ImageUtils {
    private static final Logger logger = LogManager.getLogger(ImageUtils.class);

    private ImageUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Reads the image bytes from the specified file path.
     *
     * @param imagePath the path of the image file
     * @return the image bytes
     * @throws ImageProcessingException if an error occurs while reading the image
     */
    public static byte[] readImageBytes(String imagePath) throws ImageProcessingException {
        try {
            Path path = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(path);
            return imageBytes;
        } catch (IOException e) {
            logger.error("Error reading image file: {}", e.getMessage());
            throw new ImageProcessingException("Error reading image file", e);
        }
    }

    /**
     * Writes the image bytes to the specified file path.
     *
     * @param imageBytes the image bytes to be written
     * @param imagePath  the path to write the image file
     * @throws ImageProcessingException if an error occurs while writing the image
     */
    public static void writeImageFile(byte[] imageBytes, String imagePath) throws ImageProcessingException {
        try {
            Path path = Paths.get(imagePath);
            Files.write(path, imageBytes);
        } catch (IOException e) {
            logger.error("Error writing image file: {}", e.getMessage());
            throw new ImageProcessingException("Error writing image file", e);
        }
    }

    /**
     * Resizes the image to the specified width and height.
     *
     * @param imageBytes the image bytes to be resized
     * @param width      the desired width of the resized image
     * @param height     the desired height of the resized image
     * @return the resized image bytes
     * @throws ImageProcessingException if an error occurs during the resize operation
     */
    public static byte[] resizeImage(byte[] imageBytes, int width, int height) throws ImageProcessingException {
        try (InputStream inputStream = new ByteArrayInputStream(imageBytes);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(inputStream);
            BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
            Graphics2D graphics = resizedImage.createGraphics();
            graphics.drawImage(image, 0, 0, width, height, null);
            graphics.dispose();
            ImageIO.write(resizedImage, "jpg", outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            logger.error("Error resizing image: {}", e.getMessage());
            throw new ImageProcessingException("Error resizing image", e);
        }
    }

    @Test
    public void testReadWriteImage() {
        try {
            byte[] imageBytes = readImageBytes("path/to/image.jpg");
            writeImageFile(imageBytes, "path/to/output.jpg");
        } catch (ImageProcessingException e) {
            logger.error("Error processing image: {}", e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testResizeImage() {
        try {
            byte[] imageBytes = readImageBytes("path/to/image.jpg");
            byte[] resizedBytes = resizeImage(imageBytes, 200, 200);
            writeImageFile(resizedBytes, "path/to/resized.jpg");
        } catch (ImageProcessingException e) {
            logger.error("Error processing image: {}", e.getMessage());
            Assert.fail(e.getMessage());
        }
    }
}