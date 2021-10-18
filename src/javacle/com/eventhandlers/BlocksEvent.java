package javacle.com.eventhandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import javacle.com.BlockManager;
import javacle.com.Main;
import javacle.com.ClassImp;
import javacle.com.states.StateManager;
import net.md_5.bungee.api.ChatColor;

public class BlocksEvent extends ClassImp implements Listener  {

	private BlockManager bm = getBm();
	public BlocksEvent(Main main) {
	super(main);
	}

	@EventHandler
	public void blockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		if(StateManager.isOnEdit(p)) {
			e.setCancelled(true);
			if(bm.containsBlock(e.getBlock())) {
				p.sendMessage(ChatColor.RED + "This block is already a Launchpad!");
				return;
			}
			bm.saveBlock(e.getBlock());
			e.getBlock().setType(bm.getPadMaterial());
			p.sendMessage(ChatColor.GREEN + "This block was set to a Launchpad!");
			return;
		}
		if(bm.containsBlock(e.getBlock())) {
			if(e.isCancelled()) return;
			if(!p.isOp() && !p.hasPermission("launchpads.admin") && !p.hasPermission("launchpads.break")) {
				p.sendMessage(ChatColor.RED + "You are not allowed to break a Launchpad!");
				e.setCancelled(true);
				return;
			}
			bm.removeBlock(e.getBlock());
			p.sendMessage(ChatColor.RED + "You broke a Launchpad and it was removed!");
		}
		
	}
	
	@EventHandler
	public void blockBreak2(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(StateManager.isOnBSet(p)) e.setCancelled(true);
	}
	@EventHandler
	public void onplace(BlockPlaceEvent e) {
		if(StateManager.isOnEdit(e.getPlayer())) e.setCancelled(true);
	}
	
}
	
