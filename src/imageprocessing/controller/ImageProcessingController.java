package imageprocessing.controller;

import imageprocessing.model.ImageProcessingModel;
import imageprocessing.view.ImageProcessingView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Controller for user-input/script file commands
 */
public class ImageProcessingController {
  private ImageProcessingModel model;
  private ImageProcessingView view;

  /**
   * Takes in model and view to communicate with
   * @param model
   * @param view
   */
  public ImageProcessingController(ImageProcessingModel model, ImageProcessingView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Interacts with user to communicate with model and view
   * @param scanner scanner to get user input from
   * @throws IOException if loading/saving cannot be done
   */
  public void menu(Scanner scanner) throws IOException {
    boolean exit = false;

    while (!exit) {
      System.out.println("Hello! Welcome to the image processing editor. If you'd like to see your"
          + " editing options, type options. Otherwise, type your desired command.");
      String input = scanner.next();
      if (input.equals("file")) {
        Scanner sc;
        try {
          String filename = scanner.next();
          sc = new Scanner(new FileInputStream(filename));
          input = sc.next();
          scanner = sc;
        } catch (FileNotFoundException e) {
          System.out.println("File not found! Type your desired command.");
          input = scanner.next();
        }
      }
      switch (input) {
        case "options":
          userOptions();
          break;
        case "load":
          String pathLoad = scanner.next();
          String nameLoad = scanner.next();
          if (pathLoad.substring(pathLoad.length() - 4).equals(".ppm")) {
            this.model.loadImage(pathLoad, nameLoad);
          } else {
            this.model.imageToArray(pathLoad, nameLoad);
          }
          break;
        case "save":
          String pathSave = scanner.next();
          String nameSave = scanner.next();
          if (!this.model.existingID(nameSave)) {
            System.out.println("name ID doesn't exist in system");
          } else {
            this.model.saveImage(pathSave, nameSave);
          }
          break;
        case "red-component":
          String nameRed = scanner.next();
          String destNameR = scanner.next();
          if (!this.model.existingID(nameRed)) {
            System.out.println("name ID doesn't exist in system");
          } else {
            this.model.rgb("red", nameRed, destNameR);
          }
          break;
        case "blue-component":
          String nameBlue = scanner.next();
          String destNameB = scanner.next();
          if (!this.model.existingID(nameBlue)) {
            System.out.println("name ID doesn't exist in system");
          } else {
            this.model.rgb("blue", nameBlue, destNameB);
          }
          break;
        case "green-component":
          String nameGreen = scanner.next();
          String destNameG = scanner.next();
          if (!this.model.existingID(nameGreen)) {
            System.out.println("name ID doesn't exist in system");
          } else {
            this.model.rgb("green", nameGreen, destNameG);
          }
          break;
        case "value-component":
          String nameValue = scanner.next();
          String destNameV = scanner.next();
          if (!this.model.existingID(nameValue)) {
            System.out.println("name ID doesn't exist in system");
          } else {
            this.model.value(nameValue, destNameV);
          }
          break;
        case "luma-component":
        case "greyscale":
          String nameLuma = scanner.next();
          String destNameL = scanner.next();
          if (!this.model.existingID(nameLuma)) {
            System.out.println("name ID doesn't exist in system");
          } else {
            double[][] matrix = new double[3][3];
            for (int i = 0; i < 3; i++) {
              matrix[i][0] = 0.2126;
              matrix[i][1] = 0.7152;
              matrix[i][2] = 0.0722;
            }
            this.model.colorTransform(matrix, nameLuma, destNameL);
          }
          break;
        case "sepia":
          String nameSepia = scanner.next();
          String destNameS = scanner.next();
          if (!this.model.existingID(nameSepia)) {
            System.out.println("name ID doesn't exist in system");
          } else {
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
            this.model.colorTransform(matrix, nameSepia, destNameS);
          }
          break;
        case "intensity-component":
          String nameIntensity = scanner.next();
          String destNameI = scanner.next();
          if (!this.model.existingID(nameIntensity)) {
            System.out.println("name ID doesn't exist in system");
          } else {
            this.model.intensity(nameIntensity, destNameI);
          }
          break;
        case "brighten":
          int increment = scanner.nextInt();
          String nameBrighten = scanner.next();
          String destNameBright = scanner.next();
          if (!this.model.existingID(nameBrighten)) {
            System.out.println("name ID doesn't exist in system");
          } else {
            this.model.brighten(increment, nameBrighten, destNameBright);
          }
          break;
        case "vertical-flip":
          String nameVFlip = scanner.next();
          String destNameVFlip = scanner.next();
          if (!this.model.existingID(nameVFlip)) {
            System.out.println("name ID doesn't exist in system");
          } else {
            this.model.flip(false, nameVFlip, destNameVFlip);
          }
          break;
        case "horizontal-flip":
          String nameHFlip = scanner.next();
          String destNameHFlip = scanner.next();
          if (!this.model.existingID(nameHFlip)) {
            System.out.println("name ID doesn't exist in system");
          } else {
            this.model.flip(true, nameHFlip, destNameHFlip);
          }
          break;
        case "blur":
          String nameBlur = scanner.next();
          String destNameBlur = scanner.next();
          if (!this.model.existingID(nameBlur)) {
            System.out.println("name ID doesn't exist in system");
          } else {
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
            this.model.filter(matrix, nameBlur, destNameBlur);
          }
          break;
        case "sharpen":
          String nameSharpen = scanner.next();
          String destNameSharpen = scanner.next();
          if (!this.model.existingID(nameSharpen)) {
            System.out.println("name ID doesn't exist in system");
          } else {
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
            this.model.filter(matrix, nameSharpen, destNameSharpen);
          }
          break;
        case "exit":
          System.out.println(this.view.view("hello"));
          exit = true;
          break;
        default:
          System.out.println("That is not a valid option.");
      }
    }
  }

  /**
   * Prints options available to user
   */
  private void userOptions() {
    System.out.println("Here are your command options.");
    System.out.println("load image-path image-name (load an image from the specified path and refer to it "
        + "henceforth in the program by the given destination name)");
    System.out.println("In the following commands, dest-image-name refers to the name "
        + "which the newly created image will be referred to\nas in the program"
            + "and image-name refers to the name of the image to do the command on.");
    System.out.println("save image-path image-name (save an image to the specified path "
        + "which should include the name of the file)");
    System.out.println("horizontal-flip image-name dest-image-name (flip an image horizontally)");
    System.out.println("vertical-flip image-name dest-image-name (flip an image vertically)");
    System.out.println("red-component image-name dest-image-name (create a greyscale image with the red-component of an image)");
    System.out.println("blue-component image-name dest-image-name (create a greyscale image with the blue-component of an image)");
    System.out.println("green-component image-name dest-image-name (create a greyscale image with the green-component of an image)");
    System.out.println("value-component image-name dest-image-name (create a greyscale image with the value-component of an image)");
    System.out.println("luma-component image-name dest-image-name (create a greyscale image with the luma-component of an image)");
    System.out.println("greyscale image-name dest-image-name (create a greyscale image with the luma-component of an image)");
    System.out.println("sepia image-name dest-image-name (create a sepia-toned image from an image)");
    System.out.println("intensity-component image-name dest-image-name (create a greyscale image with the intensity-component of an image)");
    System.out.println("brighten increment image-name dest-image-name (brighten an image by the given increment;"
        + "the increment may be positive (brightening) or negative (darkening))");
    System.out.println("blur image-name dest-image-name (blur an image with Gaussian blur");
    System.out.println("sharpen image-name dest-image-name (sharpen an image");
    System.out.println("file script-name.txt (run a script file and exit program afterwards)");
    System.out.println("exit (exit the system)");
  }
}