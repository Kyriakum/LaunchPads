package javacle.com;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryManager {

	private static String name;
	private static Inventory inv;
	private BlockManager mng;
	private ConfigManager cnf;
	public InventoryManager(Main main) {
		cnf = new ConfigManager(main);
		mng = new BlockManager(main);
	}

	public static Inventory getInventory() {
		return inv;
	}
	
	public void setItem(int slot, ItemStack item) {
		inv.setItem(slot, item);
		
	}
		
	public ItemStack createItemStack(Material mat, String name, String lore1, String lore2) {
		ItemStack is = new ItemStack(mat, 1);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(name);
		List<String> strings = new ArrayList<String>();
		strings.add(lore1);
		strings.add(lore2);
		meta.setLore(strings);
		is.setItemMeta(meta);
		return is;
	}
	
	public void setupInvs() {
		name =  ChatColor.BLUE + "LaunchPads+ " +ChatColor.RED + "" + ChatColor.BOLD +  "Config";
		 inv = Bukkit.createInventory(null, 3*9, name);
		setItem(11, createItemStack(mng.getPadMaterial(), ChatColor.GRAY + "Current " + ChatColor.GREEN + "Launchpad "+ ChatColor.GRAY + "Block:", ChatColor.GRAY + "Block: " + ChatColor.BLUE + mng.getPadMaterial().toString(), ChatColor.GOLD+"CLICK ME " + ChatColor.GRAY + "to change Block"));
		if(cnf.getBoolean("permission_needed")) {
			setItem(13, createItemStack(Material.LIME_DYE, ChatColor.GRAY + "Permission Needed: ", ChatColor.GRAY + "Enabled: " + ChatColor.GREEN + "True", ChatColor.GOLD+"CLICK ME " + ChatColor.GRAY + "to change State"));
		} else {
			setItem(13, createItemStack(Material.GRAY_DYE, ChatColor.GRAY + "Permission Needed: ", ChatColor.GRAY + "Enabled: " + ChatColor.RED + "False", ChatColor.GOLD+"CLICK ME " + ChatColor.GRAY + "to change State"));
		}
		if(cnf.getBoolean("apply_touching")) {
			setItem(15, createItemStack(Material.LIME_DYE, ChatColor.GRAY + "Apply Pad Effect to touching Blocks: ", ChatColor.GRAY + "Enabled: " + ChatColor.GREEN + "True", ChatColor.GOLD+"CLICK ME " + ChatColor.GRAY + "to change State"));
		} else {
			setItem(15, createItemStack(Material.GRAY_DYE, ChatColor.GRAY + "Apply Pad Effect to touching Blocks: ", ChatColor.GRAY + "Enabled: " + ChatColor.RED + "False", ChatColor.GOLD+"CLICK ME " + ChatColor.GRAY + "to change State"));
		}
		if(cnf.getBoolean("block_particles")) {
			setItem(4, createItemStack(Material.LIME_DYE, ChatColor.GRAY + "Spawn Particles on LaunchPads: ", ChatColor.GRAY + "Enabled: " + ChatColor.GREEN + "True", ChatColor.GOLD+"CLICK ME " + ChatColor.GRAY + "to change State"));
		} else {
			setItem(4, createItemStack(Material.GRAY_DYE, ChatColor.GRAY + "Spawn Particles on LaunchPads: ", ChatColor.GRAY + "Enabled: " + ChatColor.RED + "False", ChatColor.GOLD+"CLICK ME " + ChatColor.GRAY + "to change State"));
		}
	}
	public static String getName() {
		return name;
	}
}
