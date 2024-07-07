package imageprocessing.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents a histogram of an image to visualize distribution of color and intensity
 */
public class Histogram extends JPanel {
  private BufferedImage image;
  private int[][] histogram;
  private BufferedImage histogramImage;

  /**
   * Takes in an image and creates a histogram of RGB and intensity components, then creates the image
   * @param image source image
   */
  public Histogram(Image image) {
    this.image = (BufferedImage) image;
    this.computeHistogram();
    this.createHistogramImage();
  }

  /**
   * Computes histogram and puts values in a 2D int array
   */
  private void computeHistogram() {
    histogram = new int[4][256]; // r,g,b,intensity

    for (int y = 0; y < image.getHeight(); y+=1) {
      for (int x = 0; x < image.getWidth(); x+=1) {
        int rgb = image.getRGB(x, y);
        Color color = new Color(rgb);

        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int intensity = (red + green + blue) / 3;

        histogram[0][red]+=1;
        histogram[1][green]+=1;
        histogram[2][blue]+=1;
        histogram[3][intensity]+=1;
      }
    }
  }

  /**
   * Creates histogram image and sets it to histogramImage field
   */
  private void createHistogramImage() {
    int width = 500;
    int height = 500;
    histogramImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = histogramImage.createGraphics();

    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);

    // histogram title
    g.setFont(new Font("Arial", Font.BOLD, 16));
    g.setColor(Color.BLACK);
    g.drawString("Image Histogram", (width - g.getFontMetrics().stringWidth("Image Histogram")) / 2, 30);

    // axes lines
    g.setColor(Color.BLACK);
    g.drawLine(60, height - 60, width - 40, height - 60); // X-axis
    g.drawLine(60, height - 60, 60, 70); // Y-axis

    int max = getMaxHistogramValue();

    // number markings on axes
    g.setFont(new Font("Arial", Font.PLAIN, 12));
    for (int i = 0; i <= 256; i += 64) {
      int x = 60 + i * (width - 100) / 256;
      g.drawString(Integer.toString(i), x - 10, height - 45);
    }

    for (int i = 0; i <= max; i += max / 10) {
      int y = height - 60 - i * (height - 130) / max;
      g.drawString(Integer.toString(i), 35, y + 5);
    }

    // line charts for red, green, blue, and intensity components
    for (int i = 1; i < 256; i++) {
      int prevRedHeight = histogram[0][i - 1] * (height-130) / max;
      int redHeight = histogram[0][i] * (height-130) / max;
      g.setColor(Color.RED);
      g.drawLine(60+(i - 1) * (width-100) / 256, height - 60 - prevRedHeight, 60+i * (width-100) / 256, height - 60 - redHeight);

      int prevGreenHeight = histogram[1][i - 1] * (height-130) / max;
      int greenHeight = histogram[1][i] * (height-130) / max;
      g.setColor(Color.GREEN);
      g.drawLine(60+(i - 1) * (width-100) / 256, height - 60 - prevGreenHeight, 60+i * (width-100) / 256, height - 60 - greenHeight);

      int prevBlueHeight = histogram[2][i - 1] * (height-130) / max;
      int blueHeight = histogram[2][i] * (height-130) / max;
      g.setColor(Color.BLUE);
      g.drawLine(60+(i - 1) * (width-100) / 256, height - 60 - prevBlueHeight, 60+i * (width-100) / 256, height - 60 - blueHeight);

      int prevIntensityHeight = histogram[3][i - 1] * (height-130) / max;
      int intensityHeight = histogram[3][i] * (height-130) / max;
      g.setColor(Color.GRAY);
      g.drawLine(60+(i - 1) * (width-100) / 256, height - 60 - prevIntensityHeight, 60+i * (width-100) / 256, height - 60 - intensityHeight);
    }

    // axis titles
    g.setFont(new Font("Arial", Font.PLAIN, 14));
    g.setColor(Color.BLACK);
    g.drawString("Pixel Intensity", (width - g.getFontMetrics().stringWidth("Pixel Intensity")) / 2, height - 15);

    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setFont(new Font("Arial", Font.PLAIN, 14));
    g2d.setColor(Color.BLACK);
    g2d.rotate(-Math.PI / 2);
    g2d.drawString("Pixel Frequency", -((height + g2d.getFontMetrics().stringWidth("Frequency")) / 2), 20);
    g2d.dispose();
    g.dispose();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(histogramImage,0,0, this);
  }

  /**
   * returns histogramImage
   * @return histogramImage
   */
  public BufferedImage getHistogramImage() {
    return this.histogramImage;
  }

  /**
   * Gets max value from histogram
   * @return integer max in histogram
   */
  private int getMaxHistogramValue() {
    int max = 0;
    for (int i = 0; i < 256; i+=1) {
      for (int j = 0; j < 4; j+=1) {
        if (histogram[j][i] > max) {
          max = histogram[j][i];
        }
      }
    }
    return max;
  }
}