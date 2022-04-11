package java_music_player;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JProgressBar songProgressBar = new JProgressBar();
	private static MusicUI player = new MusicUI(songProgressBar);
	private static Song song = new Song();
	private static int songIdx;
	private static JButton btnNext = new JButton("»");
	private static JButton btnPrev = new JButton("«");
	private static JList songQueue;
	private static JButton btnPlay = new JButton("\u25B6\uFE0F");
	private static int pos = 0;
	private final JButton btnShuffle = new JButton("Shuffle");
	private static boolean shuffle = false;
	private static int lastSongIdx = -1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		File s = new File("/home/vandelvan/Music");
		File[] songList = s.listFiles();
		
		songQueue = new JList(songList);
		contentPane.add(songQueue, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		panel.add(btnPlay, BorderLayout.CENTER);
		
		btnPrev.setEnabled(false);
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				moveQueue(false);
			}
		});
		panel.add(btnPrev, BorderLayout.WEST);
		
		btnNext.setEnabled(false);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				moveQueue(true);
			}
		});
		panel.add(btnNext, BorderLayout.EAST);
		
		panel.add(songProgressBar, BorderLayout.NORTH);
		btnShuffle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				shuffle = !shuffle;
				if(shuffle)
				{
					btnShuffle.setText("Desactivar shuffle");
				}
				else
				{
					btnShuffle.setText("Shuffle");					
				}
			}
		});
		
		panel.add(btnShuffle, BorderLayout.SOUTH);
		
		JLabel lblCancionesEnCola = new JLabel("Canciones en cola:");
		contentPane.add(lblCancionesEnCola, BorderLayout.WEST);
		
		JLabel lblReproductorDeMusica = new JLabel("Reproductor de musica");
		contentPane.add(lblReproductorDeMusica, BorderLayout.NORTH);
		
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int sidx = songQueue.getSelectedIndex();
				if((!song.isPlaying() || songIdx != sidx) && sidx != -1)
				{
					if(songIdx != sidx) 
					{
						pos = 0;
						player.stop();
						song.stop();
						player = null;
						song = null;
						player = new MusicUI(songProgressBar);
						song = new Song();
						lastSongIdx = songIdx;
						songIdx = sidx;
					}
					player.setSong((File)songQueue.getSelectedValue());
					song.setSong((File)songQueue.getSelectedValue());
					player.setPos(pos);
					player.start();
					song.setPlaying(true);
					song.setPos(pos);
					song.start();
					btnPlay.setText("||");
				}
				else
				{
					player.stop();
					song.stop();
					player = null;
					song = null;
					player = new MusicUI(songProgressBar);
					song = new Song();
					btnPlay.setText("\u25B6\uFE0F");
					pos = songProgressBar.getValue();
				}
				checkBounds();
			}
		});
	}
	
	private static void checkBounds()
	{

		if(songIdx == songQueue.getModel().getSize()-1 && !shuffle)
		{
			btnNext.setEnabled(false);
		}
		else
		{
			btnNext.setEnabled(true);
		}
		if(songIdx == 0 && lastSongIdx == -1)
		{
			btnPrev.setEnabled(false);
		}
		else
		{
			btnPrev.setEnabled(true);
		}
	}
	
	public static void moveQueue(boolean next)
	{
		if((songIdx == songQueue.getModel().getSize()-1 && next && !shuffle) || (songIdx == 0 && !next && lastSongIdx == -1))
		{
			pos = 0;
			player.stop();
			song.stop();
			player = null;
			song = null;
			player = new MusicUI(songProgressBar);
			song = new Song();
			btnPlay.setText("\u25B6\uFE0F");
			return;
		}
		if(next)
		{
			lastSongIdx = songIdx;
			if(shuffle)
			{
				Random rand = new Random();
				int auxIdx = songIdx;
				while(auxIdx == songIdx)
				{
					auxIdx = rand.nextInt(songQueue.getModel().getSize());
				}
				songIdx = auxIdx;
			}
			else
			{
				songIdx++;
			}
		}
		else
		{
			songIdx = lastSongIdx == -1 ? songIdx-1 : lastSongIdx;
		}
		pos = 0;
		player.stop();
		song.stop();
		player = null;
		song = null;
		player = new MusicUI(songProgressBar);
		song = new Song();
		songQueue.setSelectedIndex(songIdx);
		player.setSong((File)songQueue.getSelectedValue());
		song.setSong((File)songQueue.getSelectedValue());
		player.start();
		song.setPlaying(true);
		song.start();
		btnPlay.setText("||");
		checkBounds();
	}

}
