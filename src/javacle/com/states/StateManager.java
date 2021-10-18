package javacle.com.states;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class StateManager {

	private static Map<Player, States> playerState = new HashMap<>();


	public static void addPlayer(Player player, States state) {
		if(playerState.containsKey(player)) return;
		playerState.put(player, state);
	}
		
	public static void removePlayer(Player player) {
		playerState.remove(player);
	}
	
	public static boolean containsPlayer(Player player) {
		return playerState.containsKey(player);
	}
	
	public static boolean isLaunched(Player player) {
		return (playerState.get(player) == States.LAUNCHED? true :false);
	}
	
	public static boolean isOnEdit(Player player) {
		return (playerState.get(player)==States.EDIT? true :false);
	}
	
	public static boolean isOnBSet(Player player) {
		return (playerState.get(player) == States.BLOCK_SET? true :false);
	}
}
