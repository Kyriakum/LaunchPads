package javacle.com;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class BlockManager {

	
	private ConfigManager manager;
	public BlockManager(Main main) {
	
		manager = new ConfigManager(main);
		}
	
	public boolean containsBlock(Block block) {
		List<String> l = FileManager.getBlockConf().getStringList("blocks");
		return (l.contains("x" + block.getLocation().getBlockX()+ " y"+block.getLocation().getBlockY()+" z"+block.getLocation().getBlockZ()) ? true : false);
		
	}
	
	public boolean isSurroundedByPad(Block b) {
		if(containsBlock(b)) return true;
		boolean isTrue = false;
		if(!manager.getBoolean("apply_touching")) {
			return isTrue;
		}
		for(int x=-1; x<=1; x++) {
			for(int z=-1; z<=1; z++) {
				if(containsBlock(b.getRelative(x,0,z))) {
				isTrue = true;
				break;
				}
				
		}
			
	}
		return isTrue;
}
	
	
	public Material getPadMaterial() {
		Material mat = Material.getMaterial(manager.getString("block_material"));
		return mat;
	}
	
	public void setMaterial(Material mat) {
	
		manager.setString("block_material", mat.toString());
	
	}
	
	public void saveBlock(Block block) {
		File f = FileManager.getBlockFile();
		YamlConfiguration conf = FileManager.getBlockConf();
		if(containsBlock(block)) return;
		List<String> l = conf.getStringList("blocks");
		l.add("x" + block.getLocation().getBlockX()+ " y"+block.getLocation().getBlockY()+" z"+block.getLocation().getBlockZ());
		conf.set("blocks", l);
		try {
			conf.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeBlock(Block block) {
		File f = FileManager.getBlockFile();
		YamlConfiguration conf = FileManager.getBlockConf();
		List<String> l = conf.getStringList("blocks");
		String blockS = "x" + block.getLocation().getBlockX()+ " y"+block.getLocation().getBlockY()+" z"+block.getLocation().getBlockZ();
		if(l.contains(blockS)) l.remove(blockS);
		conf.set("blocks", l);
		try {
			conf.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changeBlocks(Player p) {
		File f = new File("plugins/LaunchPads/blocks.yml");
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(f);
		List<String> fa = conf.getStringList("blocks");	
		for(String aa : fa) {
			String bb = aa.replaceAll("x", "").replaceAll("y", "").replaceAll("z", "");
			String[] split = bb.split(" ");	
			int x = Integer.valueOf(split[0]);
			int y = Integer.valueOf(split[1]);
			int z = Integer.valueOf(split[2]);
			Block ba = new Location(p.getWorld(), x,y,z).getBlock();
			ba.setType(getPadMaterial());
			
		}
	}

	
	public void playParticle() {
		File f = new File("plugins/LaunchPads/blocks.yml");
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(f);
		List<String> fa = conf.getStringList("blocks");	
		for(String aa : fa) {
			String bb = aa.replaceAll("x", "").replaceAll("y", "").replaceAll("z", "");
			String[] split = bb.split(" ");	
			int x = Integer.valueOf(split[0]);
			int y = Integer.valueOf(split[1]);
			int z = Integer.valueOf(split[2]);
			if(Bukkit.getServer().getWorlds().get(0).getBlockAt(x,y,z).getType().toString().contains("PLATE")) {
				Bukkit.getServer().getWorlds().get(0).spawnParticle(Particle.VILLAGER_HAPPY, x+0.5, y, z+0.5, 2, 0.3, 0, 0.3);
			} else {	
			Bukkit.getServer().getWorlds().get(0).spawnParticle(Particle.VILLAGER_HAPPY, x+0.5, y+1, z+0.5, 2, 0.3, 0, 0.3);
			}
		
			
		
		} 
	}
}
