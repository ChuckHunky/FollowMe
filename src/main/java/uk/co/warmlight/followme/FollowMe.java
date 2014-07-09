package uk.co.warmlight.followme;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.warmlight.followme.UUIDFetcher;
import uk.co.warmlight.followme.ServerCommandExecutor;
import uk.co.warmlight.followme.PlayerMoveListener;

public class FollowMe extends JavaPlugin {
	
	private static Integer maxTrailLength = 10;
	private HashMap<UUID, Trail> playerTrails = new HashMap<UUID, Trail>();
	private HashMap<String, UUID> playerUUIDCache = new HashMap<String, UUID>();

	
	public final PlayerMoveListener playerMoveListener = new PlayerMoveListener(this);
	public final PlayerInOutListener playerInOutListener = new PlayerInOutListener(this);
	
	@Override
	public void onEnable() {
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(playerMoveListener, this);
		pm.registerEvents(playerInOutListener, this);
		
		getCommand("followme").setExecutor(new ServerCommandExecutor(this));
		
	}
	
	public void onDisable() {
		
	}
	
	public Integer getMaxTrailLength() {
		return maxTrailLength;
	}
	
	public void addTrailToPlayer(UUID u, Trail t) {
		playerTrails.put(u,t);
	}
	
	public void removeTrailFromPlayer(UUID u) {
		playerTrails.remove(u);
	}
	
	public Boolean doesPlayerHaveTrail(UUID u) {
		if (playerTrails.containsKey(u)) {
			return true;
		} else {
			return false;
		}
	}
	
	public Trail getPlayerTrail(UUID u) {
		// If a player doesn't have a trail when trying to
		// access, create one for them
		if (this.doesPlayerHaveTrail(u)) {
			return playerTrails.get(u);
		} else {
			Player p = Bukkit.getPlayer(u);
			Trail t = new Trail(u, this.getMaxTrailLength(), p);
			this.addTrailToPlayer(u, t);
			return t;
		}
	}
	
	public UUID getUUIDFromName(String pName) {
		UUIDFetcher fetcher = new UUIDFetcher(Arrays.asList(pName));
		Map<String, UUID> response = null;
		try {
			response = fetcher.call();
			if (!response.isEmpty()) {
				UUID uuid = response.values().iterator().next();
				return uuid;
			}
		} catch (Exception e) {
			this.getLogger().info("[Followme] Unable to contact Mojang UUID lookup");
		}
		return null;
	}
	
	public void addUUIDToCache(String name, UUID uuid) {
		this.playerUUIDCache.put(name, uuid);
	}
	
	public Boolean isUUIDCached(String pName) {
		return (this.playerUUIDCache.containsKey(pName)) ? true : false;
	}
	
	public UUID getCachedUUID(String pName) {
		return this.playerUUIDCache.get(pName);
	}

}
