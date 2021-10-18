package javacle.com;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
public class Main extends JavaPlugin {

	public void onEnable() {
		this.getConfig().options().copyDefaults(true);
		this.saveDefaultConfig();
	
					new SetupManager(this);
		
		
		ConfigManager cm = new ConfigManager(this);
		BlockManager bm = new BlockManager(this);
		
		if(cm.getBoolean("block_particles"))
		Bukkit.getScheduler().runTaskTimer(this, () -> {
			bm.playParticle();	
		},20,20);
}
}
