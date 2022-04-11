package java_music_player;

import java.io.File;
import java.io.IOException;

import javax.swing.JProgressBar;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public class MusicUI extends Thread  {
	private File song = null;
	private int pos = 0;
	private int duration;
	public JProgressBar progress;
	public MusicUI()
	{
		
	}
	public MusicUI(JProgressBar progress) {
		this.progress = progress;
	}
	
	public void run()
	{       
        AudioFile audioFile;
		try {
			audioFile = AudioFileIO.read(this.song);
	        duration = audioFile.getAudioHeader().getTrackLength();
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		progress.setMaximum(duration);
        while(pos < duration)
        {
        	try {
				Thread.sleep(1000);
				pos++;
				progress.setValue(pos);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        Main.moveQueue(false);
	}

	public File getSong() {
		return song;
	}

	public void setSong(File song) {
		this.song = song;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}

}