/*
 * 19133474 문지원
 * 텀 프로젝트
 * 팩맨 게임 프로그램
 * pacman과 enemy객체를 생성해 pacman이 맵에 있는 dot들을 먹으며
 * 그 도트가 없다면 승리 아이콘을 보이게 하고 프로그램을 종료
 * 만약 pacman이 enemy와 부딪히면 pacman이 죽고 프로그램이 종료된다.
 */

package PM;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
 
public class PacMan {
	public static void main(String[] args) {
		final JFrame frame = new JFrame();

		//사진파일을 저장하는 ImageIcon클래스
		final ImageIcon successIcon = new ImageIcon("successIcon.png");
		final ImageIcon failIcon = new ImageIcon("failIcon.png");
		final ImageIcon smallDot = new ImageIcon("smallDot.png");
		final ImageIcon bigDot = new ImageIcon("bigDot.png");
		final ImageIcon wall = new ImageIcon("wall.png");
		final ImageIcon enemy = new ImageIcon("enemy.png");
		final ImageIcon pacman = new ImageIcon("pacman.png");
		final ImageIcon empty = new ImageIcon("empty.png");

		//성공, 실패시 출력하는 사진파일을 출력하는 JButton객체
		final JButton success = new JButton(successIcon);
		final JButton fail = new JButton(failIcon);
		final CardLayout card = new CardLayout();
		final JDialog dialog = new JDialog();

		//랜덤변수 객체를 선언
		random = new Random();
		dialog.setSize(690, 650);
		dialog.setVisible(false);

		//최초 팩맨과 적의 위치를 지정
		pmH = 12;
		pmW = 7;
		enemyH = 7;
		enemyW = 7;
		numOfDot = 79;
		start = 2;
		temp = empty;

		//JLabel의 크기를 배열로 두고 14*14만큼 설정
		final JLabel[][] f = new JLabel[14][14];
		for (int i = 0; i < 14; i++) {
			for (int j = 0; j < 14; j++) {
				f[i][j] = new JLabel();
			}
		}

		//승리 및 패배시 상속하는 메소드이고  프로그램을 종료한다.
		class Blistener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		}

		//timer클래스 ActionListener
		class TListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				//시작 위치가 0이하라면 where값을 1+(0~3)으로 지정한다.
				//0 이하의 수가 되면 밑의 switch문이 안돌아가기 때문
				if (start <= 0)
					where = 1 + random.nextInt(4);
				else {
					where = 1;
					start--;
				}
				
				//위의 if문의 숫자를 내려받아 돌아가는 switch문.
				//enemy의 움직임에 관여하며, pacman과는 달리 Dot를 먹지 않도록 지정
				switch (where) {
				case 1:
					if (!(f[enemyH - 1][enemyW].getIcon()).equals(wall)) {
						temp1 = f[enemyH - 1][enemyW].getIcon();
						f[enemyH - 1][enemyW].setIcon(enemy);
						f[enemyH][enemyW].setIcon(temp);
						temp = temp1;
						enemyH--;
					}
					break;
				case 2:
					if (!(f[enemyH + 1][enemyW].getIcon()).equals(wall)) {
						temp2 = f[enemyH + 1][enemyW].getIcon();
						f[enemyH + 1][enemyW].setIcon(enemy);
						f[enemyH][enemyW].setIcon(temp);
						temp = temp2;
						enemyH++;
					}
					break;
				case 3:
					if (!(f[enemyH][enemyW - 1].getIcon()).equals(wall)) {
						temp3 = f[enemyH][enemyW - 1].getIcon();
						f[enemyH][enemyW - 1].setIcon(enemy);
						f[enemyH][enemyW].setIcon(temp);
						temp = temp3;
						enemyW--;
					}
					break;
				case 4:
					if (!(f[enemyH][enemyW + 1].getIcon()).equals(wall)) {
						temp4 = f[enemyH][enemyW + 1].getIcon();
						f[enemyH][enemyW + 1].setIcon(enemy);
						f[enemyH][enemyW].setIcon(temp);
						temp = temp4;
						enemyW++;
					}
					break;
				}
				if (enemyH == pmH && enemyW == pmW) {
					f[enemyH][enemyW].setIcon(enemy);
					dialog.add(fail);
					dialog.setVisible(true);
				}

				if (enemyH == 5 && enemyW == 7) {
					f[6][7].setIcon(wall);
				}
				System.out.println(where);
			}
		}
		
		// 키보드입력에 따른 KeyListener
		class KListener extends KeyAdapter {
			public void keyPressed(KeyEvent e) {
				
				//dot의 갯수가 0이 된다면 승리 이미지icon을 출력하며 프로그램을 종료한다.
				if (numOfDot <= 0) {
					dialog.add(success);
					dialog.setVisible(true);
				}
				
				//키보드 입력을 받으면 pacman이 위 아래 좌 우로 이동하며 만약 dot가 있다면 그것을 먹고 numOfDot의 갯수가 줄어든다.
				int key1 = e.getKeyCode();
				switch (key1) {
				case KeyEvent.VK_UP:
					if ((f[pmH - 1][pmW].getIcon()).equals(smallDot) || (f[pmH - 1][pmW].getIcon()).equals(empty)) {
						if ((f[pmH - 1][pmW].getIcon()).equals(smallDot) && (f[pmH][pmW].getIcon()).equals(pacman))
							numOfDot--;
						f[pmH - 1][pmW].setIcon(pacman);
						f[pmH][pmW].setIcon(empty);
						pmH--;
					} else if ((f[pmH - 1][pmW].getIcon()).equals(bigDot) || (f[pmH - 1][pmW].getIcon()).equals(empty)) {
						if ((f[pmH - 1][pmW].getIcon()).equals(bigDot) && (f[pmH][pmW].getIcon()).equals(pacman))
							numOfDot--;
						f[pmH - 1][pmW].setIcon(pacman);
						f[pmH][pmW].setIcon(empty);
						pmH--;
					} else if ((f[pmH - 1][pmW].getIcon()).equals(enemy)) {
						f[enemyH][enemyW].setIcon(enemy);
						dialog.add(fail);
						dialog.setVisible(true);
					}
					break;
				case KeyEvent.VK_DOWN:
					if ((f[pmH + 1][pmW].getIcon()).equals(smallDot) || (f[pmH + 1][pmW].getIcon()).equals(empty)) {
						if ((f[pmH + 1][pmW].getIcon()).equals(smallDot) && (f[pmH][pmW].getIcon()).equals(pacman))
							numOfDot--;
						f[pmH + 1][pmW].setIcon(pacman);
						f[pmH][pmW].setIcon(empty);
						pmH++;
					} else if ((f[pmH + 1][pmW].getIcon()).equals(bigDot) || (f[pmH + 1][pmW].getIcon()).equals(empty)) {
						if ((f[pmH + 1][pmW].getIcon()).equals(bigDot) && (f[pmH][pmW].getIcon()).equals(pacman))
							numOfDot--;
						f[pmH + 1][pmW].setIcon(pacman);
						f[pmH][pmW].setIcon(empty);
						pmH++;
					} else if ((f[pmH + 1][pmW].getIcon()).equals(enemy)) {
						f[enemyH][enemyW].setIcon(enemy);
						dialog.add(fail);
						dialog.setVisible(true);
					}
					break;
				case KeyEvent.VK_LEFT:
					if ((f[pmH][pmW - 1].getIcon()).equals(smallDot) || (f[pmH][pmW - 1].getIcon()).equals(empty)) {
						if ((f[pmH][pmW - 1].getIcon()).equals(smallDot) && (f[pmH][pmW].getIcon()).equals(pacman))
							numOfDot--;
						f[pmH][pmW - 1].setIcon(pacman);
						f[pmH][pmW].setIcon(empty);
						pmW--;
					} else if ((f[pmH][pmW - 1].getIcon()).equals(bigDot) || (f[pmH][pmW - 1].getIcon()).equals(empty)) {
						if ((f[pmH][pmW - 1].getIcon()).equals(bigDot) && (f[pmH][pmW].getIcon()).equals(pacman))
							numOfDot--;
						f[pmH][pmW - 1].setIcon(pacman);
						f[pmH][pmW].setIcon(empty);
						pmW--;
					} else if ((f[pmH][pmW - 1].getIcon()).equals(enemy)) {
						f[enemyH][enemyW].setIcon(enemy);
						dialog.add(fail);
						dialog.setVisible(true);
					}
					break;
				case KeyEvent.VK_RIGHT:
					if ((f[pmH][pmW + 1].getIcon()).equals(smallDot) || (f[pmH][pmW + 1].getIcon()).equals(empty)) {
						if ((f[pmH][pmW + 1].getIcon()).equals(smallDot) && (f[pmH][pmW].getIcon()).equals(pacman))
							numOfDot--;
						f[pmH][pmW + 1].setIcon(pacman);
						f[pmH][pmW].setIcon(empty);
						pmW++;
					} else if ((f[pmH][pmW + 1].getIcon()).equals(bigDot) || (f[pmH][pmW + 1].getIcon()).equals(empty)) {
						if ((f[pmH][pmW + 1].getIcon()).equals(bigDot) && (f[pmH][pmW].getIcon()).equals(pacman))
							numOfDot--;
						f[pmH][pmW + 1].setIcon(pacman);
						f[pmH][pmW].setIcon(empty);
						pmW++;
					} else if ((f[pmH][pmW + 1].getIcon()).equals(enemy)) {
						f[enemyH][enemyW].setIcon(enemy);
						dialog.add(fail);
						dialog.setVisible(true);
					}
					break;
				}
				System.out.println(numOfDot);
			}
		}
		
		//키보드 입력시 이벤트 발생을 위한 KLisener, TListener클래스
		KListener listener = new KListener();
		TListener tListener = new TListener();
		success.addActionListener(new Blistener());
		fail.addActionListener(new Blistener());
		Timer t = new Timer(500, tListener);
		t.start();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(14, 14));
		frame.requestFocus();
		frame.addKeyListener(new KListener());

		//기본 JPanel을 14*14크기로 하고 그것을 wall아이콘으로 채워넣는다.
		for (int i = 0; i < 14; i++) {
			for (int j = 0; j < 14; j++) {
				f[i][j].setIcon(wall);
				f[i][j].addKeyListener(listener);
				panel.add(f[i][j]);
			}
		}
		
		//wall아이콘으로 채워진 JPanel의 원하는 위치에 smallDot, bigDot로 채워넣는다.
		f[1][1].setIcon(smallDot);	f[2][1].setIcon(smallDot);	f[3][1].setIcon(smallDot);	f[4][1].setIcon(smallDot);	f[5][1].setIcon(smallDot);
		f[5][2].setIcon(bigDot);	f[5][3].setIcon(smallDot);	f[1][3].setIcon(smallDot);	f[2][3].setIcon(smallDot);	f[3][3].setIcon(smallDot);
		f[4][3].setIcon(smallDot);	f[1][4].setIcon(smallDot);	f[1][5].setIcon(smallDot);	f[1][6].setIcon(smallDot);	f[1][7].setIcon(smallDot);
		f[1][8].setIcon(smallDot);	f[1][9].setIcon(smallDot);	f[1][10].setIcon(smallDot);	f[1][11].setIcon(smallDot);	f[1][12].setIcon(smallDot);
		f[2][9].setIcon(smallDot);	f[2][12].setIcon(smallDot);	f[3][12].setIcon(smallDot);	f[4][12].setIcon(smallDot);	f[5][12].setIcon(smallDot);
		f[3][4].setIcon(smallDot);	f[3][5].setIcon(smallDot);	f[3][10].setIcon(smallDot);	f[3][11].setIcon(smallDot);
		f[4][5].setIcon(smallDot);	f[4][6].setIcon(smallDot);	f[4][7].setIcon(smallDot);	f[4][8].setIcon(smallDot);	f[4][9].setIcon(smallDot);
		f[4][10].setIcon(smallDot);	f[2][7].setIcon(smallDot);	f[3][7].setIcon(smallDot);	f[4][10].setIcon(smallDot);	f[4][11].setIcon(bigDot);
		f[5][11].setIcon(smallDot);	f[6][11].setIcon(smallDot);	f[7][11].setIcon(smallDot);	f[7][12].setIcon(smallDot);	f[8][12].setIcon(smallDot);
		f[9][12].setIcon(smallDot);	f[10][12].setIcon(smallDot);f[11][12].setIcon(smallDot);f[5][7].setIcon(empty);		f[6][2].setIcon(smallDot);
		f[7][1].setIcon(smallDot);	f[9][11].setIcon(bigDot);	f[11][1].setIcon(bigDot);	f[11][3].setIcon(smallDot);	f[11][11].setIcon(smallDot);
		f[7][2].setIcon(smallDot);	f[7][3].setIcon(smallDot);	f[8][1].setIcon(smallDot);	f[9][1].setIcon(smallDot);	f[9][2].setIcon(smallDot);
		f[9][3].setIcon(smallDot);	f[9][4].setIcon(smallDot);	f[9][5].setIcon(smallDot);	f[9][6].setIcon(smallDot);	f[12][1].setIcon(smallDot);
		f[12][2].setIcon(smallDot);	f[12][3].setIcon(smallDot);	f[12][4].setIcon(smallDot);	f[12][5].setIcon(smallDot);	f[12][9].setIcon(smallDot);
		f[12][10].setIcon(smallDot);f[12][11].setIcon(smallDot);f[10][5].setIcon(smallDot); f[11][5].setIcon(smallDot); f[10][6].setIcon(smallDot);
		f[10][7].setIcon(smallDot);	f[10][8].setIcon(smallDot); f[10][9].setIcon(smallDot); f[11][7].setIcon(smallDot); f[12][7].setIcon(pacman);
		f[9][8].setIcon(smallDot); 	f[9][9].setIcon(smallDot);	f[11][9].setIcon(smallDot); f[6][5].setIcon(empty);		f[6][6].setIcon(empty);
		f[6][7].setIcon(empty); 	f[6][8].setIcon(empty);		f[6][9].setIcon(empty); 	f[7][5].setIcon(empty);		f[7][6].setIcon(empty);
		f[7][7].setIcon(enemy); 	f[7][8].setIcon(empty);		f[7][9].setIcon(empty);

		//JFrame의 제목을 설정하고 x클릭시 프로그램이 종료되도록 지정.
		frame.add(panel);
		frame.setTitle("팩맨 게임");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}

	//위의 JFrame의 변수선언부
	private static Random random;
	private static int pmH, pmW, enemyH, enemyW, numOfDot, where, start;
	private static int fieldMin=0, fieldMax=13;
	private static Icon temp1, temp2, temp3, temp4, temp;
	private static final int FRAME_WIDTH = 690;
	private static final int FRAME_HEIGHT = 650;
}