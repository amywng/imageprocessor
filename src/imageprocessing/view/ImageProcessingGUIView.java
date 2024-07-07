package imageprocessing.view;

import imageprocessing.controller.Features;
import java.awt.Image;

/**
 * Interface for a view using Swing GUI
 */
public interface ImageProcessingGUIView {

  /**
   * Sets features of the view to given features
   * @param features
   */
  void setFeatures(Features features);

  /**
   * Opens file at given path "filename" to use given nameID
   * @param filename path where file is
   * @param nameID name ID to use
   */
  void setOpenFile(String filename, String nameID);

  /**
   * Saves file with given name ID to given path "filename"
   * @param filename path to save file to
   * @param nameID name ID of image to save
   */
  void setSaveFile(String filename, String nameID);

  /**
   * Sets image display in view to given image
   * @param image
   */
  void setImageDisplay(Image image);

  /**
   * Sets workingNameID indicating which image is being worked on to given name ID
   * @param nameID
   */
  void setWorkingNameID(String nameID);

  /**
   * Sets message in view to given message
   * @param message
   */
  void setMessage(String message);

  /**
   * Sets histogram panel in view to given histogram image
   * @param image
   */
  void setHistogram(Image image);
}