package omiServer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class ServerMain extends JFrame implements GameServerListener {
	
	
	GameServerImpl server = GameServerImpl.getInstance();
	JTextArea textArea;
	StarServerAction startAction = new StarServerAction();
	StopServerAction stopAction = new StopServerAction();
	JLabel serverStatusLabel;
	JLabel loginedLabel;
	JLabel readyedLabel;
	
	
	private JPanel contentPane;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerMain frame = new ServerMain();
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
	public ServerMain() {
		
		try {
			LocateRegistry.createRegistry(1099);
		}catch(RemoteException e) {
			e.printStackTrace();
		}
		
		
		setTitle("OmiCard\u670D\u52A1\u5668");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 442, 541);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnStartButton = new JButton("\u542F\u52A8\u670D\u52A1\u5668");
		btnStartButton.setBounds(15, 15, 186, 60);
		btnStartButton.addActionListener(new StarServerAction());
		contentPane.add(btnStartButton);
		
		JButton btnStopButton = new JButton("\u5173\u95ED\u670D\u52A1\u5668");
		btnStopButton.setBounds(216, 15, 186, 60);
		btnStopButton.addActionListener(stopAction);
		contentPane.add(btnStopButton);
		
		textArea = new JTextArea();
		textArea.setBounds(15, 90, 390, 364);
		contentPane.add(textArea);
		textArea.setColumns(10);
		
		serverStatusLabel = new JLabel("������״̬:ֹͣ");
		serverStatusLabel.setBounds(15, 458, 134, 21);
		contentPane.add(serverStatusLabel);
		
		loginedLabel = new JLabel("�ѵ�������:0");
		loginedLabel.setBounds(163, 459, 134, 21);
		contentPane.add(loginedLabel);
		
		readyedLabel = new JLabel("��׼������:0");
		readyedLabel.setBounds(308, 459, 134, 21);
		contentPane.add(readyedLabel);
	}
	
	private void exit() {
		try {
			server.stop();
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
		
		
	}
	
	public void serverEvent(GameServerEvent evt) {
		int type = evt.getMsgType();
		switch(type) { //1=��ͨ��Ϣ��2=����/�ǳ���Ϣ��3=׼��/ȡ��׼����Ϣ
			case 1:{
				textArea.append(evt.getMessage()+"\n");
				break;
			}
			case 2:{
				loginedLabel.setText("�ѵ�������:"+evt.getMessage());
				break;
			}
			case 3:{
				readyedLabel.setText("��׼������:"+evt.getMessage());
				break;
			}
			
		}
		
	
	}
	
	

	class StarServerAction extends AbstractAction {
//		public StartServerAction() {
//			super("����");
//			
//		}
		
		public void actionPerformed(ActionEvent evt) {
			try {
				server.addListener(ServerMain.this);
				textArea.setText("");
				server.start();
				stopAction.setEnabled(true);
				this.setEnabled(false);
				textArea.append("�����������ɹ�\n");
				serverStatusLabel.setText("������״̬:����");
				System.out.println("�����������ɹ�");
			}catch(Exception ex) {
				textArea.append("��������������\n");
				server.removeListense(ServerMain.this);
				ex.printStackTrace();
				return;
			}
		}
		
	}
	
	class StopServerAction extends AbstractAction {
		public StopServerAction() {
			super("ֹͣ");
			//putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("../images/stop.gif")));
			//putValue(Action.SHORT_DESCRIPTION, "ֹͣ���������");
			//putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
			this.setEnabled(false);
		}
		
		public void actionPerformed(ActionEvent arg0) {
			try {
				server.stop();
				server.removeListense(ServerMain.this);
				startAction.setEnabled(true);
				textArea.append("������ֹͣ�ɹ�\n");
				serverStatusLabel.setText("������״̬:ֹͣ");
				this.setEnabled(false);
			} catch (Exception e) {
				textArea.append("������ֹͣ����\n");
				e.printStackTrace();
				return;
			}
		}
	}
}
