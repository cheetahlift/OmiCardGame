package omiServer;

public class PointBand {
	//�Ʒְ�
	int winner;//���غ�Ӯ��
	boolean[] inorpass = new boolean[3];//�������
	int[] finalPoint = new int[3];//����С�紨�����
	
	public PointBand(int winner,boolean[] inorpass,int[] finalPoint) {
		setWinner(winner);
		setInorpass(inorpass);
		setFinalPoint(finalPoint);
	}
	
	public void setWinner(int winner) {
		this.winner = winner;
	}
	
	public void setInorpass(boolean[] inorpass) {
		this.inorpass = inorpass;
	}
	public void setFinalPoint(int[] finalPoint) {
		this.finalPoint = finalPoint;
	}
	
	public int getWinner() {
		return winner;
	}
	
	public boolean getInorpass(int num) {
		return inorpass[num];
	}
	
	public int getFinalPoint(int num) {
		return finalPoint[num];
	}
 	
}
