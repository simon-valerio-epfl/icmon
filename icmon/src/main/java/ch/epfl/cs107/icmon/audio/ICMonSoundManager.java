package ch.epfl.cs107.icmon.audio;

import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.io.ResourcePath;
import ch.epfl.cs107.play.window.swing.SwingSound;

import javax.sound.sampled.Clip;
import java.io.InputStream;

public class ICMonSoundManager {
    private final FileSystem fileSystem;
    private Clip currentClip;
    private Clip backgroundClip;
    private String currentPlayingSound = "N/A";
    private int timeLeft = 0;
    private boolean isPlayingPrioritySound = false;

    public ICMonSoundManager (FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public void resetSound () {
        if (currentClip != null) {
            currentClip.stop();
            currentClip = null;
        }
        timeLeft = -1;
        isPlayingPrioritySound = false;
        currentPlayingSound = "N/A";
    }

    public void playSound (String name, int duration) {
        playSound(name, duration, false);
    }

    public void playSound (String name, int duration, boolean isPrioritySound) {
        try {
            InputStream inputStream = fileSystem.read(ResourcePath.getSound(name));
            SwingSound sound = new SwingSound(inputStream);

            // we reset the time left if the sound is already playing
            if (this.currentPlayingSound.equals(name)) {
                timeLeft = duration;
                return;
            }

            // if we play a different sound
            // first check if the current sound can be overtaken
            if (isPlayingPrioritySound && !isPrioritySound) {
                return;
            }

            resetSound();

            isPlayingPrioritySound = isPrioritySound;

            Clip clip = sound.openedClip(0);
            if (clip != null) {
                clip.start();

                // detect when file ends
                clip.addLineListener(event -> {
                    if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                        resetSound();
                    }
                });
            }
            this.currentPlayingSound = name;
            this.timeLeft = duration;
            this.currentClip = clip;

        } catch (Exception e) {
            System.out.println("Can not play sound... " + name);
        }
    }

    public void playBackgroundSound (String name) {
        try {
            InputStream inputStream = fileSystem.read(ResourcePath.getSound(name));
            SwingSound sound = new SwingSound(inputStream);

            Clip clip = sound.openedClip(0);
            if (clip != null) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            this.backgroundClip = clip;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can not play background sound... " + name);
        }
    }

    public void resetBackgroundSound () {
        if (backgroundClip != null) {
            backgroundClip.stop();
        }
    }

    public void update() {
        if (timeLeft < 0) {
            resetSound();
        } else {
            timeLeft--;
        }
    }

}