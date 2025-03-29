import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class game extends PApplet {

    int birdY = 250;
    int birdSpeed = 0;
    boolean isFlapping = false;
    int gravity = 1;
    PImage birdImage;

    ArrayList<Obstacle> obstacles;
    int score = 0;
    boolean isGameOver = false;  // To track if the game is over

    public void settings() {
        size(600, 600); // Set window size
    }

    public void setup() {
        birdImage = loadImage("bird.png");
        birdImage.resize(50, 50);

        obstacles = new ArrayList<Obstacle>();
    }

    public void draw() {
        background(135, 206, 235); // Sky color

        if (isGameOver) {
            showGameOverScreen();
            return;  // Skip the rest of the draw() code if game is over
        }

        // Bird movement and gravity
        if (isFlapping) {
            birdSpeed = -15; // Move up when spacebar is pressed
            isFlapping = false;
        }

        birdSpeed += gravity; // Apply gravity
        birdY += birdSpeed;   // Update bird position

        // Draw the bird
        if (birdImage != null) {
            image(birdImage, 50, birdY);  // Render the bird image at (50, birdY)
        }

        // Prevent the bird from going off-screen
        if (birdY > height - 30) birdY = height - 30;
        if (birdY < 0) birdY = 0;

        // Draw and update obstacles
        if (frameCount % 60 == 0) { // Generate a new obstacle every 60 frames (1 second)
            obstacles.add(new Obstacle(width, (int) random(100, height - 100)));
        }

        // Move obstacles and check for collisions
        for (int i = obstacles.size() - 1; i >= 0; i--) {
            Obstacle obs = obstacles.get(i);
            obs.update();

            // Check for collision
            if (obs.checkCollision(50, birdY, birdImage.width, birdImage.height)) {
                isGameOver = true;  // Game Over if collision occurs
            }

            // Remove obstacles that go off screen
            if (obs.x < -60) {
                obstacles.remove(i);
                score++; // Increment score when the bird passes an obstacle
            }

            // Draw obstacles
            obs.show();
        }

        // Render the score
        fill(255);
        textSize(32);
        text("Score: " + score, 20, 40);
    }

    public void keyPressed() {
        if (key == ' ') {
            isFlapping = true;  // Make the bird flap when spacebar is pressed
        }

        if (isGameOver && key == 'r') {
            restartGame();  // Restart game if 'r' is pressed
        }
    }

    public static void main(String[] args) {
        PApplet.main("game");
    }

    // Obstacle class to handle obstacles
    class Obstacle {
        int x, y, width = 60, gap = 150;

        Obstacle(int startX, int startY) {
            x = startX;
            y = startY;
        }

        void update() {
            x -= 6; // Move the obstacle to the left
        }

        void show() {
            fill(0, 255, 0); // Green obstacles
            rect(x, 0, width, y);  // Top obstacle
            rect(x, y + gap, width, height - y - gap);  // Bottom obstacle
        }

        boolean checkCollision(int birdX, int birdY, int birdWidth, int birdHeight) {
            // Check if the bird collides with the obstacle
            if (birdX + birdWidth > x && birdX < x + width) {
                if (birdY < y || birdY + birdHeight > y + gap) {
                    return true;  // Collision detected
                }
            }
            return false;  // No collision
        }
    }

    // Show the Game Over screen
    private void showGameOverScreen() {
        fill(0);
        textSize(48);
        textAlign(CENTER, CENTER);
        text("Game Over", width / 2, height / 2 - 40);
        textSize(32);
        text("Score: " + score, width / 2, height / 2 + 10);

        // Draw a "Resume" button
        fill(0, 255, 0);
        rect(width / 2 - 100, height / 2 + 60, 200, 50);  // Button rectangle
        fill(0);
        textSize(24);
        text(" Resume", width / 2, height / 2 + 90);  // Text on the button
    }

    // Restart the game
    private void restartGame() {
        birdY = 250;
        birdSpeed = 0;
        score = 0;
        obstacles.clear();
        isGameOver = false;
        loop();  // Start the game loop again
    }
}
