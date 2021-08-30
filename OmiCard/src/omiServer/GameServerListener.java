package omiServer;

import java.util.EventListener;

public interface GameServerListener extends EventListener {
	public void serverEvent(GameServerEvent evt);

}
