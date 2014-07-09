package uk.co.warmlight.followme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Trail {

	public static FollowMe plugin;
	private HashMap<UUID, Integer> followers = new HashMap<UUID, Integer>();
	private ArrayList<Location> nodes = new ArrayList<Location>();
	private Integer maxLength;
	private static Integer logInterval = 40; // Number of ticks delay between location log (~2 secs)
	private Integer runnableId;
		
	public Trail(UUID uuid, Integer max, final Player p) {
		this.maxLength = max;
		
		// Create the location logger as a runnable
		Integer id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("FollowMe"), new Runnable() {
			public void run() {
				addNode(p.getLocation());
			}
		}, 0, logInterval);
		
		this.runnableId = id;
	}
	
	public Boolean addFollower(UUID uuid) {
		if (this.followers.containsKey(uuid)) {
			return false;
		} else {
			this.followers.put(uuid, 1);
			return true;
		}
		
	}
	
	public Boolean removeFollower(UUID uuid) {
		if (this.followers.containsKey(uuid)) {
			this.followers.remove(uuid);
			return true;
		} else {
			return false;
		}
		
	}
	
	public void stopRunnable() {
		Bukkit.getScheduler().cancelTask(this.runnableId);
	}
	
	public Set<UUID> getFollowers() {
		return this.followers.keySet();
	}
	
	public void addNode(Location l) {
		// If we're at the limit of maximum nodes
		// remove the oldest before adding the new one
		if (this.nodes.size() == this.maxLength) {
			this.nodes.remove(0);
		}
		
		// We only add a node if the X, Y or Z are different
		// to the last node
		if (this.nodes.size() > 0) {	
			Integer lastElement = this.nodes.size() - 1;
			Location lastNode = this.nodes.get(lastElement);
			Double lastX = lastNode.getX();
			Double lastY = lastNode.getY();
			Double lastZ = lastNode.getZ();
			Double nowX = l.getX();
			Double nowY = l.getY();
			Double nowZ = l.getZ();
			Integer xEqual = Double.compare(lastX, nowX);
			Integer yEqual = Double.compare(lastY, nowY);
			Integer zEqual = Double.compare(lastZ, nowZ);
			if (xEqual != 0 || yEqual != 0 || zEqual != 0) {
				this.nodes.add(l);
			}
		} else  {
			this.nodes.add(l);
		}
		Bukkit.getPluginManager().getPlugin("FollowMe").getLogger().info(this.listNodes().toString());
	}
	
	public Integer listNodes() {
		//return this.nodes.toString();
		return this.nodes.size();
	}
	
	public Integer getTrailSize() {
		return this.nodes.size();
	}
	

}
