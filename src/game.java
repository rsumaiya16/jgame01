import processing.core.PApplet;
import processing.core.PImage;

public class game extends PApplet {

    int birdY = 250;
    int birdSpeed = 0;
    boolean isFlapping = false;
    int gravity = 1;
    PImage birdimage;


    public void settings() {
        size(600, 600); // Set window size
    }

    public void setup() {
        birdimage=loadImage("bird.png");

    }

    public void draw() {
        background(135, 206, 235); // Sky color

        // Bird movement and gravity
        if (isFlapping) {
            birdSpeed = -15; // Move up when spacebar is pressed
            isFlapping = false;
        }

        birdSpeed += gravity; // Apply gravity
        birdY += birdSpeed;   // Update bird position

        // Draw the bird
        if (birdimage != null) {
            image(birdimage, 50, birdY);  // Render the bird image at (50, birdY)
        }
        else {
            System.out.println("image cant be laoded");
        }// The bird as a red square

        // Prevent the bird from going off-screen
        if (birdY > height - 30) birdY = height - 30;
        if (birdY < 0) birdY = 0;
    }

    public void keyPressed() {
        if (key == ' ') {
            isFlapping = true;  // Make the bird flap when spacebar is pressed
        }
    }

    public static void main(String[] args) {
        PApplet.main("game");
    }
}

