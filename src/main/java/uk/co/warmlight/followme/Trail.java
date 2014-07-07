package uk.co.warmlight.followme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;

public class Trail {
	
	private UUID owner;
	private HashMap<UUID, Integer> followers = new HashMap<UUID, Integer>();
	private ArrayList<Location> nodes = new ArrayList<Location>();
	private Material marker;
	private Integer maxLength;
		
	public Trail(UUID uuid, Integer max) {
		this.maxLength = max;
		setOwner(uuid);
	}
	
	public void setOwner(UUID ownerUUID) {
		this.owner = ownerUUID;
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
	
	public Set<UUID> getFollowers() {
		return this.followers.keySet();
	}
	
	public void addNode(Location l) {
		if (this.nodes.size() < this.maxLength) {
			this.nodes.add(l);
		} else {
			this.nodes.remove(0);
			this.nodes.add(l);
		}
	}
	
	public Integer listNodes() {
		//return this.nodes.toString();
		return this.nodes.size();
	}
	
	public Integer getTrailSize() {
		return this.nodes.size();
	}
	

}
