package ch.epfl.cs107.icmon.audio;

import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.io.ResourcePath;
import ch.epfl.cs107.play.window.swing.SwingSound;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Represents the sound manager of the game.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class ICMonSoundManager {
    private final FileSystem fileSystem;
    private Clip currentClip;
    private Clip backgroundClip;
    private String currentPlayingSound = "N/A";
    private int timeLeft = 0;
    private boolean isPlayingPrioritySound = false;
    private boolean resetRequired = false;
    private LineListener stopListener;

    /**
     * Creates a new game sound manager
     *
     * @param fileSystem game's file system
     */
    public ICMonSoundManager (FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * Creates a SwingSound from a given sound name
     * @param soundName the name of the sound to read
     * @return the SwingSound
     */
    private SwingSound readSwingSound(String soundName) {
        try {
            InputStream inputStream = fileSystem.read(ResourcePath.getSound(soundName));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            return new SwingSound(bufferedInputStream);
        } catch (Exception e) {
            System.out.println("Can not read sound... " + soundName);
            return null;
        }
    }

    /**
     * Stops the currently played sound if there is one
     * and resets the sound manager state
     * this method shall be called just before a new sound is starting
     * or when the current sound has ended
     */
    public void resetSound () {
        resetRequired = false;
        if (currentClip != null) {
            currentClip.removeLineListener(stopListener);
            currentClip.stop();
            currentClip = null;
        }
        timeLeft = -1;
        isPlayingPrioritySound = false;
        currentPlayingSound = "N/A";
    }

    /**
     * Plays a sound for a given duration
     *
     * @param name the name of the sound to play
     * @param duration the duration of the sound in frames
     */
    public void playSound (String name, int duration) {
        assert duration > 0;
        playSound(name, duration, false);
    }

    /**
     * Plays a sound for a given duration
     *
     * @param name the name of the sound to play
     * @param duration the duration of the sound in frames
     * @param isPrioritySound whether this sound should replace the currently playing sound if there is one
     */
    public void playSound (String name, int duration, boolean isPrioritySound) {
        assert duration > 0;
        SwingSound sound = readSwingSound(name);
        if (sound == null) return;

        // if the same sound is already playing
        // just extend its duration
        if (this.currentPlayingSound.equals(name)) {
            timeLeft = duration;
            return;
        }

        // if the given sound is not priority and a priority sound is already playing
        // skip it
        if (isPlayingPrioritySound && !isPrioritySound) return;

        // reset all the currently playing sound
        resetSound();
        isPlayingPrioritySound = isPrioritySound;

        resetRequired = true;
        Clip clip = sound.openedClip(0);
        if (clip != null) {
            clip.start();

            LineListener listener = event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    resetSound();
                }
            };

            // detect when file ends
            clip.addLineListener(listener);
            stopListener = listener;
        }
        this.currentPlayingSound = name;
        this.timeLeft = duration;
        this.currentClip = clip;
    }

    /**
     * Plays a background sound
     * @param name the name of the sound to play
     */
    public void playBackgroundSound (String name) {
        SwingSound sound = readSwingSound(name);
        if (sound == null) return;

        Clip clip = sound.openedClip(0);
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        this.backgroundClip = clip;
    }

    /**
     * Stops the currently playing background sound
     * if there is one
     */
    public void resetBackgroundSound () {
        if (backgroundClip != null) {
            backgroundClip.stop();
        }
    }

    /**
     * Updates the sound manager state
     * this method shall be called by the game
     */
    public void update() {
        if (timeLeft < 0 && resetRequired) {
            resetSound();
        } else {
            timeLeft--;
        }
    }

    /**
     * Plays a sound that can overlap with other sounds
     * @param name the name of the sound to play
     */
    public void playOverlappingSound (String name) {
        SwingSound sound = readSwingSound(name);
        if (sound == null) return;

        Clip clip = sound.openedClip(0);
        if (clip != null) {
            clip.start();
        }
    }

}
