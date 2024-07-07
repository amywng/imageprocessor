package imageprocessing.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Implementation for an Image Processing editor model
 */
public class ImageProcessingModelImpl implements ImageProcessingModel {

  // All working images in the model with a name ID associated
  private Map<String, Color[][]> images;

  /**
   * Default constructor, sets the images map to a new Hashmap
   */
  public ImageProcessingModelImpl() {
    this.images = new HashMap<>();
  }

  /**
   * Constructor that loads a single image into the images map with the given nameID,
   * handling different file types of images
   * @param filename path of image to load
   * @param nameID ID image will go by in model
   */
  public ImageProcessingModelImpl(String filename, String nameID) throws IOException {
    this.images = new HashMap<>();
    if (filename.substring(filename.length() - 4).equals(".ppm")) {
      this.loadImage(filename, nameID);
    } else {
      this.imageToArray(filename, nameID);
    }
  }

  @Override
  public void loadImage(String filename, String nameID) {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      throw new IllegalArgumentException("File " + filename + " not found!");
    }

    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0)!='#') {
        builder.append(s+System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());
    String token;
    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int biggest = sc.nextInt();
    this.images.put(nameID, new Color[height][width]);
    Color[][] p = this.images.get(nameID);
    for (int h = 0; h < height; h += 1) {
      for (int w = 0; w < width; w += 1) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        p[h][w] = new Color(r, g, b);
      }
    }
  }

  @Override
  public void rgb(String rgb, String nameID, String destName) {
    switch (rgb) {
      case "red":
        this.images.put(destName, new Color[this.getHeight(nameID)][this.getWidth(nameID)]);
        for (int height = 0; height < this.getHeight(nameID); height+=1) {
          for (int width = 0; width < this.getWidth(nameID); width+=1) {
            int red = this.images.get(nameID)[height][width].getRed();
            this.images.get(destName)[height][width] = new Color(red,red,red);
          }
        }
        break;
      case "green":
        this.images.put(destName, new Color[this.getHeight(nameID)][this.getWidth(nameID)]);
        for (int height = 0; height < this.getHeight(nameID); height+=1) {
          for (int width = 0; width < this.getWidth(nameID); width+=1) {
            int green = this.images.get(nameID)[height][width].getGreen();
            this.images.get(destName)[height][width] = new Color(green,green,green);
          }
        }
        break;
      case "blue":
        this.images.put(destName, new Color[this.getHeight(nameID)][this.getWidth(nameID)]);
        for (int height = 0; height < this.getHeight(nameID); height+=1) {
          for (int width = 0; width < this.getWidth(nameID); width+=1) {
            int blue = this.images.get(nameID)[height][width].getBlue();
            this.images.get(destName)[height][width] = new Color(blue,blue,blue);
          }
        }
        break;
      default:
        throw new IllegalArgumentException("given color is not red, green, or blue");
    }
  }

  @Override
  public void value(String nameID, String destName) {
    this.images.put(destName, new Color[this.getHeight(nameID)][this.getWidth(nameID)]);
    for (int height = 0; height < this.getHeight(nameID); height+=1) {
      for (int width = 0; width < this.getWidth(nameID); width+=1) {
        int red = this.images.get(nameID)[height][width].getRed();
        int green = this.images.get(nameID)[height][width].getGreen();
        int blue = this.images.get(nameID)[height][width].getBlue();
        int max = Math.max(red, Math.max(green, blue));
        this.images.get(destName)[height][width] = new Color(max,max,max);
      }
    }
  }

  @Override
  public void intensity(String nameID, String destName) {
    this.images.put(destName, new Color[this.getHeight(nameID)][this.getWidth(nameID)]);
    for (int height = 0; height < this.getHeight(nameID); height+=1) {
      for (int width = 0; width < this.getWidth(nameID); width+=1) {
        int red = this.images.get(nameID)[height][width].getRed();
        int green = this.images.get(nameID)[height][width].getGreen();
        int blue = this.images.get(nameID)[height][width].getBlue();
        int average = (red + green + blue)/3;
        this.images.get(destName)[height][width] = new Color(average,average,average);
      }
    }
  }

  @Override
  public void colorTransform(double[][] matrix, String nameID, String destName) {
    this.images.put(destName, new Color[this.getHeight(nameID)][this.getWidth(nameID)]);
    for (int height = 0; height < this.getHeight(nameID); height+=1) {
      for (int width = 0; width < this.getWidth(nameID); width+=1) {
        int rOG = this.images.get(nameID)[height][width].getRed();
        int gOG = this.images.get(nameID)[height][width].getGreen();
        int bOG = this.images.get(nameID)[height][width].getBlue();
        int red = (int) (rOG * matrix[0][0] + gOG * matrix[0][1] + bOG * matrix[0][2]);
        int green = (int) (rOG * matrix[1][0] + gOG * matrix[1][1] + bOG * matrix[1][2]);
        int blue = (int) (rOG * matrix[2][0] + gOG * matrix[2][1] + bOG * matrix[2][2]);
        this.images.get(destName)[height][width] = new Color(Math.max(Math.min(red, 255),0),Math.max(Math.min(green, 255),0),Math.max(Math.min(blue, 255),0));
      }
    }
  }

  @Override
  public void flip(boolean horizontal, String nameID, String destName) {
    this.images.put(destName, new Color[this.getHeight(nameID)][this.getWidth(nameID)]);
    if (horizontal) {
      for (int height = 0; height < this.getHeight(nameID); height+=1) {
        for (int width = 0; width < this.getWidth(nameID); width+=1) {
          Color left = this.images.get(nameID)[height][width];
          Color right = this.images.get(nameID)[height][this.getWidth(nameID)-width-1];
          this.images.get(destName)[height][width] = right;
          this.images.get(destName)[height][this.getWidth(nameID)-width-1] = left;
        }
      }
    } else {
      for (int width = 0; width < this.getWidth(nameID); width+=1) {
        for (int height = 0; height < this.getHeight(nameID); height+=1) {
          Color top = this.images.get(nameID)[height][width];
          Color bottom = this.images.get(nameID)[this.getHeight(nameID) - height - 1][width];
          this.images.get(destName)[height][width] = bottom;
          this.images.get(destName)[this.getHeight(nameID) - height - 1][width] = top;
        }
      }
    }
  }

  @Override
  public void brighten(int increment, String nameID, String destName) {
    this.images.put(destName, new Color[this.getHeight(nameID)][this.getWidth(nameID)]);
    for (int height = 0; height < this.getHeight(nameID); height+=1) {
      for (int width = 0; width < this.getWidth(nameID); width+=1) {
        int red = this.images.get(nameID)[height][width].getRed();
        int green = this.images.get(nameID)[height][width].getGreen();
        int blue = this.images.get(nameID)[height][width].getBlue();
        if (increment < 0) {
          this.images.get(destName)[height][width] = new Color(Math.max(red + increment, 0),Math.max(green + increment, 0),Math.max(blue + increment, 0));
        } else {
          this.images.get(destName)[height][width] = new Color(Math.min(red + increment, 255),Math.min(green + increment, 255),Math.min(blue + increment, 255));
        }
      }
    }
  }

  @Override
  public void filter(double[][] matrix, String nameID, String destName) {
    this.images.put(destName, new Color[this.getHeight(nameID)][this.getWidth(nameID)]);
    Color[][] image = this.images.get(nameID);
    for (int height = 0; height < this.getHeight(nameID); height+=1) {
      for (int width = 0; width < this.getWidth(nameID); width+=1) {
        this.images.get(destName)[height][width] = this.kernelComp(image, height, width, matrix);
      }
    }
  }

  @Override
  public void saveImage(String filename, String nameID) throws IOException {
    Color[][] p = this.images.get(nameID);
    int height = this.getHeight(nameID);
    int width = this.getWidth(nameID);
    String type = filename.substring(filename.indexOf('.'));
    if (type.equals(".ppm")) {
      FileWriter writer;
      try {
        writer = new FileWriter(filename);
      } catch (IOException e) {
        throw new IllegalArgumentException("file path doesn't exist");
      }
      StringBuilder builder = new StringBuilder();
      builder.append("P3\n");
      builder.append(width + " " + height +"\n");
      builder.append("255\n");
      for (int h = 0; h < height; h += 1) {
        for (int w = 0; w < width; w += 1) {
          int red = p[h][w].getRed();
          int green = p[h][w].getGreen();
          int blue = p[h][w].getBlue();
          builder.append(red + " " + green + " " + blue + " \n");
        }
      }
      writer.write(builder.toString());
      writer.close();
    } else {
      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      for (int h = 0; h < height; h += 1) {
        for (int w = 0; w < width; w += 1) {
          image.setRGB(w,h,p[h][w].getRGB());
        }
      }
      try {
        ImageIO.write(image, type.substring(1), new File(filename));
      } catch (IOException e) {
        throw new IllegalArgumentException("no such file or directory found");
      }
    }
  }

  @Override
  public int getHeight(String nameID) {
    return this.images.get(nameID).length;
  }

  @Override
  public int getWidth(String nameID) {
    return this.images.get(nameID)[0].length;
  }

  @Override
  public Color pixelRGB(int row, int col, String nameID) {
    return this.images.get(nameID)[row][col];
  }

  @Override
  public int getSize() {
    return this.images.size();
  }

  @Override
  public boolean existingID(String id) {
    return this.images.containsKey(id);
  }

  @Override
  public Image getImage(String nameID) {
    BufferedImage image = new BufferedImage(this.getWidth(nameID),
        this.getHeight(nameID), BufferedImage.TYPE_INT_RGB);
    for(int x = 0; x < this.getHeight(nameID) - 1; x += 1) {
      for(int y = 0; y < this.getWidth(nameID) - 1; y += 1) {
        image.setRGB(y, x, this.pixelRGB(x, y, nameID).getRGB());
      }
    }
    return image;
  }

  @Override
  public void imageToArray(String filename, String nameID) throws IOException {
    BufferedImage image;
    try {
      image = ImageIO.read(new File(filename));
    } catch (IOException e) {
      throw new IllegalArgumentException("file not found");
    }
    int width = image.getWidth();
    int height = image.getHeight();
    Color[][] colorArr = new Color[height][width];
    for (int h = 0; h < height; h += 1) {
      for (int w = 0; w < width; w += 1) {
        colorArr[h][w] = new Color(image.getRGB(w,h));
      }
    }
    this.images.put(nameID, colorArr);
  }

  /**
   * Computes filtered color for a pixel at curHeight and curWidth in the given 2d Color array image
   * with the given kernel matrix
   * @param image working image
   * @param curHeight current height of pixel to compute color for
   * @param curWidth current width of pixel to compute color for
   * @param matrix kernel matrix
   * @return Color after kernel was applied to filter the pixel
   */
  private Color kernelComp(Color[][] image, int curHeight, int curWidth, double[][] matrix) {
    int mid = matrix.length/2;
    double red = 0;
    double green=0;
    double blue =0;
    for (int row=0;row<matrix.length;row+=1) {
      for (int col=0;col<matrix.length;col+=1) {
        int curRow = curHeight - mid + row;
        int curCol = curWidth - mid + col;
        if (curRow >=0 && curRow < image.length && curCol>=0 && curCol < image[0].length) {
          red += matrix[row][col] * image[curRow][curCol].getRed();
          green += matrix[row][col] * image[curRow][curCol].getGreen();
          blue += matrix[row][col] * image[curRow][curCol].getBlue();
        }
      }
    }
    return new Color(Math.min(Math.max((int)red, 0), 255), Math.min(Math.max((int)green, 0), 255), Math.min(Math.max((int)blue, 0), 255));
  }
}