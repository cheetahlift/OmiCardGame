package omiInterface;

import java.rmi.RemoteException;

import omiServer.OpeMsg;
import omiServer.PointBand;

public interface Gamer extends java.rmi.Remote {
	public void receiveEnter(String name,int gamerID) throws RemoteException;
	//�û�����
	public void receiveExit(String name,int gamerID) throws RemoteException;
	//�û��뿪

	public void reReady(int gamerID,boolean readyready) throws RemoteException;
	//������Ϣ �����û�׼�����

	public void keepGoing(int stage) throws RemoteException;
	//������Ҫ���û����в��� stage =1 �׶�1 stage 2 = �׶�2


	//public void receiveCard()
	//���տ�Ƭ�����Ƶ��ơ�������ҵĿ��ơ�����������õ��ƣ�


	public void reOpe1FromOther(OpeMsg msg) throws RemoteException;
	//������Ϣ��������ҵĽ׶�6�Ĳ�����
	public void reOpe2FromOther(int gamerID,int inorout) throws RemoteException;
	//������Ϣ��������ҵĽ׶�8�Ĳ�����


	public void theOmiCard(int cardPoints) throws RemoteException;
	//С�紨�ƣ��׶�3���׶�6-2�����ո���Ϣ���滻С�紨��

	public void reTheOriCard(int cardPoints) throws RemoteException;
	//������Ϣ �����ƣ�

	public void reTheNewCard(int cardPoints) throws RemoteException;
	//����6-1����������

	public void reThrowCardFromOther(int gamerID,int cardPoints) throws RemoteException;
	//������Ϣ��������ҵ������� ����6-2��

	public void reOpenCardFromOther(int gamerID,int cardPoints) throws RemoteException;
	//����������ҵĿ��ƣ��׶�9��

	public void rePointFromServer(int winnerid,int meney) throws RemoteException;
	//������һ�μ���͵ڶ��μ��㣨�׶�9��
	//չʾʤ�������ڿͻ��˽���
	//����Ӳ�Ҽ���Ҳ�ڿͻ��˽���
	
	public void serverStop() throws RemoteException;
	//��������˳������ߡ�������� ������ֹ��Ϸ



		

}
