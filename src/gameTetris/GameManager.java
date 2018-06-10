package gameTetris;

import javafx.scene.Scene;

public class GameManager implements Runnable {
    private Scene scene;
    private MyGrid grid;
    private boolean running;
    private boolean paused;

    public GameManager(Scene scene, MyGrid grid) {
        this.scene = scene;
        this.grid = grid;
        running = false;
        paused = false;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            while (paused) {
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    continue;
                }
            }
            grid.test();
        }
    }

    public void pause() {
        paused = true;
    }

    public void play() {
        paused = false;
    }

    public void resetGame() {
        running = false;
        paused = false;
        grid.reset();
    }

}
