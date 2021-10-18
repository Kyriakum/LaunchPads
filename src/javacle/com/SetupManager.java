package javacle.com;

import org.bukkit.Bukkit;

import javacle.com.eventhandlers.BlocksEvent;
import javacle.com.eventhandlers.InventoryEvent;
import javacle.com.eventhandlers.MoveEvent;

public class SetupManager {

	private Main main;
	public SetupManager(Main main) {
		this.main = main;
		main.getCommand("launchpads").setExecutor(new Commands(main));
		main.getCommand("lpreload").setExecutor(new Commands(main));
		setupFile();
		new InventoryManager(main).setupInvs();;
		setupEvents();
		}
	
	public void setupEvents() {
		Bukkit.getPluginManager().registerEvents(new MoveEvent(main), main);
		Bukkit.getPluginManager().registerEvents(new InventoryEvent(main), main);
		Bukkit.getPluginManager().registerEvents(new BlocksEvent(main), main);
	}
	
	public void setupCommands() {
		
	}
	
	public void setupFile() {
		FileManager.createFile();
		FileManager.createConfig();
	}
}
