package imageprocessing.view;

import imageprocessing.model.ImageProcessingModel;

public class ImageProcessingTextView implements ImageProcessingView {
  private final ImageProcessingModel model;
  public ImageProcessingTextView(ImageProcessingModel model) {
    this.model = model;
  }

  @Override
  public String view(String nameID) {
    return "This model has " + this.model.getSize() + " total image(s).";
  }
}