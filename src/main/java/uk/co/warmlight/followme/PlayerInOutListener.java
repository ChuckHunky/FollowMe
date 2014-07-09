package uk.co.warmlight.followme;


import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

public class PlayerInOutListener implements Listener {
	
	public static FollowMe plugin;
	
	public PlayerInOutListener(FollowMe instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		
		Player p = event.getPlayer();
		UUID u = p.getUniqueId();
		
		if (plugin.doesPlayerHaveTrail(u)) {
			Trail t = plugin.getPlayerTrail(u);
			t.stopRunnable();
			plugin.removeTrailFromPlayer(u);
		}
		
		
	}
}