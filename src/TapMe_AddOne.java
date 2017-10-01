
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.AncestorListener;

public class TapMe_AddOne extends JPanel implements ActionListener {
	private static final int TILE_SIZE =64; // 框大小
	private static final int TILES_MARGIN = 12; // 間距
	
	JLabel l = new JLabel("", JLabel.CENTER); // 分數顯示
	JLabel h = new JLabel("", JLabel.CENTER);
	JLabel r = new JLabel("", JLabel.CENTER);
	JLabel panel = new JLabel();
	JButton[][] b = new JButton[5][5];
	JButton btnNewButton = new JButton();
	
	int[][] num = new int[5][5]; // 方塊值
	boolean myWin = false;
	boolean myLose = false;
	int myScore = 0; // 分數
	int hp = 5; // 生命
 
	ImageIcon[] image = new ImageIcon[20]; // 圖片
	ImageIcon[] life = new ImageIcon[6];
	ImageIcon Bak = new ImageIcon();
	ImageIcon f = new ImageIcon("fire.gif");
	public TapMe_AddOne() {
		
		for (int i = 0; i < 20; i++) {
			image[i] = new ImageIcon(i + ".png");
		}
		for (int i = 0; i < 6; i++) {
			life[i] = new ImageIcon(i + "life.png");
		}
		setLayout(null); // 排版:絕對位置
		

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				addTile(i, j);
			}
		}
		
		x(0, 0);

		myScore = 0;
		
		l.setText("" + myScore);
		l.setBounds(43, 13, 311, 38);
		
		l.setFont(new Font("Arial", Font.BOLD, 32));// 字體大小字形粗體
		
		add(l);
		hp=5;
		h.setBorder(null);
		h.setIcon(life[hp]);
		
		h.setBounds(43, 51, 320, 50);
		add(h);
		
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(num[j][i]);
			}
			System.out.println();
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				b[i][j] = new JButton();

				
				b[i][j].setBounds(TILE_SIZE * i + TILES_MARGIN * (i + 1), 100 + TILE_SIZE * j + TILES_MARGIN * (j + 1),
						TILE_SIZE, TILE_SIZE);// button的位置
				add(b[i][j]);
				b[i][j].setActionCommand(i + "" + j);
				b[i][j].addActionListener(this);
				b[i][j].setIcon(image[num[i][j]]);// button的顏色
				b[i][j].setBorderPainted(false); // button去邊
				b[i][j].setBorder(null);
				b[i][j].setContentAreaFilled(false);
			}
		}
		
		Bak = new ImageIcon("Bak.jpg");
		panel.setIcon(Bak);
		panel.setBounds(0, 0, 400, 520);
		add(panel);
		
	}

	public void resetGame() {
		for(int i=0;i<5;i++)
			for(int j=0;j<5;j++){
				myScore+=num[i][j]*10;
			}
		l.setText("" + myScore);
		JOptionPane.showMessageDialog(null, "分數:"+myScore, "廢物輸了吼", JOptionPane.PLAIN_MESSAGE );
		System.exit(0);
	}
	@Override
	public void actionPerformed(ActionEvent e) { // 事件
		String str = e.getActionCommand();
		int i = Character.getNumericValue(str.charAt(0)), j = Character.getNumericValue(str.charAt(1));
		hp -= 1;
		h.setIcon(life[hp]);
		num[i][j] += 1;
		
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				System.out.print(num[y][x]);
			}
			System.out.println();
		}
		System.out.println();
		t(i, j);
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				System.out.print(num[y][x]);
			}
			System.out.println();
		}
		x(0, 0);
		
		if(hp==0){
			resetGame();
		}
		

		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				b[x][y].setIcon(image[num[x][y]]);
			}
		}

		System.out.println("i" + i + "j" + j);

	}

	int sum = 1; // 相鄰個數
	

	void x(int i, int j) {
		if (y(i, j) && num[i][j] != 0) {
			
			t(i, j);
			
			x(0, 0);
		} else {
			if (i + 1 < 5)
				x(i + 1, j);
			else if (j + 1 < 5)
				x(0, j + 1);
		}
	}

	boolean y(int i, int j) {
		int n = num[i][j];

		if (i - 1 >= 0)
			checking_left(n, i, j); // 檢查有幾個相鄰
		if (i + 1 < 5)
			checking_right(n, i, j);
		if (j - 1 >= 0)
			checking_up(n, i, j);
		if (j + 1 < 5)
			checking_down(n, i, j);

		if (sum > 2) {
			sum = 1;
			return true; // 有兩個相鄰以上傳回true
		}

		sum = 1;
		return false;
	}

	void t(int i, int j) {

		int n = num[i][j];

		if (i - 1 >= 0)
			checking_left(n, i, j); // 檢查有幾個相鄰
		if (i + 1 < 5)
			checking_right(n, i, j);
		if (j - 1 >= 0)
			checking_up(n, i, j);
		if (j + 1 < 5)
			checking_down(n, i, j);

		if (sum > 2) {
			// 有三個相鄰以上才做消除的動作
			sum = 1;
			if (i - 1 >= 0)
				left(n, i, j);
			if (i + 1 < 5)
				right(n, i, j);
			if (j - 1 >= 0)
				up(n, i, j);
			if (j + 1 < 5)
				down(n, i, j);
			num[i][j] = n + 1;
			myScore += sum * (num[i][j]) * 10;
			l.setText("" + myScore);
			dropTile();
			hp++;
			if(hp>=5)
				hp=5;
			h.setIcon(life[hp]);
			
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					addTile(x, y);
				}
			}
		}

		sum = 1;
	}

	void checking_left(int n, int i, int j) { // 檢查左
		if (n == num[i - 1][j] && sum < 3) {
			sum += 1;
			if (i - 2 >= 0)
				checking_left(n, i - 1, j);
			if (j - 1 >= 0)
				checking_up(n, i - 1, j);
			if (j + 1 < 5)
				checking_down(n, i - 1, j);
		}
	}

	void checking_right(int n, int i, int j) { // 檢查右
		if (n == num[i + 1][j] && sum < 3) {
			sum += 1;
			if (i + 2 < 5)
				checking_right(n, i + 1, j);
			if (j - 1 >= 0)
				checking_up(n, i + 1, j);
			if (j + 1 < 5)
				checking_down(n, i + 1, j);
		}
	}

	void checking_up(int n, int i, int j) { // 檢查上
		if (n == num[i][j - 1] && sum < 3) {
			sum += 1;
			if (i - 1 >= 0)
				checking_left(n, i, j - 1);
			if (i + 1 < 5)
				checking_right(n, i, j - 1);
			if (j - 2 >= 0)
				checking_up(n, i, j - 1);
		}
	}

	void checking_down(int n, int i, int j) { // 檢查下
		if (n == num[i][j + 1] && sum < 3) {
			sum += 1;
			if (i - 1 >= 0)
				checking_left(n, i, j + 1);
			if (i + 1 < 5)
				checking_right(n, i, j + 1);
			if (j + 2 < 5)
				checking_down(n, i, j + 1);
		}
	}

	void left(int n, int i, int j) { // 左
		if (n == num[i - 1][j]) {
			num[i - 1][j] = 0;
			sum += 1;
			if (i - 2 >= 0)
				left(n, i - 1, j);
			if (j - 1 >= 0)
				up(n, i - 1, j);
			if (j + 1 < 5)
				down(n, i - 1, j);
		}
	}

	void right(int n, int i, int j) { // 右
		if (n == num[i + 1][j]) {
			num[i + 1][j] = 0;
			sum += 1;
			if (i + 2 < 5)
				right(n, i + 1, j);
			if (j - 1 >= 0)
				up(n, i + 1, j);
			if (j + 1 < 5)
				down(n, i + 1, j);
		}
	}

	void up(int n, int i, int j) { // 上
		if (n == num[i][j - 1]) {
			num[i][j - 1] = 0;
			sum += 1;
			if (i - 1 >= 0)
				left(n, i, j - 1);
			if (i + 1 < 5)
				right(n, i, j - 1);
			if (j - 2 >= 0)
				up(n, i, j - 1);
		}
	}

	void down(int n, int i, int j) { // 下
		if (n == num[i][j + 1]) {
			num[i][j + 1] = 0;
			sum += 1;
			if (i - 1 >= 0)
				left(n, i, j + 1);
			if (i + 1 < 5)
				right(n, i, j + 1);
			if (j + 2 < 5)
				down(n, i, j + 1);
		}
	}

	public void dropTile() { // 方塊落下

		int temp = 0;
		for (int i = 0; i < 5; i++) {
			for (int y = 0; y < 4; y++) {
				for (int j = 1; j < 5 - y; j++)
					if (num[i][j] == 0) {
						temp = num[i][j];
						num[i][j] = num[i][j - 1];
						num[i][j - 1] = temp;
					}
			}
		}
	}

	public void addTile(int i, int j) { // 增加新的方塊
		if (num[i][j] == 0)
			num[i][j] = (int) (Math.random() * 5 + 1);
	}

	public static void main(String[] args) {
		JFrame game = new JFrame();
		game.setTitle("Tap Me+1");
		game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		game.setSize(400, 520);
		game.setResizable(false);

		game.getContentPane().add(new TapMe_AddOne());

		game.setLocationRelativeTo(null);
		game.setVisible(true);
	}
}
