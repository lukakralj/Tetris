package gameTetris;

import javafx.scene.Scene;

/**
 * This class controls the game flow. When it is running
 * the new shapes will start dropping, etc...
 *
 * @author Luka Kralj
 * @version 10 June 2018
 */
public class GameManager implements Runnable {
    private Scene scene;
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
        this.scene = scene;
        this.grid = grid;
        running = false;
        paused = false;
    }

    /**
     * Executed when the game is running.
     */
    @Override
    public void run() {
        running = true;
        while (running) {
            while (paused) {
                try {
                    // Shorten this time if need more frequent checking if the game is paused.
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    continue;
                }
            }
            // TODO: add code to move blocks, delete full rows, count points etc.
            grid.test();
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
}
