package omiServer;

import omiInterface.Gamer;

public class GamerInfo {
	private String name;
	//����û���

	private int gamerID;
	//���Ψһid
	
	private Gamer gamer;
	//Զ�̿ͻ��˶���
	
	public GamerInfo(String name, int gamerID,Gamer gamer) {
		setName(name);
		setGamerID(gamerID);
		setGamer(gamer);
	}
	
	public String getName() {
		return name;
	}
	

	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getGamerID() {
		return gamerID;
	}
	
	public void setGamerID(int gamerID) {
		this.gamerID = gamerID;
	}
	
	
	public Gamer getGamer() {
		return gamer;
	}
	
	public void setGamer(Gamer gamer) {
		this.gamer = gamer;
	}
	
}
