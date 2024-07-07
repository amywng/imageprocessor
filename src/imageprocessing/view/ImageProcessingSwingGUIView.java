package imageprocessing.view;
import imageprocessing.controller.Features;
import imageprocessing.model.ImageProcessingModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Swing GUI View implementation for Image Processing editor
 */
public class ImageProcessingSwingGUIView extends JFrame implements ImageProcessingGUIView {
  private JButton flipHButton, flipVButton, greyButton, blurButton, sharpenButton, sepiaButton, brightenButton, resetButton;
  private JButton fileOpenButton, fileSaveButton;
  private JButton exitButton;
  private JPanel mainPanel, buttonPanel, brightenPanel, messagePanel, imagePanel;
  private JScrollPane mainScrollPane;
  private JLabel fileOpenDisplay, fileSaveDisplay, imageDisplay, workingNameID, brightenInstruction, fileOpenName, messageLabel, histogramLabel;
  private JScrollPane imageScrollPane;
  JRadioButton[] radioButtons;
  private JTextField brightIncrement;

  /**
   * Constructor:
   * - sets size and initializes all panels, including:
   *  - main panel that holds
   *    - image panel for showing the current working image and its corresponding histogram
   *    - button panel for editing options
   *      - brighten panel in button panel for brighten instructions and increment specifier
   *    - message panel for showing alerts/messages to user
   * - initalizes all editing buttons, including:
   *  - flipping horizontally
   *  - flipping vertically
   *  - greyscaling
   *  - blurring
   *  - sharpening
   *  - turning into sepia tone
   *  - brightening
   *  - resetting to original
   *  - opening file
   *  - saving file
   *  - exiting
   */
  public ImageProcessingSwingGUIView() {
    super("Image Processing Editor");
    setSize(500,500);

    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    //show an image with a scrollbar
    imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Working image (Name ID will display here)"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    workingNameID = new JLabel("Name ID will appear here");

    imageDisplay = new JLabel();
    imageScrollPane = new JScrollPane(imageDisplay);
    imageScrollPane.setPreferredSize(new Dimension(300,500));
    imagePanel.add(imageScrollPane);

    histogramLabel = new JLabel();
    imagePanel.add(histogramLabel);
    mainPanel.add(imagePanel);

    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    flipHButton = new JButton("flip horizontal");
    flipVButton = new JButton("flip vertical");
    greyButton = new JButton("greyscale");
    blurButton = new JButton("blur");
    sharpenButton = new JButton("sharpen");
    sepiaButton = new JButton("sepia");
    resetButton = new JButton("reset");
    brightenPanel = new JPanel();
    brightenPanel.setLayout(new BoxLayout(brightenPanel, BoxLayout.PAGE_AXIS));
    brightenButton = new JButton("brighten");
    brightenInstruction = new JLabel("<html>Enter a number<br>*Negative for darken<br>*Positive for brighten</html>");
    brightIncrement = new JTextField(5);
    brightenPanel.add(brightenButton);
    brightenPanel.add(brightenInstruction);
    brightenPanel.add(brightIncrement);

    buttonPanel.add(flipHButton);
    buttonPanel.add(flipVButton);
    buttonPanel.add(greyButton);
    buttonPanel.add(blurButton);
    buttonPanel.add(sharpenButton);
    buttonPanel.add(sepiaButton);
    buttonPanel.add(brightenPanel);

    // component option buttons
    JPanel componentPanel = new JPanel();
    componentPanel.setBorder(BorderFactory.createTitledBorder("Component Options"));
    componentPanel.setPreferredSize(new Dimension(150,200));
    componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.PAGE_AXIS));
    radioButtons = new JRadioButton[6];

    ButtonGroup radioGroup = new ButtonGroup();
    radioButtons[0] = new JRadioButton("red");
    radioButtons[1] = new JRadioButton("green");
    radioButtons[2] = new JRadioButton("blue");
    radioButtons[3] = new JRadioButton("luma");
    radioButtons[4] = new JRadioButton("intensity");
    radioButtons[5] = new JRadioButton("value");
    for (int i = 0; i < radioButtons.length; i++) {
      radioButtons[i].setSelected(false);
      radioGroup.add(radioButtons[i]);
      componentPanel.add(radioButtons[i]);
    }
    buttonPanel.add(componentPanel);
    buttonPanel.add(resetButton);
    mainPanel.add(buttonPanel);

    // upload/save
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Uploading and Saving"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);

    //file open
    JPanel fileOpenPanel = new JPanel();
    fileOpenPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileOpenPanel);
    fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");
    fileOpenPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileOpenName = new JLabel("Name ID will appear here");
    fileOpenPanel.add(fileOpenDisplay);

    //file save
    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(filesavePanel);
    fileSaveButton = new JButton("Save a file");
    fileSaveButton.setActionCommand("Save file");
    filesavePanel.add(fileSaveButton);
    fileSaveDisplay = new JLabel("File path will appear here");
    filesavePanel.add(fileSaveDisplay);

    messagePanel = new JPanel();
    messagePanel.setBorder(BorderFactory.createTitledBorder("Messages"));
    messageLabel = new JLabel("Any alerts will show up here");
    messagePanel.add(messageLabel);
    mainPanel.add(messagePanel);

    exitButton = new JButton("Exit");
    mainPanel.add(exitButton);

    getContentPane().add(mainPanel);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }

  @Override
  public void setFeatures(Features features) {
    fileOpenButton.addActionListener(evt -> {
      try {
        features.uploadImage();
      } catch (IllegalArgumentException | IOException e) {
        this.messageLabel.setText("Unable to upload image");
      }
    });
    fileSaveButton.addActionListener(evt -> {
      try {
        features.saveImage(workingNameID.getText());
      } catch (IOException | IllegalArgumentException e) {
        this.messageLabel.setText("Unable to save image");
      }
    });
    flipHButton.addActionListener(evt -> {
      try {
        features.flipImage(true, workingNameID.getText());
      } catch (NullPointerException e) {
        this.messageLabel.setText("Image hasn't been uploaded yet");
      }
    });
    flipVButton.addActionListener(evt -> {
      try {
        features.flipImage(false, workingNameID.getText());
      } catch (NullPointerException e) {
        this.messageLabel.setText("Image hasn't been uploaded yet");
      }
    });
    greyButton.addActionListener(evt -> {
      try {
        features.greyscale(workingNameID.getText());
      } catch (NullPointerException e) {
        this.messageLabel.setText("Image hasn't been uploaded yet");
      }
    });
    blurButton.addActionListener(evt -> {
      try {
        features.blur(workingNameID.getText());
      } catch (NullPointerException e) {
        this.messageLabel.setText("Image hasn't been uploaded yet");
      }
    });
    sharpenButton.addActionListener(evt -> {
      try {
        features.sharpen(workingNameID.getText());
      } catch (NullPointerException e) {
        this.messageLabel.setText("Image hasn't been uploaded yet");
      }
    });
    sepiaButton.addActionListener(evt -> {
      try {
        features.sepia(workingNameID.getText());
      } catch (NullPointerException e) {
        this.messageLabel.setText("Image hasn't been uploaded yet");
      }
    });
    brightenButton.addActionListener(evt -> {
      try {
        features.brighten(Integer.parseInt(brightIncrement.getText()), workingNameID.getText());
      } catch (NullPointerException e) {
        this.messageLabel.setText("Image hasn't been uploaded yet");
      } catch (NumberFormatException e) {
        this.messageLabel.setText("Increment not specified");
      }
    });
    radioButtons[0].addActionListener(evt -> {
      try {
        features.componentVisual("red", workingNameID.getText());
      } catch (NullPointerException e) {
        this.messageLabel.setText("Image hasn't been uploaded yet");
      }
    });
    radioButtons[1].addActionListener(evt -> {
      try {
        features.componentVisual("green", workingNameID.getText());
      } catch (NullPointerException e) {
        this.messageLabel.setText("Image hasn't been uploaded yet");
      }
    });
    radioButtons[2].addActionListener(evt -> {
      try {
        features.componentVisual("blue", workingNameID.getText());
      } catch (NullPointerException e) {
        this.messageLabel.setText("Image hasn't been uploaded yet");
      }
    });
    radioButtons[3].addActionListener(evt -> {
      try {
        features.componentVisual("luma", workingNameID.getText());
      } catch (NullPointerException e) {
        this.messageLabel.setText("Image hasn't been uploaded yet");
      }
    });
    radioButtons[4].addActionListener(evt -> {
      try {
        features.componentVisual("intensity", workingNameID.getText());
      } catch (NullPointerException e) {
        this.messageLabel.setText("Image hasn't been uploaded yet");
      }
    });
    radioButtons[5].addActionListener(evt -> {
      try {
        features.componentVisual("value", workingNameID.getText());
      } catch (NullPointerException e) {
        this.messageLabel.setText("Image hasn't been uploaded yet");
      }
    });
    exitButton.addActionListener(evt -> features.exitProgram());
    resetButton.addActionListener(evt -> {
      try {
        features.reset(fileOpenName.getText());
      } catch (NullPointerException e) {
        this.messageLabel.setText("Image hasn't been uploaded yet");
      }
    });
  }

  @Override
  public void setOpenFile(String filename, String nameID) {
    this.fileOpenDisplay.setText(filename);
    this.fileOpenName.setText(nameID);
  }

  @Override
  public void setSaveFile(String filename, String nameID) {
    this.fileSaveDisplay.setText("Image " + nameID + " saved to " + filename);
  }

  @Override
  public void setImageDisplay(Image image) {
    this.imageDisplay.setIcon(new ImageIcon(image));
  }

  @Override
  public void setWorkingNameID(String nameID) {
    this.workingNameID.setText(nameID);
    imagePanel.setBorder(BorderFactory.createTitledBorder("Working image (" + workingNameID.getText()+")"));
  }

  @Override
  public void setMessage(String message) {
    this.messageLabel.setText(message);
  }

  @Override
  public void setHistogram(Image image) {
    this.histogramLabel.setIcon(new ImageIcon(new Histogram(image).getHistogramImage()));
  }
}