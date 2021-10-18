package javacle.com.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLaunchedEvent extends Event {

	private static final HandlerList HANDLER = new HandlerList();
	private final Player p;
	@Override
	public HandlerList getHandlers() {
		
		return HANDLER;
	}

	public static HandlerList getHandlerList() {
		return HANDLER;
	}
	
	public PlayerLaunchedEvent(Player p) {
		this.p = p;
}
	
	public Player getPlayer() {
		return this.p;
	}
}
