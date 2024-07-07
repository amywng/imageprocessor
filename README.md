**Overview (TLDR):**

This Image Processing editor can edit images by converting them to greyscale in a multitude of techniques
(including using the individual pixels' luma, intensity, value, red component, blue component, and 
green component), brighten or darken them, apply sepia tone to them, blur/sharpen them, and flipping 
them horizontally or vertically. To start, download the zip and run the jar in the "res" folder called "Program.jar". 
You can do this in 4 ways. The first three ways require using your terminal.
1) java -jar path/to/Program.jar (where path/to is replaced by the path to get to the jar file from your current directory)
    * This will open the graphical user interface where you can experiment with the Image Processing Editor.
2) java -jar path/to/Program.jar -text
    * This will open the program in interactive text mode, where you can type a script and execute it 
   one line at a time with the script commands specified below.
3) java -jar path/to/Program.jar -file path-of-script-file
    * This will cause the program to open the script file, execute it, and then shut it down
4) Double-clicking the jar file will open the graphical user interface

This editor takes ppm, bmp, jpeg, and png filetypes and can save as those filetypes. Have fun!

**Script of commands:**

In the following commands, dest-image-name refers to the name which the newly created image will be referred to as in the program and image-name refers to the name of the image to do the command on.

**load image-path image-name:** Load an image from the specified path and refer it to henceforth in the program by the given image name.

ex. load res/snail.bmp snail

**save image-path image-name:** Save the image with the given name to the specified path which should include the name of the file.

ex. save res/snail2.bmp snail

**red-component image-name dest-image-name:** Create a greyscale image with the red-component of the image with the given name, and refer to it henceforth in the program by the given destination name.

ex. red-component snail snailRed

Similar commands:

**green-component image-name dest-image-name**

ex. green-component snail snailGreen

**blue-component image-name dest-image-name**

ex. blue-component snail snailBlue

**value-component image-name dest-image-name**

ex. value-component snail snailValue

**intensity-component image-name dest-image-name**

ex. intensity-component snail snailIntensity

**luma-component image-name dest-image-name** and **greyscale image-name dest-image-name** (do the same thing)

ex. luma-component snail snailLuma

**horizontal-flip image-name dest-image-name:** Flip an image horizontally to create a new image, referred to henceforth by the given destination name.

ex. horizontal-flip snail snailHF

**vertical-flip image-name dest-image-name:** Flip an image vertically to create a new image, referred to henceforth by the given destination name.

ex. vertical-flip snail snailVF

**brighten increment image-name dest-image-name:** Brighten the image by the given increment to create a new image, referred to henceforth by the given destination name. The increment may be positive (brightening) or negative (darkening).

ex. brighten 10 snail snailBright, brighten -10 snail snailDark

**sepia image-name dest-image-name:** Create a sepia-toned image from an image.

ex. sepia snail snailSepia

**blur image-name dest-image-name:** Blur an image with Gaussian blur.

ex. blur snail snailBlur

**sharpen image-name dest-image-name:** Sharpen an image.

ex. sharpen snail snailSharpen

**file script-name.txt:** Run a script file and exit program afterwards.

ex. file res/script.txt

**exit:** Exit the system.

ex. exit

**Image Citations:**

res/example.ppm: https://filesamples.com/formats/ppm

res/rainier.JPG: owned by me

res/snail.bmp: https://people.math.sc.edu/Burkardt/data/bmp/bmp.html

res/manhattan-small.png: provided by the assignment
