package imageprocessing;

import imageprocessing.controller.SwingGUIController;
import imageprocessing.controller.Features;
import imageprocessing.controller.ImageProcessingController;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.model.ImageProcessingModelImpl;
import imageprocessing.view.ImageProcessingGUIView;
import imageprocessing.view.ImageProcessingSwingGUIView;
import imageprocessing.view.ImageProcessingTextView;
import imageprocessing.view.ImageProcessingView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Driver of Image Processing editor that takes in inputs and
 * sets up model, view, and controller accordingly
 */
public class ImageProcessingDriver {

  /**
   *  * Argument options:
   *  * nothing: graphical user interface
   *  * -text: interactive text mode, one line at a time
   *  * -file path-of-script-file.txt: opens script file, executes it, shuts down
   * @param args arguments (valid ones specified above)
   * @throws IOException if file can't be found in interactive text mode when uploading/saving
   */
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      ImageProcessingModel model = new ImageProcessingModelImpl();
      Features controller = new SwingGUIController(model);
      ImageProcessingGUIView view = new ImageProcessingSwingGUIView();
      controller.setView(view);
    } else if (args.length == 1) {
      if (args[0].equals("-text")) {
        ImageProcessingModel model = new ImageProcessingModelImpl();
        ImageProcessingView view = new ImageProcessingTextView(model);
        ImageProcessingController controller = new ImageProcessingController(model, view);
        controller.menu(new Scanner(System.in));
      }
    } else if (args.length == 2) {
      if (args[0].equals("-file")) {
        String filePath = args[1];
        ImageProcessingModel model = new ImageProcessingModelImpl();
        ImageProcessingView view = new ImageProcessingTextView(model);
        ImageProcessingController controller = new ImageProcessingController(model, view);
        try {
          Scanner sc = new Scanner(new FileInputStream(filePath));
          controller.menu(sc);
        } catch (FileNotFoundException e) {
          System.out.println("Script file not found");
        }
      }
    } else {
      System.out.println("Invalid command-line argument(s)");
    }
  }
}