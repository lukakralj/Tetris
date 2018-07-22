package gameTetris;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;


/**
 * This class controls the game flow. When it is running
 * the new shapes will start dropping, etc...
 *
 * @author Luka Kralj
 * @version 18 June 2018
 */
public class GameManager implements Runnable {
    private MyGrid grid;
    private MediaPlayer player;
    private boolean isMusicPlaying;
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
        isMusicPlaying = true;
        resetGame();
        startMusic();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::keyPressed);
    }


    /**
     * Starts the music "korobeiniki"
     */
    private void startMusic() {
        Media media = new Media(new File("src/gameTetris/korobeiniki.mp3").toURI().toString());
        player = new MediaPlayer(media);
        player.setOnEndOfMedia(new Runnable() {
            public void run() {
                player.seek(Duration.ZERO);
            }
        });
        player.play();
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

    /**
     * Toggles music in the game
     */
    public void toggleMusic(){
        if(isMusicPlaying){
            player.pause();
            isMusicPlaying = false;
        }
        else {
            player.play();
            isMusicPlaying = true;
        }
    }
}
