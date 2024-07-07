package imageprocessing.view;
import javax.swing.*;

public interface ImageProcessingView {

  /**
   * Returns visual representation of image processing editor in text form
   * @param nameID
   * @return
   */
  String view(String nameID);
}