package javacle.com;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javacle.com.states.StateManager;
import javacle.com.states.States;
import net.md_5.bungee.api.ChatColor;

public class Commands implements CommandExecutor {

	private ConfigManager manager;
	public Commands(Main main) {
		manager = new ConfigManager(main);
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("launchpads")) {
			if(!(sender instanceof Player)) return false;
			Player p = (Player) sender;
			if(!p.isOp() && !p.hasPermission("launchpads.admin")) {
				p.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			
			if(args.length < 1) {
				p.sendMessage(ChatColor.RED + "Wrong usage of /launchpads!");
				p.sendMessage(ChatColor.RED + "Try /launchpads (edit/config/reload)");
			return true;
			}
			
			
			if(args[0].equalsIgnoreCase("edit")) {
				if(StateManager.isOnEdit(p)) {
					p.sendMessage(ChatColor.GREEN + "You disabled EDIT mode!");
					StateManager.removePlayer(p);
					return true;
				} else {
					StateManager.addPlayer(p, States.EDIT);
					p.sendMessage(ChatColor.GREEN + "You enabled EDIT mode!");
					p.sendMessage(" ");
					p.sendMessage(ChatColor.GOLD + "Left Click "+ ChatColor.GREEN + "any block to set it as a Launchpad!");
					p.sendMessage(ChatColor.GOLD + "Right Click "+ ChatColor.GREEN + "any Launcpad to remove it!");
					return true;
				}
				
			} else if(args[0].equalsIgnoreCase("reload")) {
				manager.reload();
				p.sendMessage(ChatColor.GREEN + "LaunchPads+ reloaded successfully!");
				return true;
			} else if(args[0].equalsIgnoreCase("config")) {
				p.openInventory(InventoryManager.getInventory());
		}	 else {
				p.sendMessage(ChatColor.RED + "Wrong usage of /launchpads!");
				p.sendMessage(ChatColor.RED + "Try /launchpads (edit/config/reload)");
			return false;
			}
			} 
		if(cmd.getName().equalsIgnoreCase("lpreload")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(!p.isOp() && !p.hasPermission("launchpads.admin")) {
					p.sendMessage(ChatColor.RED + "You do not have access to that command!");
					return true;
				}
			}
			manager.reload();
			
			sender.sendMessage(ChatColor.GREEN + "LaunchPads+ reloaded successfully!");
		}
		
		return false;
	}

}
