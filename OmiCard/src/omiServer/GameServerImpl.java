package omiServer;

import java.util.List;
import java.util.Random;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;

import omiInterface.GameServer;
import omiInterface.Gamer;
import omiServer.OpeMsg;

public class GameServerImpl extends java.rmi.server.UnicastRemoteObject implements GameServer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private final static String BINDNAME = "GameServer";
	/**
	 * ����ǰ����
	 * */
	int loginGamer = 0;				//������3ʱ����ֹ����
	int[] gamerIDHouse = {0,0,0};   //ID�ֿ�
	int readyGamer = 0;				//������3ʱ����Ϸ��ʼ
	static GameServerImpl server = null; //���ͻ��˵���
	List gamers = new ArrayList();     //����б�
	List listeners = new ArrayList();	//	����
	protected GameServerImpl() throws java.rmi.RemoteException {
		
	}
	
	public static GameServerImpl getInstance() {
		try {
			if (server == null) {
				server = new GameServerImpl();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
		return server;
	}
	
	/**
	 * ÿ���ƾ�����
	 * */
	int[] cardHouse = new int[15]; 	//�ƿ� ÿ����Ϸ�Զ�ϴ��
	int cardHousePointer;				//�ƿ�ָ�� ���ڼ�����
	int round;						//�ڼ��֣�1-7��
	int curGamerID;					//��ǰ��� id 0 1 2 
	int[] joinGamer = new int[3];	//����ľֵ���� �±�ΪgamerID 0Ϊpass 1Ϊ����
	int[] GamersCard = new int[3];	//���������Ƶĵ���
	int omiCard;					//С�紨��
	
	
	
	/**
	 * ����
	 * */
	public int getWhoId(String name) throws java.rmi.RemoteException{//���ܲ����õ�
		Iterator itr = gamers.iterator();
		while(itr.hasNext()) {//���� ��id
			GamerInfo gamerinfo2 = (GamerInfo) itr.next();
			if(gamerinfo2.getName().equals(name)){
				return gamerinfo2.getGamerID();
			}
		}
		return -1;
		
	}
	
	
	
	
	public boolean login(String name,Gamer g) throws java.rmi.RemoteException{
		if(loginGamer == 3) return false; // �ﵽ����û���¼�� �ܾ���¼
		int tgamerID = 0;
		for(int i = 0;i<3;i++) {
			if(gamerIDHouse[i]==0) {
				 tgamerID = i;			//��ȡΨһid
				 gamerIDHouse[i] = 1;
				 break;
			}
		}
		      
		/**
		 * gamerinfo :�µ�¼���
		 * gamerinfo2 :���µ�¼������ѵ�¼���
		 * */
		GamerInfo gamerinfo = new GamerInfo(name,tgamerID,g);     
		Iterator itr = gamers.iterator();
		while(itr.hasNext()) {//���͸���������µ�¼���û�  
			GamerInfo gamerinfo2 = (GamerInfo) itr.next();
			gamerinfo2.getGamer().receiveEnter(name,tgamerID);
		}
		
		
	
		
		gamers.add(gamerinfo);
		
		loginGamer++;
		notifyListener(gamerinfo.getName() + " ���뷿��",1);
		notifyListener(String.valueOf(loginGamer),2);//���µ�¼����
		round = 0;
		return true;
	}
	
	public void getOtherLogined(Gamer g) throws java.rmi.RemoteException{
		//���͸��µ�¼����Ѿ���¼���û�
		
		
		
		Iterator itr = gamers.iterator();
		while(itr.hasNext()) {//���͸��µ�¼����Ѿ���¼���û�
			GamerInfo gamerinfo2 = (GamerInfo) itr.next();
			String tname2 = gamerinfo2.getName();
			int tgamerID2 = gamerinfo2.getGamerID();
				g.receiveEnter(tname2,tgamerID2);
			
		}
		
	}
	
	public void logout(String name,int gamerID) throws java.rmi.RemoteException {
		GamerInfo u_gone = null;
		Iterator itr = null;
		
		synchronized(gamers) {
			for(int i = 0;i<gamers.size();i++) {
				GamerInfo gamerinfo = (GamerInfo) gamers.get(i);
				if(gamerinfo.getName().equals(name)) {
					u_gone = gamerinfo;
					gamers.remove(i);
					itr = gamers.iterator();
					break;
				}
			}
		}
		
		gamerIDHouse[gamerID] = 0;
		
		
		
		
		while(itr.hasNext()) {//���͸���������˳���¼�û�
			GamerInfo gamerinfo = (GamerInfo) itr.next();
			gamerinfo.getGamer().receiveExit(name,gamerID);
		}
		
		loginGamer--;
		notifyListener(name + "�˳�����",1);
		notifyListener(String.valueOf(loginGamer),2);//���µ�¼����
		
		
	}
	
	public void stop() throws RemoteException, NotBoundException, MalformedURLException {
		//notifyListener(STATEMSG[1]);
		Iterator itr = gamers.iterator();
		while (itr.hasNext()) {
			GamerInfo u = (GamerInfo) itr.next();
			u.getGamer().serverStop();
		}
		java.rmi.Naming.unbind(BINDNAME);
	}
	
	public void ready(int gamerID, boolean readyready) throws java.rmi.RemoteException{
		if(readyready) {
			readyGamer++;
			
			if(readyGamer==3) {
				//round = 0;
				gameStart();
				return;
			}
		}else {
			readyGamer--;
		}
		
		Iterator itr = gamers.iterator();
		while(itr.hasNext()) {//���͸��������׼�����
			GamerInfo gamerinfo2 = (GamerInfo) itr.next();
			gamerinfo2.getGamer().reReady(gamerID,readyready);
		}
		notifyListener(gamerID + "�����"+(readyready?"��׼��":"ȡ��׼��"),1);
		notifyListener(String.valueOf(readyGamer),3);//����׼������
		
	}
	
	
	

	private void gameStart() throws java.rmi.RemoteException{
		// TODO Auto-generated method stub
		notifyListener("������Ҿ�׼����������Ϸ��ʼ",1);
		round++;				//�غ���
		curGamerID = 0;			//��ǰ���id
		
		cardHouse = washCards(); //ϴ��
		cardHousePointer = 0;
		omiCard  = cardHouse [cardHousePointer++]; //ȷ��С�紨��
		
		Iterator itr = gamers.iterator();
		while(itr.hasNext()) {//���͸��������С�紨��
			GamerInfo gamerinfo2 = (GamerInfo) itr.next();
			gamerinfo2.getGamer().keepGoing(0);		//keepGoing �׶�0 ��һ����Ϸ��ʼ
			gamerinfo2.getGamer().theOmiCard(omiCard);
		}
		
//		try {
//			Thread.sleep(5000);//��ͣ5s
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		
		for(int i = 0;i<3;i++) {
			//ȷ��������ҵĵ���
			GamersCard[i] = cardHouse [cardHousePointer++];
		}
		
		Iterator itr2 = gamers.iterator();
		
		while(itr2.hasNext()) {//���͸�������ҵ���
			GamerInfo gamerinfo2 = (GamerInfo) itr2.next();
			int gamerID = gamerinfo2.getGamerID();
			gamerinfo2.getGamer().reTheOriCard(GamersCard[gamerID]);
		}
		
		Iterator itr3 = gamers.iterator();
		while(itr3.hasNext()) {//Ѱ��id = 0 ����� ��������keepGoing
			GamerInfo gamerinfo2 = (GamerInfo) itr3.next();
			int gamerID = gamerinfo2.getGamerID();
			if(gamerID == 0) {
				gamerinfo2.getGamer().keepGoing(1); // keepGoing �׶�1
				break;
			}
			
		}
		
		//gameStart end
	}
	
	private int[] washCards() {   // ϴ�ƴ���
		// TODO Auto-generated method stub
		int[] arr = {1,3,5,7,9,2,4,6,8,10,11,13,12,14,15};
		int length = 15;
		int index = length - 1;
		
		for(int i = 0;i < length && index > 0 ; i++) {
			int num = (new Random().nextInt(index));
			int temp = arr[num];
			arr[num] = arr[index];
			arr[index] = temp;
			index --;
		}
		return arr;
	}

	public void drawCard(int gamerID) throws java.rmi.RemoteException{
		//�û��������ƽ׶�6-1
		GamersCard[gamerID] = cardHouse[cardHousePointer++];
				
		Iterator itr = gamers.iterator();
		while(itr.hasNext()) {//Ѱ��id = gamerID ����� ���������¿�Ƭ
			GamerInfo gamerinfo2 = (GamerInfo) itr.next();
			int tgamerID = gamerinfo2.getGamerID();
			if(tgamerID == gamerID) {
				gamerinfo2.getGamer().reTheNewCard(GamersCard[gamerID]);//���û����Ͳ���6-1����������
				break;
			}
			
		}		

		//reOpe1FromOther(OpeMsg msg);//���͸������û�����Ϣ
		
		
		//drawCard end
	}

	public void throwCard(int gamerID,int chooseCard,int throwCard) throws java.rmi.RemoteException{
		//�û������� �׶�6-1-2
		notifyListener(gamerID + "�����ѡ����"+chooseCard+"��Ϊ�Լ��ĵ���"+"��"+throwCard+"������",1);
		GamersCard [gamerID] = chooseCard; //�����������û��Ŀ��滻Ϊ�û�ѡ��Ŀ�
		
		Iterator itr = gamers.iterator();
		while(itr.hasNext()) {//���͸������û�����Ϣ gamerID�����õĿ�Ƭ
			GamerInfo gamerinfo2 = (GamerInfo) itr.next();
			int tgamerID = gamerinfo2.getGamerID();
			if(tgamerID != gamerID) {
				gamerinfo2.getGamer().reThrowCardFromOther(gamerID,throwCard);
				
			}
			
		}		
		
		if(curGamerID == 0) {					//Ѱ����һ�����
			curGamerID = 1;
		}else if(curGamerID == 1) {
			curGamerID = 2;
		}else if(curGamerID == 2) {
			curGamerID = -1;
			
			
		}
		
		Iterator itr2 = gamers.iterator();
		while(itr2.hasNext()) {
			GamerInfo gamerinfo2 = (GamerInfo) itr2.next();
			int tgamerID = gamerinfo2.getGamerID();
			if(tgamerID == curGamerID) {
				gamerinfo2.getGamer().keepGoing(1);//����һ����ҷ���keepGoing
				
			}
			
		}	
		
		if (curGamerID == -1) gameContinue();
		
		

	}

	
	public void turnCard(int gamerID) throws java.rmi.RemoteException{
		//�û�������С�紨�� ����6-2
		omiCard  = cardHouse[cardHousePointer++];

		Iterator itr = gamers.iterator();
		while(itr.hasNext()) {//���͸��������С�紨��
			GamerInfo gamerinfo2 = (GamerInfo) itr.next();
			gamerinfo2.getGamer().theOmiCard(omiCard);
		}

		if(curGamerID == 0) {					//Ѱ����һ�����
			curGamerID = 1;
		}else if(curGamerID == 1) {
			curGamerID = 2;
		}else if(curGamerID == 2) {
			curGamerID = -1;
			
			
		}
		
		Iterator itr2 = gamers.iterator();
		while(itr2.hasNext()) {
			GamerInfo gamerinfo2 = (GamerInfo) itr2.next();
			int tgamerID = gamerinfo2.getGamerID();
			if(tgamerID == curGamerID) {
				gamerinfo2.getGamer().keepGoing(1);//����һ����ҷ���keepGoing
				
			}
			
		}	
		
		if (curGamerID == -1) gameContinue();

	}
	
	private void gameContinue() throws java.rmi.RemoteException {
		// TODO Auto-generated method stub
		curGamerID = 0;
		Iterator itr = gamers.iterator();
		while(itr.hasNext()) {
			GamerInfo gamerinfo2 = (GamerInfo) itr.next();
			int tgamerID = gamerinfo2.getGamerID();
			if(tgamerID == curGamerID) {
				gamerinfo2.getGamer().keepGoing(2);//����һ����ҷ���keepGoing stage 2
				break;
			}
		}

	}
	
	public void joinGame(int gamerID) throws java.rmi.RemoteException {
		//�û�����ľ�
		joinGamer[gamerID] = 1;
		
		//reOpe2FromOther(OpeMsg msg);//���͸������û�����Ϣ2
		String tex = "���"+gamerID+"ѡ�����ľ�";
		notifyListener(tex,1);
		OpeMsg msg = new OpeMsg(tex,gamerID,1);
		
		Iterator itr2 = gamers.iterator();
		while(itr2.hasNext()) {
			GamerInfo gamerinfo3 = (GamerInfo) itr2.next();
			int tgamerID = gamerinfo3.getGamerID();
			if(tgamerID != gamerID) {
				gamerinfo3.getGamer().reOpe2FromOther(gamerID,1);//���͸������û�����Ϣ2
				//break;
			}
		}
		
		if(curGamerID == 0) {					//Ѱ����һ�����
			curGamerID = 1;
		}else if(curGamerID == 1) {
			curGamerID = 2;
		}else if(curGamerID == 2) {
			curGamerID = -1;
			
		}
		Iterator itr = gamers.iterator();
		while(itr.hasNext()) {
			GamerInfo gamerinfo2 = (GamerInfo) itr.next();
			int tgamerID = gamerinfo2.getGamerID();
			if(tgamerID == curGamerID) {
				gamerinfo2.getGamer().keepGoing(2);//����һ����ҷ���keepGoing stage 2
				break;
			}
		}
		
		if (curGamerID == -1) gameOver();

	}

	public void pass(int gamerID) throws java.rmi.RemoteException {
		joinGamer[gamerID] = 0;
		
		if(curGamerID == 0) {					//Ѱ����һ�����
			curGamerID = 1;
		}else if(curGamerID == 1) {
			curGamerID = 2;
		}else if(curGamerID == 2) {
			curGamerID = -1;
			
		}
		Iterator itr = gamers.iterator();
		while(itr.hasNext()) {
			GamerInfo gamerinfo2 = (GamerInfo) itr.next();
			int tgamerID = gamerinfo2.getGamerID();
			if(tgamerID == curGamerID) {
				gamerinfo2.getGamer().keepGoing(2);//����һ����ҷ���keepGoing stage 2
				break;
			}
		}
		
		if (curGamerID == -1) gameOver();

	}
	
	
	private void gameOver() throws java.rmi.RemoteException{
		// TODO Auto-generated method stub
		Iterator itr = gamers.iterator();
		while(itr.hasNext()) {                   //��������� ���� ����ľֵ���ҵĵ���
			GamerInfo gamerinfo2 = (GamerInfo) itr.next();
			int tgamerID = gamerinfo2.getGamerID();
			for(int i = 0 ;i<3;i++) {
				//��������ľ���ҵ�����
				if(joinGamer[i]==1) {
					//��i����ľ�
					//�����i�����ҷ��������
					if(tgamerID != i) {
						gamerinfo2.getGamer().reOpenCardFromOther(i,GamersCard[i]);
					}
				}
			}
			
		}
		
		
		
		/**
		 * ������
		 * �ռ����в���ľֵ��˵ĵ���
		 * �ҳ���С�� ���� С�紨��
		 * ���ҳ����� ���ʤ��
		 * 
		 * */
		for(int i = 0;i<3;i++) {
			
			if(joinGamer[i]==0) {
				//�Բ�����ľֵ��˵���+100;
				GamersCard[i]+=100;
			}
		}
		int min = FindMin(GamersCard);//����С
		int minuser = 0;
		for(int i = 0;i<3;i++) {		//����С���±�
			if(min == GamersCard[i]) {
				minuser = i;
				break;
			}
		}
		GamersCard[minuser]+=omiCard;	//��С���Ƽ���С�紨��
		for(int i = 0;i<3;i++) {
			
			if(joinGamer[i]==0) {
				//�Բ�����ľֵ��˵���-200;
				GamersCard[i]-=200;
			}
		}
		
		int max = FindMax(GamersCard);//�����
		int winner = 0;
		for(int i = 0;i<3;i++) {		//����С���±�
			if(max == GamersCard[i]) {
				winner = i;
				break;
			}
		}
		
		int money = 0;
		if(round!=7) {
			money=1+joinGamer[0]+joinGamer[1]+joinGamer[2];//��õĽ���= ����ľֵ��˵�֧�� ÿ��1��+���и���1��
		}else {
			money=1+(joinGamer[0]+joinGamer[1]+joinGamer[2])*2;
		}
		
		
		Iterator itr2 = gamers.iterator();
		while(itr2.hasNext()) {                   //��������� ���� ���ֵ����ս��
			GamerInfo gamerinfo2 = (GamerInfo) itr2.next();
			gamerinfo2.getGamer().rePointFromServer(winner,money);
		}
		
		
			//��Ϸ��ֹ �����Ҫ����׼��
			readyGamer = 0;
		

		
		
	}

	private int FindMin(int[] gamersCard2) {
		// TODO Auto-generated method stub
		int l = gamersCard2.length;
		int[] ta = new int[l];
		for(int i = 0;i<l;i++) {
			ta[i] = gamersCard2[i];
		}
		
		for(int i = 0;i<l-1;i++) {
			for(int j = i+1;j<l;j++) {
				if(ta[i]>ta[j]) {
					int t = ta[i];
					ta[i] = ta[j];
					ta[j] = t;
				}
			}
		}
		
		return ta[0];
	}
	
	private int FindMax(int[] gamersCard2) {
		// TODO Auto-generated method stub
		int l = gamersCard2.length;
		int[] ta = new int[l];
		for(int i = 0;i<l;i++) {
			ta[i] = gamersCard2[i];
		}
		
		for(int i = 0;i<l-1;i++) {
			for(int j = i+1;j<l;j++) {
				if(ta[i]<ta[j]) {
					int t = ta[i];
					ta[i] = ta[j];
					ta[j] = t;
				}
			}
		}
		
		return ta[0];
	}

	public void addListener(GameServerListener listener) {
		listeners.add(listener);

		
	}
	
	public void removeListense(GameServerListener listener) {
		listeners.remove(listener);
	}
	
	void notifyListener(String msg,int msgType) {
		Iterator itr = listeners.iterator();
		GameServerEvent evt = new GameServerEvent(this, msg, msgType);
		while (itr.hasNext()) {
			((GameServerListener) itr.next()).serverEvent(evt);
		}
	}
	
	
	public void start() throws RemoteException, MalformedURLException {
		java.rmi.Naming.rebind(BINDNAME, server);
		//notifyListener(STATEMSG[0]);
	}
	

	
	
	

}
