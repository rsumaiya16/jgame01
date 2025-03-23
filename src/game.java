import processing.core.PApplet;

public class game extends PApplet {

    int birdY = 250;
    int birdSpeed = 0;
    boolean isFlapping = false;
    int gravity = 1;

    public void settings() {
        size(400, 600); // Set window size
    }

    public void setup() {
        // Initialize game settings (if any)
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
        fill(255, 0, 0);
        rect(50, birdY, 30, 30); // The bird as a red square

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

