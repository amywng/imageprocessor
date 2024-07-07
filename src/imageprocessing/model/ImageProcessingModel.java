package imageprocessing.model;

import java.io.IOException;

/**
 * Operations able to be done by an Image Processing editor model
 */
public interface ImageProcessingModel extends ImageProcessingModelState{
  /**
   * Load the image with the given path and set it as the given name ID in the model
   * @param filename path of file to load
   * @param nameID nameID to associate with loaded image
   */
  void loadImage(String filename, String nameID);

  /**
   * Computes greyscale of image with given name ID using red, green, or blue
   * component based on given rgb value and saves edited image to image with given destName
   * @param rgb red, green, or blue (specifies component to use)
   * @param nameID name ID of image
   * @param destName name ID to set edited image to
   */
  void rgb(String rgb, String nameID, String destName);

  /**
   * Computes greyscale of image with given name ID using intensity
   * component and saves edited image to image with given destName
   * @param nameID name ID of image
   * @param destName name ID to set edited image to
   */
  void intensity(String nameID, String destName);

  /**
   * Transforms colors of pixels in image with given name ID using
   * given matrix and saves edited image to image with given destName
   * @param matrix color transformation matrix to use
   * @param nameID name ID of image
   * @param destName name ID to set edited image to
   */
  void colorTransform(double[][] matrix, String nameID, String destName);

  /**
   * Filters image with given name ID using given matrix
   * and saves edited image to image with given destName
   * @param matrix kernel of filter operation
   * @param nameID name ID of image
   * @param destName name ID to set edited image to
   */
  void filter(double[][] matrix, String nameID, String destName);

  /**
   * Flips image with given name ID horizontally if boolean is true and
   * vertical if false and saves edited image to image with given destName
   * @param horizontal indicates how to flip image
   * @param nameID name ID of image
   * @param destName name ID to set edited image to
   */
  void flip(boolean horizontal, String nameID, String destName);

  /**
   * Brightens image with given name ID by given increment
   * and saves edited image to image with given destName
   * Negative increments darken the image
   * @param increment increment to brighten by
   * @param nameID name ID of image
   * @param destName name ID to set edited image to
   */
  void brighten(int increment, String nameID, String destName);

  /**
   * Saves image with given name ID to given file path
   * @param filename path to save to
   * @param nameID name ID of image to save
   */
  void saveImage(String filename, String nameID) throws IOException;

  /**
   * Turns image with non ppm file type (at given path) to array of colors and
   * saves the image with given nameID
   * @param filename path of file to load
   * @param nameID name ID of image
   */
  void imageToArray(String filename, String nameID) throws IOException;

  /**
   * Computes greyscale of image with given name ID using value
   * component and saves edited image to image with given destName
   * @param nameID name ID of image
   * @param destName name ID to set edited image to
   */
  void value(String nameID, String destName);
}