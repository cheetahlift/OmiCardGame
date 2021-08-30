package omiServer;

import java.util.EventObject;
//�������¼�
public class GameServerEvent extends EventObject {
	int msgType;   //1=��ͨ��Ϣ��2=������Ϣ��3=�ǳ���Ϣ 4=׼����Ϣ 5=ȡ��׼����Ϣ
	String message;
	
	public GameServerEvent(Object src, String message,int msgType) {
		super(src);
		setMessage(message);
		setMsgType(msgType);
	}

	public String getMessage() {
		return message;
	}
	
	
	public int getMsgType() {
		return msgType;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	
	

}
