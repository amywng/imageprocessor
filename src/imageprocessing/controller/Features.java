package imageprocessing.controller;

import imageprocessing.view.ImageProcessingGUIView;
import java.io.IOException;

/**
 * Features interface for Swing GUI Controller, specifies functions able to be used
 */
public interface Features {

  /**
   * Flips image with given nameID in model (horizontally if given boolean is true, false otherwise)
   * @param horizontal specifies horizontal or vertical flipping
   * @param nameID nameID of image in model
   */
  void flipImage(boolean horizontal, String nameID);

  /**
   * Brightens image with given nameID in model by given increment
   * @param increment increment to change image by (negative means darken, positive means brighten)
   * @param nameID nameID of image in model
   */
  void brighten(int increment, String nameID);

  /**
   * Computes greyscale using given component of image with given nameID in model
   * @param component component to use (valid options include red, green, blue, luma, intensity, value)
   * @param nameID nameID of image in model
   */
  void componentVisual(String component, String nameID);

  /**
   * Turns image with given nameID in model to greyscale (using luma component)
   * @param nameID nameID of image in model
   */
  void greyscale(String nameID);

  /**
   * Blurs image with given nameID in model
   * @param nameID nameID of image in model
   */
  void blur(String nameID);

  /**
   * Sharpens image with given nameID in model
   * @param nameID nameID of image in model
   */
  void sharpen(String nameID);

  /**
   * Turns image with given nameID in model to sepia tone
   * @param nameID nameID of image in model
   */
  void sepia(String nameID);

  /**
   * Exits the program, shutting down the GUI view
   */
  void exitProgram();

  /**
   * Connects given view to this Features implementation
   * @param view view to connect to
   */
  void setView(ImageProcessingGUIView view);

  /**
   * Uploads selected image to model
   * @throws IOException if image can't be uploaded
   */
  void uploadImage() throws IOException;

  /**
   * Saves image with given nameID in model
   * @param nameID nameID of image in model
   * @throws IOException if image can't be saved
   */
  void saveImage(String nameID) throws IOException;

  /**
   * Resets working image to original (image with given nameID)
   * @param nameID nameID of image in model
   */
  void reset(String nameID);
}