package omiInterface;

import java.rmi.RemoteException;

public interface GameServer extends java.rmi.Remote{
	public boolean login(String name,Gamer gamer) throws RemoteException;
	//ע������� �����ظ����� ��ܾ�
	
	public void getOtherLogined(Gamer gamer) throws RemoteException;
	
	public int getWhoId(String name) throws RemoteException;
	
	public void logout(String name ,int gamerID) throws RemoteException;
	//�û��˳�
	
	public void ready(int gamerID, boolean readyready) throws RemoteException;
	//�û�����׼����� true��׼����� false��ȡ��׼��  3���˷��͸�ָ������Ϸ��ʼ

	public void drawCard(int gamerID) throws RemoteException;
	//�û��������ƣ��׶�6-1��

	public void throwCard(int gamerID,int chooseCard,int throwCard) throws RemoteException;
	//�û����������ƣ��׶�6-1-2��

	public void turnCard(int gamerID) throws RemoteException;
	//�û�����С�紨�ƣ��׶�6-2��

	public void joinGame(int gamerID) throws RemoteException;
	//�û��������ľ֣��׶�8-1��

	public void pass(int gamerID) throws RemoteException;
	//�û�����pass

	
}
