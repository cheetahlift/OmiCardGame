package omiClient;

import javax.swing.JFrame;

import omiServer.OpeMsg;
import omiServer.PointBand;
import omiServer.ServerMain;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import omiInterface.GameServer;
import omiInterface.Gamer;

import javax.swing.JPanel;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

public class GameClient extends JFrame{
	
	
	/**
	 * �ͻ�����Ϣ
	 * */
	String my_name = "gamer";
	int my_gamerID = -1;
	String serverAddr;
	Gamer gamer;
	GameServer server;
	ConnectAction connectAction = new ConnectAction();
	ConnectDlg dlg = new ConnectDlg(this);
	
	/**
	 * Ӳ����
	 * */
	static int me_coin = 4;
	static int left_coin = 4;
	static int right_coin = 4;
	
	/**
	 * id������
	 * */
	String left_name =	"player1";
	int left_gamerID = -1;
	
	String right_name = "player2";
	int right_gamerID = -1;
	
	/**
	 * ׼�����
	 * */
	int left_ready = 0;
	int right_ready = 0;
	int me_ready = 0;
	
	/**
	 * �ڼ���
	 * */
	int round = 0;
	
	/**
	 * ��ǰ���Ƶ���
	 * */
	int me_cardpoint = 0;
	int me_newPoint = 0;
	
	
	/**
	 * �������
	 * */
	JLabel[] roundLabel_ = new JLabel[7]; //��1....7�غ�
	JTextArea textArea;     //��Ϸ����
	JTextArea textHistory;  //��ʷ��¼
	JButton leftPicBtn;		//������ͷ��
	JButton rightPicBtn;	//�Ҳ����ͷ��
	JButton mePicBtn;		//�ҵ�ͷ��
	JLabel leftLabel;		//����������
	JLabel rightLabel;		//�Ҳ��������
	JLabel meLabel;			//�ҵ�����
	JLabel leftCoinLabel;	//���Ӳ������
	JLabel rightCoinLabel;	//�Ҳ�Ӳ������
	JLabel meCoinLabel;		//�ҵ�Ӳ������
	JButton leftThrowBtn;	//�������
	JButton rightThrowBtn;	//�Ҳ�����
	JLabel leftStatusLabel;	//���״̬����׼��/����ľ�/pass��
	JLabel rightStatusLabel;//�Ҳ�״̬
	JButton btnDrawBtn;		//���ư�ť
	JButton meThrowBtn;		//�ҵ�����
	JButton btnHandCard2;	//����2
	JButton btnHandCard;	//����1
	JButton btnOmiReq;		//������С�紨�ư�ť
	JButton btnJoin;		//����ľְ�ť
	JButton btnPass;		//PASS��ť
	JButton omiCardBtn;		//�м��С�紨��
	JButton btnReadyCancel;	//ȡ��׼����ť
	JButton btnReady;		//׼����ť
	JButton leftOpenCardBtn;//��࿪��ͼ��
	JButton rightOpenCardBtn;//�Ҳ࿪��ͼ��
	JLabel opeLabel;		//Ҫ����Ҳ���������

	
	
	
	/**
	 * �������
	 * */
	public GameClient() {
		
		super("OmiCard-�ͻ���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1012, 700);
		getContentPane().setLayout(null);
		
		setupMenu();//�˵�
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		
		
		textArea = new JTextArea();
		textArea.setText("");
		/**
		 * ÿλ��ҿ���4öӲ��
			һ�������߾���Ϸ����עΪ1öӲ�ң����һ
			�ֶ�עΪ2öӲ��
			���ӵ��Ӳ������߻�ʤ
			1.����һ��С�紨�Ʋ���ÿλ��ҷ�һ�ŵ���
			2.����ʼ��ҿ�ʼѡ����һ���Ʋ���������һ
			�Ż򷢿�һ���Ƴ�Ϊ�µ�С�紨��
			3.����ʼ��ҿ�ʼ�����Ƿ���һöӲ�Ҳ����
			��
			4.���ƣ�������С����ҿ��Լ���С�紨�Ƶ�
			����Ȼ��ȴ�С���������������ȥ����Ӳ
			�Ҳ���������ȥ��Ӧ��Ӳ��
		 * */
		textArea.append("ÿλ��ҿ���4öӲ��\n");
		textArea.append("һ�������߾���Ϸ����עΪ1öӲ�ң����һ\n");
		textArea.append("�ֶ�עΪ2öӲ��\n");
		textArea.append("���ӵ��Ӳ������߻�ʤ\n");
		textArea.append("1.����һ��С�紨�Ʋ���ÿλ��ҷ�һ�ŵ���\n");
		textArea.append("2.����ʼ��ҿ�ʼѡ����һ���Ʋ���������һ\n");
		textArea.append("�Ż򷢿�һ���Ƴ�Ϊ�µ�С�紨��\n");
		textArea.append("3.����ʼ��ҿ�ʼ�����Ƿ���һöӲ�Ҳ����\n");
		textArea.append("��\n");
		textArea.append("4.���ƣ�������С����ҿ��Լ���С�紨�Ƶ�\n");
		textArea.append("����Ȼ��ȴ�С���������������ȥ����Ӳ\n");
		textArea.append("�Ҳ���������ȥ��Ӧ��Ӳ��");
		
		textArea.setEditable(false);
		textArea.setBounds(761, 43, 218, 253);
		getContentPane().add(textArea);
		
		textHistory = new JTextArea();
		textHistory.setBounds(761, 347, 218, 269);
		getContentPane().add(textHistory);
		
		JLabel lblNewLabel = new JLabel("\u6E38\u620F\u89C4\u5219");
		lblNewLabel.setFont(new Font("����", Font.PLAIN, 17));
		lblNewLabel.setBounds(834, 15, 89, 21);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u8BB0\u5F55");
		lblNewLabel_1.setFont(new Font("����", Font.PLAIN, 17));
		lblNewLabel_1.setBounds(846, 311, 63, 21);
		getContentPane().add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(15, 15, 727, 601);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		roundLabel_[0] = new JLabel("\u7B2C1\u56DE\u5408>>");
		roundLabel_[0].setFont(new Font("����", Font.PLAIN, 17));
		roundLabel_[0].setEnabled(false);
		roundLabel_[0].setBounds(15, 15, 81, 35);
		panel.add(roundLabel_[0]);
		
		roundLabel_[1] = new JLabel("\u7B2C2\u56DE\u5408>>");
		roundLabel_[1].setFont(new Font("����", Font.PLAIN, 17));
		roundLabel_[1].setEnabled(false);
		roundLabel_[1].setBounds(106, 15, 81, 35);
		panel.add(roundLabel_[1]);
		
		roundLabel_[2] = new JLabel("\u7B2C3\u56DE\u5408>>");
		roundLabel_[2].setFont(new Font("����", Font.PLAIN, 17));
		roundLabel_[2].setEnabled(false);
		roundLabel_[2].setBounds(198, 15, 81, 35);
		panel.add(roundLabel_[2]);
		
		roundLabel_[3] = new JLabel("\u7B2C4\u56DE\u5408>>");
		roundLabel_[3].setFont(new Font("����", Font.PLAIN, 17));
		roundLabel_[3].setEnabled(false);
		roundLabel_[3].setBounds(294, 15, 81, 35);
		panel.add(roundLabel_[3]);
		
		roundLabel_[4] = new JLabel("\u7B2C5\u56DE\u5408>>");
		roundLabel_[4].setFont(new Font("����", Font.PLAIN, 17));
		roundLabel_[4].setEnabled(false);
		roundLabel_[4].setBounds(390, 15, 81, 35);
		panel.add(roundLabel_[4]);
		
		roundLabel_[5] = new JLabel("\u7B2C6\u56DE\u5408>>");
		roundLabel_[5].setFont(new Font("����", Font.PLAIN, 17));
		roundLabel_[5].setEnabled(false);
		roundLabel_[5].setBounds(486, 15, 81, 35);
		panel.add(roundLabel_[5]);
		
		roundLabel_[6] = new JLabel("\u7B2C7\u56DE\u5408");
		roundLabel_[6].setFont(new Font("����", Font.PLAIN, 17));
		roundLabel_[6].setEnabled(false);
		roundLabel_[6].setBounds(577, 15, 81, 35);
		panel.add(roundLabel_[6]);
		
		leftPicBtn = new JButton("");
		leftPicBtn.setBounds(15, 95, 123, 123);
		leftPicBtn.setIcon(backAddress(20,123,123));
		panel.add(leftPicBtn);
		
		rightPicBtn = new JButton("");
		rightPicBtn.setBounds(589, 95, 123, 123);
		rightPicBtn.setIcon(backAddress(20,123,123));
		panel.add(rightPicBtn);
		
		mePicBtn = new JButton("");
		mePicBtn.setBounds(15, 463, 123, 123);
		mePicBtn.setIcon(backAddress(20,123,123));
		panel.add(mePicBtn);
		
		leftLabel = new JLabel("player2");
		leftLabel.setBounds(15, 233, 123, 29);
		panel.add(leftLabel);
		
		rightLabel = new JLabel("player3");
		rightLabel.setBounds(589, 237, 123, 29);
		panel.add(rightLabel);
		
		meLabel = new JLabel("player1");
		meLabel.setBounds(153, 464, 123, 29);
		panel.add(meLabel);
		
		leftCoinLabel = new JLabel("\u786C\u5E01: ");
		leftCoinLabel.setBounds(15, 277, 81, 21);
		panel.add(leftCoinLabel);
		
		rightCoinLabel = new JLabel("\u786C\u5E01: ");
		rightCoinLabel.setBounds(589, 277, 81, 21);
		panel.add(rightCoinLabel);
		
		meCoinLabel = new JLabel("\u786C\u5E01: ");
		meCoinLabel.setBounds(153, 496, 81, 21);
		panel.add(meCoinLabel);
		
		leftThrowBtn = new JButton("");
		leftThrowBtn.setBounds(153, 130, 81, 117);
		leftThrowBtn.setVisible(false);
		leftThrowBtn.addActionListener(new LeftThrowBtnListener());//������
		panel.add(leftThrowBtn);
		
		rightThrowBtn = new JButton("");
		rightThrowBtn.setBounds(486, 130, 81, 117);
		rightThrowBtn.setVisible(false);
		rightThrowBtn.addActionListener(new RightThrowBtnListener ());//������
		panel.add(rightThrowBtn);
		
		leftStatusLabel = new JLabel("\u72B6\u6001");
		leftStatusLabel.setBounds(153, 94, 126, 21);
		panel.add(leftStatusLabel);
		
		rightStatusLabel = new JLabel("\u72B6\u6001");
		rightStatusLabel.setBounds(483, 95, 101, 21);
		panel.add(rightStatusLabel);
		
		btnDrawBtn = new JButton("\u6478\u724C");
		btnDrawBtn.setToolTipText("");
		btnDrawBtn.setBounds(419, 387, 123, 53);
		btnDrawBtn.setVisible(false);
		btnDrawBtn.addActionListener(new BtnDrawBtnListener());
		panel.add(btnDrawBtn);
		
		meThrowBtn = new JButton("");
		meThrowBtn.setBounds(106, 331, 81, 117);
		meThrowBtn.setVisible(false);
		panel.add(meThrowBtn);
		
		btnHandCard2 = new JButton("");
		btnHandCard2.setBounds(271, 444, 93, 142);
		btnHandCard2.addActionListener(new BtnHandCard2Listener());
		btnHandCard2.setVisible(false);
		panel.add(btnHandCard2);
		
		btnHandCard = new JButton("");
		btnHandCard.setBounds(390, 444, 93, 142);
		btnHandCard.addActionListener(new BtnHandCardListener());
		btnHandCard.setVisible(false);
		panel.add(btnHandCard);
		
		btnOmiReq = new JButton("\u65B0\u5C0F\u65E9\u5DDD\u724C");
		btnOmiReq.setToolTipText("");
		btnOmiReq.setBounds(262, 387, 123, 53);
		btnOmiReq.addActionListener(new BtnOmiReqListener());
		btnOmiReq.setVisible(false);
		panel.add(btnOmiReq);
		
		btnJoin = new JButton("\u52A0\u5165\u8D4C\u5C40");
		btnJoin.setToolTipText("");
		btnJoin.setBounds(252, 378, 123, 53);
		btnJoin.setVisible(false);
		btnJoin.addActionListener(new BtnJoinListener());
		panel.add(btnJoin);
		
		btnPass = new JButton("PASS");
		btnPass.setToolTipText("");
		btnPass.setBounds(399, 378, 123, 53);
		btnPass.setVisible(false);
		btnPass.addActionListener(new BtnPassListener());
		panel.add(btnPass);
		
		omiCardBtn = new JButton();
		omiCardBtn.setBounds(280, 151, 123, 163);
		
		
		omiCardBtn.setIcon(backAddress(0,123,163));
		panel.add(omiCardBtn);
		
		JLabel lblNewLabel_5 = new JLabel("\u5C0F\u65E9\u5DDD\u724C");
		lblNewLabel_5.setFont(new Font("��������", Font.PLAIN, 16));
		lblNewLabel_5.setBounds(311, 130, 81, 21);
		panel.add(lblNewLabel_5);
		
		btnReadyCancel = new JButton("\u53D6\u6D88\u51C6\u5907");
		btnReadyCancel.setToolTipText("");
		btnReadyCancel.setBounds(238, 387, 123, 53);
		btnReadyCancel.addActionListener(new BtnReadyCancelListener());
		btnReadyCancel.setVisible(false);
		panel.add(btnReadyCancel);
		
		btnReady = new JButton("\u51C6\u5907");
		btnReady.setToolTipText("");
		btnReady.setBounds(390, 387, 123, 53);
		btnReady.addActionListener(new BtnReadyListener());
		btnReady.setVisible(false);
		panel.add(btnReady);
		
		leftOpenCardBtn = new JButton("");
		leftOpenCardBtn.setBounds(39, 72, 81, 117);
		leftOpenCardBtn.setVisible(false);
		panel.add(leftOpenCardBtn);
		
		rightOpenCardBtn = new JButton("");
		rightOpenCardBtn.setBounds(611, 72, 81, 117);
		rightOpenCardBtn.setVisible(false);
		panel.add(rightOpenCardBtn);
		
		opeLabel = new JLabel("�����Ͻǵ�����ӵ�������");
		opeLabel.setFont(new Font("����", Font.ITALIC, 20));
		opeLabel.setBounds(224, 335, 318, 45);
		panel.add(opeLabel);
		//�������
		try {
			gamer = new GamerImpl(this);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	
	private void setupMenu() {
		// TODO Auto-generated method stub
		JMenuBar menuBar = new JMenuBar();
		JMenuItem conn = new JMenuItem(connectAction);
		JMenuItem exit = new JMenuItem("�˳�");
		exit.addActionListener(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exit();
			}
		});
		JMenu file = new JMenu("�ͻ���");
		file.add(conn);
		menuBar.add(file);
		setJMenuBar(menuBar);
	}
	
	private void exit() {
		destroy();
		System.exit(0);
	}
	
	public void destroy() {
		try {
			disconnect();
		} catch (java.rmi.RemoteException ex) {
			ex.printStackTrace();
		}
	}


	/**
	 * ActionListener
	 * */
	class LeftThrowBtnListener implements ActionListener{//�������*
		@Override
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	class RightThrowBtnListener implements ActionListener{//�Ҳ�����*
		@Override
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	class BtnDrawBtnListener implements ActionListener{//���ư�ť*
		@Override
		public void actionPerformed(ActionEvent e) {
			btnOmiReq.setVisible(false);
			btnDrawBtn.setVisible(false);
			opeLabel.setText("��ѡ��һ����չʾ������");
			try {
				server.drawCard(my_gamerID);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
	
	class BtnHandCard2Listener implements ActionListener{//����2*
		@Override
		public void actionPerformed(ActionEvent e) {
			btnHandCard2.setVisible(false);		 //ѡ��������2 ����1������ ����2���� �Ǵ�����������2
			
			btnHandCard.setEnabled(false);
			try {
				server.throwCard(my_gamerID, me_cardpoint, me_newPoint);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int t = me_newPoint;
			//meThrowBtn.setText(""+t);
			meThrowBtn.setIcon(backAddress(t,81,117));
			meThrowBtn.setVisible(true);
			meThrowBtn.setEnabled(false);
			me_cardpoint = me_newPoint; // ���µ���
			opeLabel.setText("�ȴ��������...");
			
		}
	}
	
	class BtnHandCardListener implements ActionListener{	//����1*
		@Override
		public void actionPerformed(ActionEvent e) {
			btnHandCard2.setEnabled(false);     //ѡ��������1 ����2������ ����1�����Ǵ�����������1
			btnHandCard.setVisible(false);
			try {
				server.throwCard(my_gamerID, me_newPoint, me_cardpoint);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//meThrowBtn.setText(""+me_cardpoint);
			meThrowBtn.setIcon(backAddress(me_cardpoint,81,117));
			meThrowBtn.setVisible(true);
			meThrowBtn.setEnabled(false);
			opeLabel.setText("�ȴ��������...");
		}
	}
	
	class BtnOmiReqListener implements ActionListener{//������С�紨�ư�ť*
		@Override
		public void actionPerformed(ActionEvent e) {
			btnOmiReq.setVisible(false);
			btnDrawBtn.setVisible(false);
			opeLabel.setText("�ȴ��������...");
			try {
				server.turnCard(my_gamerID);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	class BtnJoinListener implements ActionListener{//����ľְ�ť*
		@Override
		public void actionPerformed(ActionEvent e) {
			opeLabel.setText("����ľ�...�ȴ��������...");
			me_coin--;
			refreshCoins();
			btnJoin.setVisible(false);
			btnPass.setVisible(false);
			try {
				server.joinGame(my_gamerID);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	class BtnPassListener implements ActionListener{//PASS��ť*
		@Override
		public void actionPerformed(ActionEvent e) {
			opeLabel.setText("PASS...�ȴ��������...");
			btnJoin.setVisible(false);
			btnPass.setVisible(false);
			try {
				server.pass(my_gamerID);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	class BtnReadyCancelListener implements ActionListener{//ȡ��׼����ť*
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				server.ready(my_gamerID, false);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			btnReady.setEnabled(true);
			btnReadyCancel.setEnabled(false);
			opeLabel.setText("��׼����ʼ��Ϸ");
		}
	}
	
	class BtnReadyListener implements ActionListener{//׼����ť*
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				server.ready(my_gamerID, true);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			btnReady.setEnabled(false);
			btnReadyCancel.setEnabled(true);
			opeLabel.setText("�ȴ��������...");
		}
	}
	
	
	
	
	
	/**
	 * ������
	 * */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameClient frame = new GameClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * ������---->�ͻ���
	 * */
	public void receiveEnter(String name, int gamerID) {
		System.out.println("now is receiveEnter() going");
		textHistory.append("���"+name+"���뷿��,idΪ"+gamerID+"\n");
		// TODO Auto-generated method stub
		/**
		 * 1   2        0   1       2   0  
		 *   0            2           1
		 *   �������
		 * */
		if(my_gamerID == 0) {
			if(gamerID == 1) {
				left_gamerID = gamerID;
				left_name = name;
				//textHistory.append("���"+name+"���뷿��\n");
			}else if(gamerID == 2) {
				right_gamerID = gamerID;
				right_name = name;
				//textHistory.append("���"+name+"���뷿��\n");
			}else {
				System.out.println("receiveEnter���ʹ��� at mygameID ==0");
			}
			
		}else if(my_gamerID == 2) {
			if(gamerID == 0) {
				left_gamerID = gamerID;
				left_name = name;
				//textHistory.append("���"+name+"���뷿��\n");
			}else if(gamerID == 1) {
				right_gamerID = gamerID;
				right_name = name;
				//textHistory.append("���"+name+"���뷿��\n");
			}else {
				System.out.println("receiveEnter���ʹ��� at mygameID ==2");
			}
			
			
			
		}else if(my_gamerID == 1) {
			if(gamerID == 2) {
				left_gamerID = gamerID;
				left_name = name;
			}else if(gamerID == 0) {
				right_gamerID = gamerID;
				right_name = name;
			}else {
				System.out.println("receiveEnter���ʹ��� at mygameID ==1");
			}
			
			
		}else {
			System.out.println("receiveEnter���ʹ��� at home");
		}
		
		
		refresh();
		
	}

	


	public void receiveExit(String name, int gamerID) {
		System.out.println("now is receiveexit() going");
		textHistory.append("���"+name+"�뿪����\n");
		// TODO Auto-generated method stub
		if(left_gamerID == gamerID) {
			left_gamerID = -1;
			left_name = "player1";
			left_ready = 0;
		}else if(right_gamerID == gamerID) {
			right_gamerID = -1;
			right_name = "player2";
			right_ready = 0;
		}
		
		refresh();
		
		
	}

	public void reReady(int gamerID, boolean readyready) {
		System.out.println("now is reready() going");
		textHistory.append("��"+gamerID+"�������׼��\n");
		// TODO Auto-generated method stub
		if(left_gamerID == gamerID) {
			left_ready = readyready?1:0;
		}else if(right_gamerID == gamerID) {
			right_ready = readyready?1:0;
		}
		
		refresh();
		
	}

	public void keepGoing(int stage) {
		// TODO Auto-generated method stub
		/**
		 * stage = 0 ��Ϸ��ʼ ������� ��ʼ�µ�һ�� stage 1 һ�׶� stage2 ���׶�
		 * */
		if(stage == 0) {
			
			if(round==7) {
				round = 0;
				me_coin = 4;
				left_coin = 4;
				right_coin = 4;
				for(int i = 0;i<7;i++) {
					roundLabel_[i].setEnabled(false);
				}

			}
			
			textHistory.append("���ڿ�ʼ��"+(round+1)+"�غ�\n");
			roundLabel_[round++].setEnabled(true);
			leftStatusLabel.setText("");
			rightStatusLabel.setText("");
			omiCardBtn.setIcon(backAddress(0,123,163)); // С�紨ͼ��
			opeLabel.setText("�ȴ��������...");
			btnReadyCancel.setVisible(false);
			btnReady.setVisible(false);
			meThrowBtn.setVisible(false);
			leftThrowBtn.setVisible(false);
			rightThrowBtn.setVisible(false);
			btnHandCard2.setVisible(false);
			btnHandCard.setVisible(false);
			leftPicBtn.setVisible(true);
			rightPicBtn.setVisible(true);
			leftOpenCardBtn.setVisible(false);
			rightOpenCardBtn.setVisible(false);

			
			
			
		}else if(stage == 1) {
			btnOmiReq.setVisible(true);
			btnDrawBtn.setVisible(true);
			opeLabel.setText("�����ˣ���ѡ��һ�������");
			
			
		}else if(stage == 2) {
			btnJoin.setVisible(true);
			btnPass.setVisible(true);
			opeLabel.setText("�����ˣ���ѡ���Ƿ���1öӲ�Ҽ���ľ�:");
			if(round==7) opeLabel.setText("�����ˣ���ѡ���Ƿ���2öӲ�Ҽ���ľ�:");
			if(me_coin>0) {
				if(round!=7) {
					//�������վ�ʱ ��Ӳ��������Ϊ0
					btnJoin.setEnabled(true);
				}else if(me_coin>=2) {
					btnJoin.setEnabled(true);
				}else {
					btnJoin.setEnabled(false);
				}
			}else {
				btnJoin.setEnabled(false);
			}
			
			
			
		}
		
		
		
		
	}

	public void reOpe1FromOther(OpeMsg msg) {
		// TODO Auto-generated method stub
		
	}

	public void reOpe2FromOther(int tgamerID,int inorout) {
		// TODO Auto-generated method stub
		//textHistory.append(msg.getTextmsg());
		//msg����Ϊ˭�����˶ľ� 
		//��˭�۳���Ӧ��Ӳ����
		//������״̬������ľ�/��PASS"
		int tid = tgamerID;
		int type = inorout;
		if(type == 1) {//1����ľ�
			if(tid == left_gamerID) {
				left_coin--;
				leftStatusLabel.setText("����ľ�");
				textHistory.append("��"+tgamerID+"����Ҽ���ľ�\n");
				if(round==7) left_coin--;//��7�غϿ�������
				
			}else if(tid == right_gamerID) {
				right_coin--;
				textHistory.append("��"+tgamerID+"����Ҽ���ľ�\n");
				rightStatusLabel.setText("����ľ�");
				if(round == 7) right_coin--;
				
			}
			refreshCoins();
		}else if(type == 2) {//2PASS
			if(tid == left_gamerID) {
				leftStatusLabel.setText("PASS");
				textHistory.append("��"+tgamerID+"�����ѡ��PASS\n");
				
			}else if(tid == right_gamerID) {
				rightStatusLabel.setText("PASS");
				textHistory.append("��"+tgamerID+"�����ѡ��PASS\n");
				
			}
		}
		
	}

	public void theOmiCard(int cardPoints) {//����С�紨��
		// TODO Auto-generated method stub
		//omiCardBtn.setText(""+cardPoints);
		omiCardBtn.setIcon(backAddress(cardPoints,123,163));
		textHistory.append("С�紨��Ϊ"+cardPoints+"\n");
		//history����
		
		
	}

	public void reTheOriCard(int cardPoints) {//���ܵ���
		// TODO Auto-generated method stub
		btnHandCard.setVisible(true);
		//btnHandCard.setText(""+cardPoints);
		btnHandCard.setIcon(backAddress(cardPoints,93,142));
		btnHandCard.setEnabled(false);
		me_cardpoint = cardPoints;
		textHistory.append("���յ�����Ϊ"+cardPoints+"\n");
		
		//history����
	}

	public void reTheNewCard(int cardPoints) {//�����µ���
		// TODO Auto-generated method stub
		me_newPoint = cardPoints;
		btnHandCard2.setVisible(true);
		//btnHandCard2.setText(""+cardPoints);
		btnHandCard2.setIcon(backAddress(cardPoints,93,142));
		btnHandCard.setEnabled(true);
		btnHandCard2.setEnabled(true);
		textHistory.append("���յ�������Ϊ"+cardPoints+"\n");
		
		
	}

	public void reThrowCardFromOther(int gamerID, int cardPoints) {
		// TODO Auto-generated method stub
		//����������ҵ�����
		textHistory.append("��"+gamerID+"�����������"+cardPoints+"\n");
		if(left_gamerID == gamerID) {
			leftThrowBtn.setVisible(true);
			leftThrowBtn.setEnabled(false);
			//leftThrowBtn.setText(""+cardPoints);
			leftThrowBtn.setIcon(backAddress(cardPoints,81,117));
			
		}else if(right_gamerID == gamerID) {
			rightThrowBtn.setVisible(true);
			rightThrowBtn.setEnabled(false);
			//rightThrowBtn.setText(""+cardPoints);
			rightThrowBtn.setIcon(backAddress(cardPoints,81,117));
		}
		
		
	}

	public void reOpenCardFromOther(int gamerID, int cardPoints) {
		// TODO Auto-generated method stub
		textHistory.append("����:��"+gamerID+"����ҵĵ���Ϊ"+cardPoints+"\n");
		if(left_gamerID == gamerID) {
			leftPicBtn.setVisible(false);
			leftOpenCardBtn.setVisible(true);
			//leftOpenCardBtn.setText(""+cardPoints);
			leftOpenCardBtn.setIcon(backAddress(cardPoints,81,117));
			opeLabel.setText("���غ���Ϸ����");
		}else if(right_gamerID == gamerID) {
			
			rightPicBtn.setVisible(false);
			rightOpenCardBtn.setVisible(true);
			//rightOpenCardBtn.setText(""+cardPoints);
			rightOpenCardBtn.setIcon(backAddress(cardPoints,81,117));
			opeLabel.setText("���غ���Ϸ����");
		}
		
		
	}

	public void rePointFromServer(int winnerid,int money) {
		// TODO Auto-generated method stub
		int winner = winnerid;
		String winName = "��" ;
		if(winner == left_gamerID) {
			winName = left_name;
			left_coin+=money;
		}else if(winner == right_gamerID) {
			winName = right_name;
			right_coin+=money;
		}else {
			me_coin+=money;
		}
		refreshCoins();
		
		
		if(round<7) {
			opeLabel.setText(winName+"����˱�����Ϸ��ʤ��,׼��������һ�غ�");
			textHistory.append(winName+"����˱�����Ϸ��ʤ��,׼��������һ�غ�");
		}else {
			String str = "";
			if(left_coin>right_coin) {
				//�ҳ����ֵ
				if(left_coin>me_coin) {
					str=left_name+"���ӵ������Ӳ�ң���������ֱ�����ʤ����";
				}else {
					str="��ӵ������Ӳ�ң���������ֱ�����ʤ����";
				}
				
			}else {
				
				if(right_coin>me_coin) {
					str=right_name+"���ӵ������Ӳ�ң���������ֱ�����ʤ����";
				}else {
					str="��ӵ������Ӳ�ң���������ֱ�����ʤ����";
				}
			}
			textHistory.append(str+"\n");
		}
		
		left_ready = 0;
		right_ready = 0;
		me_ready = 0;

		
		
		btnReadyCancel.setVisible(true);
		btnReadyCancel.setEnabled(false);
		btnReady.setVisible(true);
		btnReady.setEnabled(true);
		
		
		
	}

	public void serverStop() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(dlg, "�������ʧȥ����");
		server = null;
		connectAction.setEnabled(false);
		
	}
	
	
	/**
	 * ˢ�����
	 * */
	private void refresh() {
		// TODO Auto-generated method stub
		System.out.println("now is refresh() going");
		leftLabel.setText(left_name+" ("+left_gamerID+")");
		rightLabel.setText(right_name+" ("+right_gamerID+")");
		leftStatusLabel.setText((left_ready==1)?"��׼��":" ");
		rightStatusLabel.setText((right_ready==1)?"��׼��":" ");
		
		refreshCoins();
		
	}
	
	
	
	
	private void refreshCoins() {
		// TODO Auto-generated method stub
		 meCoinLabel.setText("Ӳ�ң�"+me_coin);
		 leftCoinLabel.setText("Ӳ�ң�"+left_coin);
		 rightCoinLabel.setText("Ӳ�ң�"+right_coin);
		 
	}

	/**
	 * ����ͼƬ
	 * */
	public ImageIcon backAddress(int num,int w,int h) {
		String url = System.getProperty("user.dir")+"\\image\\" + num + ".png";
		
		ImageIcon icon1 = new ImageIcon(url);
		Image img = icon1.getImage();
		Image newimg = img.getScaledInstance(w,h,java.awt.Image.SCALE_SMOOTH); 
		ImageIcon icon = new ImageIcon(newimg); 
		
		return icon;
	}
	

	/**
	 * connect
	 * */
	public boolean connect() throws java.rmi.RemoteException, java.net.MalformedURLException, java.rmi.NotBoundException{
		server = (GameServer) java.rmi.Naming.lookup("//" + serverAddr + "/GameServer");
		boolean ans = server.login(my_name, gamer);
		return ans;
	}
	protected void disconnect() throws java.rmi.RemoteException {
		if (server != null)
			server.logout(my_name,my_gamerID);
	}
	
	
	/**
	 * �ͻ������Ӵ���
	 * */	
	class ConnectAction extends AbstractAction {
		public ConnectAction() {
			super("����");
			
		}
		
		public void actionPerformed(ActionEvent evt) {
			dlg.pack();
			dlg.setLocationRelativeTo(GameClient.this);
			dlg.setVisible(true);
			if (dlg.getValue() == JOptionPane.OK_OPTION) {
				try {
					my_name = dlg.getUserName();
					serverAddr = dlg.getServerAddr();
					if(connect()) {
						JOptionPane.showMessageDialog(dlg, "������");
						System.out.println(my_name + " ������");
						my_gamerID = server.getWhoId(my_name);			//��ȡgamerID
						meLabel.setText(my_name+" ��"+my_gamerID+")");  //����id
						server.getOtherLogined(gamer);
						opeLabel.setText("�����������ӣ���׼����ʼ��Ϸ");
						btnReadyCancel.setVisible(true);
						btnReady.setVisible(true);
						btnReady.setEnabled(true);
						btnReadyCancel.setEnabled(false);//����׼����ȡ��׼����ť
						round = 0;
					}else {
						JOptionPane.showMessageDialog(dlg, "�ظ��û���/�����Ѵ����������");
					}
					
					//inputBox.setEditable(true);
					//displayBox.setText("");
					
					this.setEnabled(false);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(dlg, "����ʧ��");
					System.out.println("�������ӵ�������");
					return;
				}
			}
		}
	}
	
}
