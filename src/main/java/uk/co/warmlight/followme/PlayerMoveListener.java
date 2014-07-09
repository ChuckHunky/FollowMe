package uk.co.warmlight.followme;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
	
	public static FollowMe plugin;
	
	public PlayerMoveListener(FollowMe instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		
		Player p = event.getPlayer();
		
		if (plugin.doesPlayerHaveTrail(p.getUniqueId())) {
			//p.sendBlockChange(p.getLocation(), Material.BEDROCK, (byte) 0);
		}
		
	}

}
