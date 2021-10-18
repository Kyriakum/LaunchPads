package javacle.com.eventhandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import javacle.com.ConfigManager;
import javacle.com.InventoryManager;
import javacle.com.Main;
import javacle.com.ClassImp;
import javacle.com.states.StateManager;
import javacle.com.states.States;
import net.md_5.bungee.api.ChatColor;

public class InventoryEvent extends ClassImp implements Listener  {

	
	private ConfigManager manager = getManager();
	public InventoryEvent(Main main) {
		super(main);
	}

	@EventHandler
	public void onInv(InventoryClickEvent e) {
		if(e.getView().getTitle().equalsIgnoreCase(InventoryManager.getName())) {
			if(!e.getWhoClicked().hasPermission("launchpads.admin") && !e.getWhoClicked().isOp()) return;
			if(e.getCurrentItem() == null)
				return;
			if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Permission")) {
				
				if(manager.getBoolean("permission_needed")) {
					manager.setBoolean("permission_needed", false);
					manager.reload();
					e.getWhoClicked().openInventory(InventoryManager.getInventory());
				} else {
					manager.setBoolean("permission_needed", true);
					manager.reload();
					e.getWhoClicked().openInventory(InventoryManager.getInventory());
				}
			} else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Apply Pad Effect")) {
			
				if(manager.getBoolean("apply_touching")) {
					manager.setBoolean("apply_touching", false);
					manager.reload();
					e.getWhoClicked().openInventory(InventoryManager.getInventory());
				} else {
					manager.setBoolean("apply_touching", true);
					manager.reload();
					e.getWhoClicked().openInventory(InventoryManager.getInventory());
				}
			} else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Current")) {
				Player p = (Player) e.getWhoClicked();
				StateManager.addPlayer(p, States.BLOCK_SET);
				p.sendMessage(ChatColor.GOLD + "LEFT CLICK " + ChatColor.GREEN + "a block in your main hand you wish to set as Launchpad");
				e.getWhoClicked().closeInventory();
			} else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Spawn Particles")) {
			
				if(manager.getBoolean("block_particles")) {
					manager.setBoolean("block_particles", false);
					manager.reload();
					e.getWhoClicked().openInventory(InventoryManager.getInventory());
				} else {
					manager.setBoolean("block_particles", true);
					manager.reload();
					e.getWhoClicked().openInventory(InventoryManager.getInventory());
				}
			}
			e.setCancelled(true);
		}
	}

}
