package de.hebkstudents.recipemanager.utils;

import de.hebkstudents.recipemanager.exception.InvalidAudioFileException;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Class used to play wav audio files
 */
public class AudioPlayer {

    /**
     * System audio line clip
     */
    private final Clip clip;

    /**
     * Creates a new AudioPlayer object
     * @param audioFile Initial audio file-object
     * @throws InvalidAudioFileException If the given audio file-object is invalid
     * @throws LineUnavailableException If system has no audio-line available. (-> If no audio device is connected)
     */
    public AudioPlayer(File audioFile) throws InvalidAudioFileException, LineUnavailableException {

        if (audioFile == null || !audioFile.exists()) {
            throw new InvalidAudioFileException("The given audio file is invalid!");
        }
        this.clip = AudioSystem.getClip();
        setAudioFile(audioFile);
    }

    /**
     * Creates a new AudioPlayer object
     * @param audioFile Initial audio file-object
     * @param autoplay Defines if audio-file should be played after object initialization
     * @throws InvalidAudioFileException If the given audio file-object is invalid
     * @throws LineUnavailableException If system has no audio-line available. (-> If no audio device is connected)
     */
    public AudioPlayer(File audioFile, boolean autoplay) throws InvalidAudioFileException, LineUnavailableException {

        if (audioFile == null || !audioFile.exists()) {
            throw new InvalidAudioFileException("The given audio file is invalid!");
        }
        this.clip = AudioSystem.getClip();
        setAudioFile(audioFile);
        if (autoplay) start();
    }

    /**
     * Sets a new audio-file (-> stops the current play)
     * @param audioFile Valid audio file-object
     * @throws InvalidAudioFileException If the given audio file-object is invalid
     */
    public void setAudioFile(File audioFile) throws InvalidAudioFileException {
        if (audioFile == null || !audioFile.exists()) {
            throw new InvalidAudioFileException("The given audio file is invalid!");
        }
        try {
            clip.stop();
            clip.open(AudioSystem.getAudioInputStream(audioFile.toURI().toURL()));
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new InvalidAudioFileException("The given audio file is invalid!");
        }
    }

    /**
     * Starts playing the audio
     */
    public void start()
    {
        clip.start();
    }

    /**
     * Stops playing the audio
     */
    public void stop()
    {
        clip.stop();
    }

    /**
     * Check if the audio-file is running in clip
     * @return true if it is running
     */
    public boolean isRunning()
    {
        return clip.isRunning();
    }

    /**
     * Sets the current playing volume
     * @param volume Float value between 0 - 1
     */
    public void setVolume(float volume)
    {
        if (volume < 0f || volume > 1f) {
            throw new IllegalArgumentException("Volume not valid: " + volume);
        }
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    /**
     * Adds a listener to this line. Whenever the line's status changes, the
     * listener's {@code update()} method is called with a {@code LineEvent}
     * object that describes the change.
     *
     * @param listener listener to add
     */
    public void addLineListener(LineListener listener)
    {
        clip.addLineListener(listener);
    }


    /**
     * Plays a positive notification sound.
     * If AudioSystem is not available a simple beep is tried to be played
     */
    public static void playPositiveNotification()
    {
        new Thread(() -> {
            try {
                new AudioPlayer(new File("resources/sounds/notifications/positive-notification.wav")).start();
            } catch (InvalidAudioFileException | LineUnavailableException e) {
                Toolkit.getDefaultToolkit().beep();
                Logger.log(LogType.WARNING, "An Exception was thrown while trying to play notification sound! Playing system beep instead.");
                Logger.logException(e);
            }
        }).start();
    }
}
