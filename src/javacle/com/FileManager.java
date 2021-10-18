package javacle.com;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {

	
	public static YamlConfiguration getBlockConf() {
		File f = getBlockFile();
		return YamlConfiguration.loadConfiguration(f);
	}
	
	public static File getBlockFile() {
		return new File("plugins/LaunchPads/blocks.yml");
		
	}
	
	public static void createConfig() {
		File f = new File("plugins/LaunchPads/blocks.yml");
		if(f.exists()) return;
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(f);
		try {
			f.createNewFile();
			List<String> list = new ArrayList<String>();
			conf.set("blocks", list);
			conf.save(f);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}
	
	
	
	public static void createFile() {
		
		File f = new File("plugins/LaunchPads");
		if(f.exists()) return;
		f.mkdir();
	}
}
