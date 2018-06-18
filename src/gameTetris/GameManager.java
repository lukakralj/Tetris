package gameTetris;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;


/**
 * This class controls the game flow. When it is running
 * the new shapes will start dropping, etc...
 *
 * @author Luka Kralj
 * @version 18 June 2018
 */
public class GameManager implements Runnable {
    private MyGrid grid;
    private boolean running;
    private boolean paused;

    /**
     * Create a new game manager.
     *
     * @param scene Scene that listens to the key typing.
     * @param grid Grid of Tiles to operate on.
     */
    public GameManager(Scene scene, MyGrid grid) {
        this.grid = grid;
        running = false;
        paused = false;
        resetGame();

        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::keyPressed);
    }

    /**
     * Executed when the game is running.
     */
    @Override
    public void run() {
        running = true;
        while (running) {
            while (paused) {
                // Shorten this time if need more frequent checking if the game is paused.
                sleep(100);
            }
            grid.step();
            sleep(500);
        }
    }

    private void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case DOWN: grid.moveOneDown(); break;
            case UP: grid.rotate(); break;
            case LEFT: grid.moveOneLeft(); break;
            case RIGHT: grid.moveOneRight(); break;
            case SPACE: System.out.println("SPACE pressed"); break;
        }
    }

    /**
     * Pauses the game.
     */
    public void pause() {
        paused = true;
    }

    /**
     * Resumes the game.
     */
    public void resume() {
        paused = false;
    }

    /**
     * Stops the game.
     */
    public void stopGame() {
        running = false;
        paused = false;
    }

    /**
     * Resets the game.
     */
    public void resetGame() {
        grid.reset();
    }

    /**
     * Sleep for some time.
     *
     * @param millis Time in milliseconds.
     */
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
