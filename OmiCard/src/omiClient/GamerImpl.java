package omiClient;

import java.rmi.RemoteException;

import omiInterface.Gamer;
import omiServer.OpeMsg;
import omiServer.PointBand;

public class GamerImpl extends java.rmi.server.UnicastRemoteObject implements Gamer {

	GameClient client;
	
	public GamerImpl(GameClient client) throws java.rmi.RemoteException {
		this.client = client;
	}
	
	public void receiveEnter(String name,int gamerID) throws java.rmi.RemoteException{
		client.receiveEnter(name,gamerID);
	}
	//�û�����
	public void receiveExit(String name,int gamerID) throws java.rmi.RemoteException{
		client.receiveExit(name,gamerID);
	}
	//�û��뿪

	public void reReady(int gamerID,boolean readyready) throws java.rmi.RemoteException{
		client.reReady(gamerID,readyready);
	}
	//������Ϣ �����û�׼�����

	public void keepGoing(int stage) throws java.rmi.RemoteException{
		client.keepGoing(stage);
	}
	//������Ҫ���û����в��� stage =1 �׶�1 stage 2 = �׶�2


	//public void receiveCard()
	//���տ�Ƭ�����Ƶ��ơ�������ҵĿ��ơ�����������õ��ƣ�


	public void reOpe1FromOther(OpeMsg msg) throws java.rmi.RemoteException{
		client.reOpe1FromOther(msg);
	}
	//������Ϣ��������ҵĽ׶�6�Ĳ�����
	public void reOpe2FromOther(int gamerID,int inorout) throws java.rmi.RemoteException{
		client.reOpe2FromOther(gamerID,inorout);
	}
	//������Ϣ��������ҵĽ׶�8�Ĳ�����


	public void theOmiCard(int cardPoints) throws java.rmi.RemoteException{
		client.theOmiCard(cardPoints);
	}
	//С�紨�ƣ��׶�3���׶�6-2�����ո���Ϣ���滻С�紨��

	public void reTheOriCard(int cardPoints) throws java.rmi.RemoteException{
		client.reTheOriCard(cardPoints);
	}
	//������Ϣ �����ƣ�

	public void reTheNewCard(int cardPoints) throws java.rmi.RemoteException{
		client.reTheNewCard(cardPoints);
	}
	//����6-1����������

	public void reThrowCardFromOther(int gamerID,int cardPoints) throws java.rmi.RemoteException{
		client.reThrowCardFromOther(gamerID,cardPoints);
	}
	//������Ϣ��������ҵ������� ����6-2��

	public void reOpenCardFromOther(int gamerID,int cardPoints) throws java.rmi.RemoteException{
		client.reOpenCardFromOther(gamerID,cardPoints);
	}
	//����������ҵĿ��ƣ��׶�9��

	public void rePointFromServer(int winnerid,int money) throws java.rmi.RemoteException{
		client.rePointFromServer(winnerid,money);
	}
	//������һ�μ���͵ڶ��μ��㣨�׶�9��
	//չʾʤ�������ڿͻ��˽���
	//����Ӳ�Ҽ���Ҳ�ڿͻ��˽���
	
	public void serverStop() throws java.rmi.RemoteException{
		client.serverStop();
	}
	//��������˳������ߡ�������� ������ֹ��Ϸ
	
	
}
