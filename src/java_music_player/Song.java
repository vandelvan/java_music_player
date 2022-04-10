package java_music_player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class Song extends Thread{
	private File song = null;
	private boolean playing = false;
	private int pos;
	public Song() {
	}
	public void run()
	{
        try(FileInputStream fis=new FileInputStream(this.getSong())){
        	BufferedInputStream bis = new BufferedInputStream(fis);
        	AdvancedPlayer player=new AdvancedPlayer(bis);
            player.play(pos,Integer.MAX_VALUE);
        }catch (JavaLayerException e1) {
        	System.out.println( "No es un fichero de audio");
        }catch (IOException ex) {
        	System.out.println("");
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
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
}