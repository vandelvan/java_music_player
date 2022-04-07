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
		
		JList songQueue = new JList(songList);
		contentPane.add(songQueue, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnPlay = new JButton("New button");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        try(FileInputStream fis=new FileInputStream((File)songQueue.getSelectedValue())){
		            Player player=new Player(fis);
		            player.play();
		        }catch (JavaLayerException e1) {
		        	System.out.println( "No es un fichero de audio");
		        }catch (IOException ex) {
		        	System.out.println("");
		        }
			}
		});
		panel.add(btnPlay, BorderLayout.CENTER);
		
		JButton btnPrev = new JButton("New button");
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel.add(btnPrev, BorderLayout.WEST);
		
		JButton btnNext = new JButton("New button");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel.add(btnNext, BorderLayout.EAST);
		
		JProgressBar songProgressBar = new JProgressBar();
		panel.add(songProgressBar, BorderLayout.NORTH);
		
		JLabel lblCancionesEnCola = new JLabel("Canciones en cola:");
		contentPane.add(lblCancionesEnCola, BorderLayout.WEST);
		
		JLabel lblReproductorDeMusica = new JLabel("Reproductor de musica");
		contentPane.add(lblReproductorDeMusica, BorderLayout.NORTH);
	}

}
