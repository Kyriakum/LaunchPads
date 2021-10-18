package javacle.com.eventhandlers;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import javacle.com.BlockManager;
import javacle.com.ConfigManager;
import javacle.com.InventoryManager;
import javacle.com.Main;
import javacle.com.ClassImp;
import javacle.com.events.PlayerLaunchedEvent;
import javacle.com.states.StateManager;
import javacle.com.states.States;
import net.md_5.bungee.api.ChatColor;

public class MoveEvent extends ClassImp implements Listener {

	private Main main = getMain();
	private ConfigManager manager = getManager();
	private BlockManager bm = getBm();
	public MoveEvent(Main main) {
		super(main);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		if(!p.isOp() && !p.hasPermission("launchpads.usage") && manager.getBoolean("permission_needed")) return;
				if(StateManager.isLaunched(p) && ((e.getTo().getBlock().isLiquid()) || p.isOnGround()) || StateManager.isLaunched(p) && p.isFlying()) {
					if(e.getTo().getBlock().getType() == bm.getPadMaterial()) {
						return;
					} else {
						new BukkitRunnable() {
							
							@Override
							public void run() {
								StateManager.removePlayer(p);
							}
						}.runTaskLater(main, 10);
					}
				}			
		Block b;
		if(bm.getPadMaterial().toString().contains("PLATE")) {
			b = p.getLocation().getBlock();	
		} else {
		b = p.getLocation().subtract(0,1,0).getBlock();
		}
		if(b.getType() == bm.getPadMaterial() && bm.isSurroundedByPad(b)) {
			if(StateManager.isLaunched(p)) return;
			Bukkit.getPluginManager().callEvent(new PlayerLaunchedEvent(p));
			}
	}

	@EventHandler
	public void onPlayerLaunch(PlayerLaunchedEvent e) {
		Player p = e.getPlayer();
		StateManager.addPlayer(p, States.LAUNCHED);
		Vector v = p.getEyeLocation().getDirection();
		Vector ea = v.clone().multiply(1.3).setY(1.43);
		p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SHOOT,  1.4f, 1);
		p.setVelocity(ea);
		
	}				
		
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onDamage(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		
		Player p = (Player) e.getEntity();
		if(StateManager.isLaunched(p)){
		
		if (e.getCause() == DamageCause.FALL) 
		{
			e.setCancelled(true);
			if(e.getEntity().getLocation().getBlock().getLocation().subtract(0,1,0).getBlock().getType() == bm.getPadMaterial()) return;
			StateManager.removePlayer(p);
			
		}
		}
	}
	
	
	
	@EventHandler
	public void onInter(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(StateManager.isOnBSet(p)) {
			if(e.getHand() == EquipmentSlot.HAND) {
			if((e.getAction() == Action.LEFT_CLICK_BLOCK) || (e.getAction()==Action.LEFT_CLICK_AIR)) {
				if(p.getInventory().getItemInMainHand().getType().isAir() | !(p.getInventory().getItemInMainHand().getType().isBlock())) {
					p.sendMessage(ChatColor.RED + "This is not a block!");
					p.openInventory(InventoryManager.getInventory());
				} else {
					p.sendMessage(ChatColor.GREEN + "New block successfully set!");
					bm.setMaterial(p.getInventory().getItemInMainHand().getType());
					manager.reload();
					bm.changeBlocks(p);
					p.openInventory(InventoryManager.getInventory());
					
				}
				StateManager.removePlayer(p);
				}
			}
		}
		
	}
	
	@EventHandler
	public void onEdit(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(StateManager.isOnEdit(p)) {
			if(e.getHand() == EquipmentSlot.HAND) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
		if(!bm.containsBlock(e.getClickedBlock())) {
			p.sendMessage(ChatColor.RED + "This block is not a Launchpad!");
			return;
		} else {
			bm.removeBlock(e.getClickedBlock());
			p.sendMessage(ChatColor.GREEN + "Launchpad was removed!");
		}
		}
		}
		}
	}
		
}
