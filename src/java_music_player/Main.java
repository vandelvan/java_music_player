package java_music_player;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	private JPanel contentPane;
	private Music player = new Music();
	private int songIdx;
	private JButton btnNext = new JButton("»");
	private JButton btnPrev = new JButton("«");
	private JList songQueue;
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
		
		JButton btnPlay = new JButton("\u25B6\uFE0F");
		
		panel.add(btnPlay, BorderLayout.CENTER);
		
		btnPrev.setEnabled(false);
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				songIdx--;
				player.stop();
				player = null;
				player = new Music();
				songQueue.setSelectedIndex(songIdx);
				player.setSong((File)songQueue.getSelectedValue());
				player.start();
				player.setPlaying(true);
				btnPlay.setText("||");
				checkBounds();
			}
		});
		panel.add(btnPrev, BorderLayout.WEST);
		
		btnNext.setEnabled(false);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				songIdx++;
				player.stop();
				player = null;
				player = new Music();
				songQueue.setSelectedIndex(songIdx);
				player.setSong((File)songQueue.getSelectedValue());
				player.start();
				player.setPlaying(true);
				btnPlay.setText("||");
				checkBounds();
			}
		});
		panel.add(btnNext, BorderLayout.EAST);
		
		JProgressBar songProgressBar = new JProgressBar();
		panel.add(songProgressBar, BorderLayout.NORTH);
		
		JLabel lblCancionesEnCola = new JLabel("Canciones en cola:");
		contentPane.add(lblCancionesEnCola, BorderLayout.WEST);
		
		JLabel lblReproductorDeMusica = new JLabel("Reproductor de musica");
		contentPane.add(lblReproductorDeMusica, BorderLayout.NORTH);
		
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int sidx = songQueue.getSelectedIndex();
				if((!player.isPlaying() || songIdx != sidx) && sidx != -1)
				{
					if(songIdx != sidx) 
					{
						player.stop();
						player = null;
						player = new Music();
						songIdx = sidx;
					}
					player.setSong((File)songQueue.getSelectedValue());
					player.start();
					player.setPlaying(true);
					btnPlay.setText("||");
				}
				else
				{
					player.stop();
					player = null;
					player = new Music();
					btnPlay.setText("\u25B6\uFE0F");
				}
				checkBounds();
			}
		});
	}
	
	private void checkBounds()
	{

		if(songIdx == songQueue.getModel().getSize()-1)
		{
			btnNext.setEnabled(false);
		}
		else
		{
			btnNext.setEnabled(true);
		}
		if(songIdx == 0)
		{
			btnPrev.setEnabled(false);
		}
		else
		{
			btnPrev.setEnabled(true);
		}
	}

}
