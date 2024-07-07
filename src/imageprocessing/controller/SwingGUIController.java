package imageprocessing.controller;

import imageprocessing.model.ImageProcessingModel;
import imageprocessing.view.ImageProcessingGUIView;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Controller for Swing GUI View representation of Image Processing editor
 */
public class SwingGUIController implements Features {

  private final ImageProcessingModel model;
  private ImageProcessingGUIView view;

  /**
   * Takes in model to communicate with
   * @param model
   */
  public SwingGUIController(ImageProcessingModel model) {
    this.model = model;
  }

  @Override
  public void setView(ImageProcessingGUIView view) {
    this.view = view;
    view.setFeatures(this);
  }

  @Override
  public void flipImage(boolean horizontal, String nameID) {
    String destNameID = nameID + "Flip" + (horizontal ? "H" : "V");
    this.model.flip(horizontal, nameID, destNameID);
    this.view.setImageDisplay(this.model.getImage(destNameID));
    this.view.setWorkingNameID(destNameID);
    this.view.setHistogram(this.model.getImage(destNameID));
  }

  @Override
  public void brighten(int increment, String nameID) {
    String destNameID = nameID + (increment < 0 ? "Dark" : "Bright");
    this.model.brighten(increment, nameID, destNameID);
    this.view.setImageDisplay(this.model.getImage(destNameID));
    this.view.setWorkingNameID(destNameID);
    this.view.setHistogram(this.model.getImage(destNameID));
  }

  @Override
  public void componentVisual(String component, String nameID) {
    String destNameID = nameID + component.toUpperCase();
    switch (component) {
      case "red":
      case "green":
      case "blue":
        this.model.rgb(component, nameID, destNameID);
        break;
      case "intensity":
        this.model.intensity(nameID, destNameID);
        break;
      case "value":
        this.model.value(nameID, destNameID);
        break;
      case "luma":
        double[][] matrix = new double[3][3];
        for (int i = 0; i < 3; i++) {
          matrix[i][0] = 0.2126;
          matrix[i][1] = 0.7152;
          matrix[i][2] = 0.0722;
        }
        this.model.colorTransform(matrix, nameID, destNameID);
        break;
    }
    this.view.setImageDisplay(this.model.getImage(destNameID));
    this.view.setWorkingNameID(destNameID);
    this.view.setHistogram(this.model.getImage(destNameID));
  }

  @Override
  public void greyscale(String nameID) {
    double[][] matrix = new double[3][3];
    for (int i = 0; i < 3; i++) {
      matrix[i][0] = 0.2126;
      matrix[i][1] = 0.7152;
      matrix[i][2] = 0.0722;
    }
    this.model.colorTransform(matrix, nameID, nameID+"Greyscale");
    this.view.setImageDisplay(this.model.getImage(nameID+"Greyscale"));
    this.view.setWorkingNameID(nameID+"Greyscale");
    this.view.setHistogram(this.model.getImage(nameID+"Greyscale"));
  }

  @Override
  public void blur(String nameID) {
    double[][] matrix = new double[3][3];
    matrix[0][0] = .0625;
    matrix[0][1] = 0.125;
    matrix[0][2] = .0625;
    matrix[1][0] = 0.125;
    matrix[1][1] = .25;
    matrix[1][2] = 0.125;
    matrix[2][0] = .0625;
    matrix[2][1] = 0.125;
    matrix[2][2] = .0625;
    this.model.filter(matrix, nameID, nameID+"Blur");
    this.view.setImageDisplay(this.model.getImage(nameID+"Blur"));
    this.view.setWorkingNameID(nameID+"Blur");
    this.view.setHistogram(this.model.getImage(nameID+"Blur"));
  }

  @Override
  public void sharpen(String nameID) {
    double[][] matrix = new double[5][5];
    matrix[0][0] = -0.125;
    matrix[0][1] = -0.125;
    matrix[0][2] = -0.125;
    matrix[0][3] = -0.125;
    matrix[0][4] = -0.125;
    matrix[1][0] = -0.125;
    matrix[1][1] = .25;
    matrix[1][2] = .25;
    matrix[1][3] = .25;
    matrix[1][4] = -0.125;
    matrix[2][0] = -0.125;
    matrix[2][1] = .25;
    matrix[2][2] = 1;
    matrix[2][3] = .25;
    matrix[2][4] = -0.125;
    matrix[3][0] = -0.125;
    matrix[3][1] = .25;
    matrix[3][2] = .25;
    matrix[3][3] = .25;
    matrix[3][4] = -0.125;
    matrix[4][0] = -0.125;
    matrix[4][1] = -0.125;
    matrix[4][2] = -0.125;
    matrix[4][3] = -0.125;
    matrix[4][4] = -0.125;
    this.model.filter(matrix, nameID, nameID+"Sharpen");
    this.view.setImageDisplay(this.model.getImage(nameID+"Sharpen"));
    this.view.setWorkingNameID(nameID+"Sharpen");
    this.view.setHistogram(this.model.getImage(nameID+"Sharpen"));
  }

  @Override
  public void sepia(String nameID) {
    double[][] matrix = new double[3][3];
    matrix[0][0] = 0.393;
    matrix[0][1] = 0.769;
    matrix[0][2] = 0.189;
    matrix[1][0] = 0.349;
    matrix[1][1] = 0.686;
    matrix[1][2] = 0.168;
    matrix[2][0] = 0.272;
    matrix[2][1] = 0.534;
    matrix[2][2] = 0.131;
    this.model.colorTransform(matrix, nameID, nameID+"Sepia");
    this.view.setImageDisplay(this.model.getImage(nameID+"Sepia"));
    view.setWorkingNameID(nameID+"Sepia");
    this.view.setHistogram(this.model.getImage(nameID+"Sepia"));
  }

  @Override
  public void uploadImage() throws IOException {
    final JFileChooser fchooser = new JFileChooser("/res");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "JPG, PNG, PPM, and BMP images", "jpg", "png", "ppm", "bmp");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(null);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      String path = f.getAbsolutePath();
      String nameID = path.substring(path.lastIndexOf("/")+1, path.lastIndexOf("."));
      String type = path.substring(path.lastIndexOf(".")+1);
      view.setOpenFile(path, nameID);
      view.setWorkingNameID(nameID);
      switch (type.toLowerCase()) {
        case "ppm":
          this.model.loadImage(path, nameID);
          break;
        default:
          this.model.imageToArray(path, nameID);
          break;
      }
      view.setImageDisplay(this.model.getImage(nameID));
      view.setMessage("Any alerts will show up here");
      view.setHistogram(this.model.getImage(nameID));
      view.setWorkingNameID(nameID);
    }
  }

  @Override
  public void saveImage(String nameID) throws IOException {
      final JFileChooser fchooser = new JFileChooser();
      int retvalue = fchooser.showSaveDialog(null);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        String path = f.getAbsolutePath();
        try {
          this.model.saveImage(path, nameID);
          this.view.setSaveFile(path, nameID);
        } catch (NullPointerException e) {
          throw new IllegalArgumentException("no images loaded");
        }

      }
  }

  @Override
  public void reset(String nameID) {
    this.view.setImageDisplay(this.model.getImage(nameID));
    this.view.setWorkingNameID(nameID);
    this.view.setHistogram(this.model.getImage(nameID));
  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }
}