package java_music_player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Music extends Thread  {
	private File song = null; 
	private boolean playing = false; 
	private int duration;
	public Music() {
		// TODO Auto-generated constructor stub
	}
	
	public void run()
	{
        try(FileInputStream fis=new FileInputStream(this.song)){
        	 BufferedInputStream bis = new BufferedInputStream(fis);
            Player player=new Player(bis);
            player.play();
            
            AudioFile audioFile = AudioFileIO.read(this.song);
            setDuration(audioFile.getAudioHeader().getTrackLength());
        }catch (JavaLayerException e1) {
        	System.out.println( "No es un fichero de audio");
        }catch (IOException ex) {
        	System.out.println("");
        } catch (CannotReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAudioFrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public File getSong() {
		return song;
	}

	public void setSong(File song) {
		this.song = song;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
