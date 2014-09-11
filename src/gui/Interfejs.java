package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Interfejs extends JFrame{
	public static JButton[][] _tiles;
	private static JPanel contentPane;
	
	
	public static void update(){
		for(int i=0;i<10;i++)
			for(int j=0;j<10;j++){
				switch (world[i][j]){
				case 0:
					_tiles[i][j].setBackground(Color.WHITE);
					_tiles[i][j].repaint();
					break;
				case 1:
					_tiles[i][j].setBackground(Color.BLACK);
					_tiles[i][j].repaint();
					break;				
				case 2:
					_tiles[i][j].setBackground(Color.RED);
					_tiles[i][j].repaint();
					break;
				case 3:
					_tiles[i][j].setBackground(Color.GREEN);
					_tiles[i][j].repaint();
					break;
				case 4:
					_tiles[i][j].setBackground(Color.YELLOW);
					_tiles[i][j].repaint();
					break;
				default:
					_tiles[i][j].setBackground(Color.BLUE);
					break;
				}
			}

	}
	public static int[][] world = new int[10][10];
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfejs frame = new Interfejs();
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
	public Interfejs() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 450, 470);
		JPanel gui = new JPanel(new BorderLayout());
		JPanel blocks = new JPanel(new GridLayout(10,10));
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		_tiles = new JButton[10][10];
		Insets buttonMargin = new Insets(0,0,0,0);
		JMenuBar bar = new JMenuBar();
		JButton clear = new JButton("Clear");
		clear.addActionListener(new clearScreen());
		JButton path = new JButton("Path");
		path.addActionListener(new getPath());
		bar.add(path);
		bar.add(clear);
		gui.add(bar,BorderLayout.NORTH);
		for(int j=0;j<10;j++)
			for(int i=0;i<10;i++){
				world[i][j]=0;
				JButton b = new JButton();
				b.setMargin(buttonMargin);
				ImageIcon icon = new ImageIcon(
						new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB));
				b.setIcon(icon);
				b.putClientProperty("row", j);
				b.putClientProperty("column", i);
				b.addActionListener(new changeColor());
				switch (world[i][j]){
				case 0:
					b.setBackground(Color.WHITE);
					break;
				case 1:
					b.setBackground(Color.BLACK);
					break;				
				case 2:
					b.setBackground(Color.RED);
					break;
				case 3:
					b.setBackground(Color.GREEN);
					break;
				case 4:
					b.setBackground(Color.YELLOW);
					break;
				default:
					b.setBackground(Color.BLUE);
					break;
				}
				_tiles[i][j]=b;
				blocks.add(_tiles[i][j]);
			}
		contentPane.add(gui,BorderLayout.NORTH);
		contentPane.add(blocks,BorderLayout.SOUTH);



	}
	class changeColor implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton) e.getSource();
			world[(int)btn.getClientProperty("column")][(int)btn.getClientProperty("row")]++;
			if(world[(int)btn.getClientProperty("column")][(int)btn.getClientProperty("row")] == 4)
				world[(int)btn.getClientProperty("column")][(int)btn.getClientProperty("row")]=0;
			Start main = new Start();
			main.drawWorld2();
			update();
		}
	}
	class clearScreen implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			for(int i=0;i<10;i++)
				for(int j=0;j<10;j++)
					world[i][j]=0;
			update();
			System.out.printf("\n\n\n\n\n\n");
		}
	}
	class getPath implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Start main = new Start();        
			Path m8 = new Path();
//			main.drawWorld2();
			int xs=1,ys=1,xe=9,ye=9;
			for(int i=0;i<10;i++)
				for(int j=0;j<10;j++){
					if(_tiles[i][j].getBackground()==Color.GREEN){
						xs = j;
						ys = i;
					}
					if(_tiles[i][j].getBackground()==Color.RED){
						xe = j;
						ye = i;
					}
				}
			System.out.printf("xs = %d   ys = %d   xe = %d   ye = %d\n", xs,ys,xe,ye);
			try {
				m8 = main.findPath(xs,ys,xe,ye);
				m8.printPath();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			update();
		}
	}
}
