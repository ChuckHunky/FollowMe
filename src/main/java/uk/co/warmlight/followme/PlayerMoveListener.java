package uk.co.warmlight.followme;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PlayerMoveListener implements Listener {
	
	public static FollowMe plugin;
	
	public PlayerMoveListener(FollowMe instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
	
		Location loc = event.getPlayer().getLocation();
		UUID u = event.getPlayer().getUniqueId();
		if (plugin.doesPlayerHaveTrail(u)) {
			Trail t = plugin.getPlayerTrail(u);
		}
		
	}

}
