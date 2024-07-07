package imageprocessing.model;

import java.awt.*;

/**
 * State of an Image Processing editor model
 */
public interface ImageProcessingModelState {

  /**
   * Gets the number of images in the model
   * @return number of images in the model
   */
  int getSize();

  /**
   * Gets the height of the image with the given name ID
   * @param nameID name ID of image in model
   * @return height of image with given name ID
   */
  int getHeight(String nameID);

  /**
   * Gets the width of the image with the given name ID
   * @param nameID name ID of image in model
   * @return width of image with given name ID
   */
  int getWidth(String nameID);

  /**
   * Gets the Color of the pixel at (row, col) in the image with the given name ID
   * @param nameID name ID of image in model
   * @return Color at pixel (row, col) in image with nameID
   */
  Color pixelRGB(int row, int col, String nameID);

  /**
   * Returns if the given name ID exists in the model system
   * @param id to check for
   * @return true if the ID exists, and false otherwise
   */
  boolean existingID(String id);

  /**
   * Returns image in model with given nameID
   * @param nameID name ID of image to get
   * @return Image object
   */
  Image getImage(String nameID);
}