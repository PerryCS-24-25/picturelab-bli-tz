import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

/**
 * A class that represents a picture. This class inherits from SimplePicture and
 * allows the student to add functionality to the Picture class.
 *
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture {
    ///////////////////// constructors //////////////////////////////////

    /**
     * Constructor that takes no arguments
     */
    public Picture() {
        /* not needed but use it to show students the implicit call to super()
         * child constructors always call a parent constructor 
         */
        super();
    }

    /**
     * Constructor that takes a file name and creates the picture
     *
     * @param fileName the name of the file to create the picture from
     */
    public Picture(String fileName) {
        // let the parent class handle this fileName
        super(fileName);
    }

    /**
     * Constructor that takes a file name and creates the picture
     *
     * @param file the name of the file to create the picture from
     */
    public Picture(java.io.File file) {
        // let the parent class handle this fileName
        super(file);
    }

    /**
     * Constructor that takes the width and height
     * 
     * @param height the height of the desired picture
     * @param width the width of the desired picture
     */
    public Picture(int width, int height) {
        // let the parent class handle this width and height
        super(width, height);
    }

    /**
     * Constructor that takes a picture and creates a copy of that picture
     *
     * @param copyPicture the picture to copy
     */
    public Picture(SimplePicture copyPicture) {
        // let the parent class do the copy
        super(copyPicture);
    }

    /**
     * Constructor that takes a buffered image
     *
     * @param image the buffered image to use
     */
    public Picture(BufferedImage image) {
        super(image);
    }

    ////////////////////// methods ///////////////////////////////////////
    /**
     * Method to return a string with information about this picture.
     *
     * @return a string with information about the picture such as fileName,
     * height and width.
     */
    public String toString() {
        String output = "Picture, filename " + getFileName()
            + " height " + getHeight()
            + " width " + getWidth();
        return output;

    }

    /**
     * Method to create a new picture by scaling the current picture by the given
     * x and y factors
     *
     * @param xFactor the amount to scale in x
     * @param yFactor the amount to scale in y
     * @return the resulting picture
     */
    public Picture scale(double xFactor, double yFactor) {
        // set up the scale transform
        AffineTransform scaleTransform = new AffineTransform();
        scaleTransform.scale(xFactor, yFactor);

        // create a new picture object that is the right size
        Picture result = new Picture((int) (getWidth() * xFactor),
                (int) (getHeight() * yFactor));

        // get the graphics 2d object to draw on the result
        Graphics graphics = result.getGraphics();
        Graphics2D g2 = (Graphics2D) graphics;

        // draw the current image onto the result image scaled
        g2.drawImage(this.getImage(), scaleTransform, null);

        result.setTitle(getTitle());
        return result;
    }

    /**
     * Method to set the blue to 0
     */
    public void zeroBlue() {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                pixelObj.setBlue(0);
            }
        }
    }

    /**
     * Removes all the red from this image.
     */
    public void zeroRed() {
        //TODO: Write this method.
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                pixelObj.setRed(0);
            }
        }
    }

    /**
     * Removes all the green from this image.
     */
    public void zeroGreen() {
        //TODO: Write this method.  
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                pixelObj.setGreen(0);
            }
        } 
    }

public void keepOnlyBlue() {
    zeroRed();
    zeroGreen();
}

public void keepOnlyRed() {
    zeroBlue();
    zeroGreen();
}

public void keepOnlyGreen() {
    zeroRed();
    zeroBlue();
}

public void negate() {
    Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                pixelObj.setGreen(255 - pixelObj.getGreen());
                pixelObj.setRed(255 - pixelObj.getRed());
                pixelObj.setBlue(255 - pixelObj.getBlue());
            }
        }
}

public void grayScale() {
    Pixel[][] pixels = this.getPixels2D();
    
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                double avg = pixelObj.getAverage();
                pixelObj.setGreen((int)avg);
                pixelObj.setRed((int)avg);
                pixelObj.setBlue((int)avg);
            }
        }
}

public void blackAndWhite() {
    Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                double avg = pixelObj.getAverage();
                if (avg > 127) {
                    pixelObj.setGreen(255);
                    pixelObj.setRed(255);
                    pixelObj.setBlue(255);
                } else {
                    pixelObj.setGreen(0);
                    pixelObj.setRed(0);
                    pixelObj.setBlue(0);
                }
                
            }
        }
}

public void fixUnderwater() {
    Pixel[][] pixels = this.getPixels2D();
    int largestGreen = 0;
    int smallestGreen = 255;
    int largestBlue = 0;
    int smallestBlue = 255;
    int largestRed = 0;
    int smallestRed = 255;
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                int currentGreen = pixelObj.getGreen();
                if (currentGreen > largestGreen) {
                    largestGreen = currentGreen;
                }

                if (currentGreen < smallestGreen) {
                    smallestGreen = currentGreen;
                }

                int currentBlue = pixelObj.getBlue();
                if (currentBlue > largestBlue) {
                    largestBlue = currentBlue;
                }

                if (currentBlue < smallestBlue) {
                    smallestBlue = currentBlue;
                }

                int currentRed = pixelObj.getRed();
                if (currentRed > largestRed) {
                    largestRed = currentRed;
                }

                if (currentRed < smallestRed) {
                    smallestRed = currentRed;
                }
            }
        }
        int redRange = largestRed - smallestRed;
        int greenRange = largestGreen - smallestGreen;
        int blueRange = largestBlue - smallestBlue;
        double scaleFactorRed = 255.0 / redRange;
        double scaleFactorBlue = 255.0 / blueRange;
        double scaleFactorGreen = 255.0 / greenRange;
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                int currentGreen = pixelObj.getGreen() - smallestGreen;
                int currentBlue = pixelObj.getBlue() - smallestBlue;
                int currentRed = pixelObj.getRed() - smallestRed;
                pixelObj.setRed((int)(currentRed * scaleFactorRed));
                pixelObj.setBlue((int)(currentBlue * scaleFactorBlue));
                pixelObj.setGreen((int)(currentGreen * scaleFactorGreen));
            }
        }
}

    /**
     * Method that mirrors the picture around a vertical mirror in the center of
     * the picture from left to right
     */
    public void mirrorVertical() {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < width / 2; col++) {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    public void mirrorVerticalRightToLeft() {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < width / 2; col++) {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                leftPixel.setColor(rightPixel.getColor());
            }
        }
    }

    public void mirrorHorizontal() {
        Pixel[][] pixels = this.getPixels2D();
        Pixel topPixel = null;
        Pixel botPixel = null;
        int height = pixels.length;
        for (int col = 0; col < pixels[0].length; col++) {
            for (int row = 0; row < pixels.length; row++) {
                topPixel = pixels[row][col];
                botPixel = pixels[height - 1 - row][col];
                botPixel.setColor(topPixel.getColor());
            }
        }
    }

    public void mirrorHorizontalBotToTop() {
        Pixel[][] pixels = this.getPixels2D();
        Pixel topPixel = null;
        Pixel botPixel = null;
        int height = pixels.length;
        for (int col = 0; col < pixels[0].length; col++) {
            for (int row = 0; row < pixels.length; row++) {
                topPixel = pixels[row][col];
                botPixel = pixels[height - 1 - row][col];
                topPixel.setColor(botPixel.getColor());
            }
        }
    }

    public void mirrorDiagonal() {
        Pixel[][] pixels = this.getPixels2D();
        Pixel botLeftPixel = null;
        Pixel topRightPixel = null;
        int length = 0;
        // int height = pixels.length;
        // int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                if (pixels.length > pixels[0].length) {
                    length = pixels[0].length;
                } else {
                    length = pixels.length;
                }
                if (row != col && row < length && col < length) {
                    botLeftPixel = pixels[row][col];
                    topRightPixel = pixels[col][row];
                    topRightPixel.setColor(botLeftPixel.getColor());
                }
                    
                
            }
        }
    }

    /**
     *  Creates a vertical mirror image of the this picture.
     */
    public void verticalReflection() {
        //TODO: Write this method.
    }

    /**
     * Converts this image into a horizontal mirror image of itself.
     */
    public void horizontalReflection() {
        //TODO: Write this method.
    }

    /**
     * Mirror just part of a picture of a temple
     */
    public void mirrorTemple() {
        int mirrorPoint = 276;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int count = 0;
        Pixel[][] pixels = this.getPixels2D();

        // loop through the rows
        for (int row = 27; row < 97; row++) {
            // loop from 13 to just before the mirror point
            for (int col = 13; col < mirrorPoint; col++) {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][mirrorPoint - col + mirrorPoint];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
        System.out.println(count);
    }

    public void mirrorArms() {
        int mirrorPoint1 = 190;
        Pixel topPixel1 = null;
        Pixel botPixel1 = null;
        Pixel[][] pixels = this.getPixels2D();

        for (int row = 160; row < mirrorPoint1; row++) {
            // loop from 13 to just before the mirror point
            for (int col = 105; col < 170; col++) {
                topPixel1 = pixels[row][col];
                botPixel1 = pixels[mirrorPoint1 - row + mirrorPoint1][col];
                botPixel1.setColor(topPixel1.getColor());
            }
        }

        for (int row = 160; row < mirrorPoint1; row++) {
            // loop from 13 to just before the mirror point
            for (int col = 235; col < 295; col++) {
                topPixel1 = pixels[row][col];
                botPixel1 = pixels[mirrorPoint1 - row + mirrorPoint1][col];
                botPixel1.setColor(topPixel1.getColor());
            }
        }
    }

    public void mirrorGull() {
        int mirrorPoint1 = 350;
        Pixel rightPixel = null;
        Pixel leftPixel = null;
        Pixel[][] pixels = this.getPixels2D();

        for (int row = 230; row < 325; row++) {
            // loop from 13 to just before the mirror point
            for (int col = 230; col < mirrorPoint1; col++) {
                rightPixel = pixels[row][col];
                leftPixel = pixels[row][mirrorPoint1 - col + mirrorPoint1];
                leftPixel.setColor(rightPixel.getColor());
            }
        }

        // for (int row = 160; row < mirrorPoint1; row++) {
        //     // loop from 13 to just before the mirror point
        //     for (int col = 235; col < 295; col++) {
        //         topPixel1 = pixels[row][col];
        //         botPixel1 = pixels[mirrorPoint1 - row + mirrorPoint1][col];
        //         botPixel1.setColor(topPixel1.getColor());
        //     }
        // }
    }

    /**
     * copy from the passed fromPic to the specified startRow and startCol in the
     * current picture
     *
     * @param fromPic the picture to copy from
     * @param startRow the start row to copy to
     * @param startCol the start col to copy to
     */
    public void copy(Picture fromPic, int startRow, int startCol) {
        Pixel fromPixel = null;
        Pixel toPixel = null;
        Pixel[][] toPixels = this.getPixels2D();
        Pixel[][] fromPixels = fromPic.getPixels2D();
        for (int fromRow = 0, toRow = startRow;
        fromRow < fromPixels.length
        && toRow < toPixels.length;
        fromRow++, toRow++) {
            for (int fromCol = 0, toCol = startCol;
            fromCol < fromPixels[0].length
            && toCol < toPixels[0].length;
            fromCol++, toCol++) {
                fromPixel = fromPixels[fromRow][fromCol];
                toPixel = toPixels[toRow][toCol];
                toPixel.setColor(fromPixel.getColor());
            }
        }
    }

    /**
     * 
     * @param fromPic The source image we are copying from
     * @param destRow the start row to copy to
     * @param destCol the start col to copy to
     * @param fromRow The start row of fromPic
     * @param fromCol The start col of fromPic
     * @param w Width of the area we wish to copy.
     * @param h Height of the area we wish to copy.
     */
    public void copy(Picture fromPic,int destRow, int destCol, int fromRow, int fromCol, int w, int h) {
        //TODO: Write and test this method
    }

    /**
     * Method to show large changes in color
     *
     * @param edgeDist the distance for finding edges
     */
    public void edgeDetection(int edgeDist) {
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        Color rightColor = null;
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0;
            col < pixels[0].length - 1; col++) {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][col + 1];
                rightColor = rightPixel.getColor();
                if (leftPixel.colorDistance(rightColor)
                > edgeDist)
                    leftPixel.setColor(Color.BLACK);
                else
                    leftPixel.setColor(Color.WHITE);
            }
        }
    }

    /* Main method for testing - each class in Java can have a main 
     * method 
     */
    public static void main(String[] args) {
        Picture beach = new Picture("beach.jpg");
        beach.explore();
        beach.zeroBlue();
        beach.explore();
    }

} // this } is the end of class Picture, put all new methods before this
