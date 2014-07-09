package uk.co.warmlight.followme;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ServerCommandExecutor implements CommandExecutor {
	
	public static FollowMe plugin;
	
	public ServerCommandExecutor(FollowMe instance) {
		plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("followme")) {
			
			if (sender instanceof Player) {
			
				if (args.length > 0) {
					
					Player p = (Player) sender;
					UUID u = p.getUniqueId();
						
					String param = args[0].toLowerCase();
							
					if (param.equals("new")) {
									
						Trail t = new Trail(u, plugin.getMaxTrailLength(), p);
						
						plugin.addTrailToPlayer(u, t);
						
						sender.sendMessage("Trail created");
	
						return true;
						
					} else if (param.equals("add")) {
						
						if (args.length == 2) {
							
							UUID addUUID;
							
							if (plugin.isUUIDCached(args[1])) {
								addUUID = plugin.getCachedUUID(args[1]);
							} else {
								addUUID = plugin.getUUIDFromName(args[1]);
								if (addUUID != null) {
									plugin.addUUIDToCache(args[1], addUUID);
								} else {
									sender.sendMessage("Cannot find " + args[1]);
									return true;
								}
							}
							
							Trail t = plugin.getPlayerTrail(u);
							
							if (t.addFollower(addUUID)) {
								sender.sendMessage(args[1] + " is now following you");
							} else {
								sender.sendMessage(args[1] + " is already following you");
							}
							
							return true;
						}
						
					} else if (param.equals("stop")) {
						
						Trail t = plugin.getPlayerTrail(u);
						t.stopRunnable();
						plugin.removeTrailFromPlayer(u);
						sender.sendMessage("Trail removed");
						return true;
						
					} else if (param.equals("remove")) {

						if (args.length == 2) {
							
							UUID addUUID;
							
							if (plugin.isUUIDCached(args[1])) {
								addUUID = plugin.getCachedUUID(args[1]);
							} else {
								addUUID = plugin.getUUIDFromName(args[1]);
								plugin.addUUIDToCache(args[1], addUUID);
							}
							
							Trail t = plugin.getPlayerTrail(u);
							
							if (t.removeFollower(addUUID)) {
								sender.sendMessage(args[1] + " is no longer following you");
							} else {
								sender.sendMessage(args[1] + " is not following you");
							}
							
							return true;
						}
						
					} else if (param.equals("followers")) {
						
						Trail t = plugin.getPlayerTrail(u);
						
						sender.sendMessage(t.getFollowers().toString());
						
						return true;
						
					}
					return false;
					
				} else {
					return false;
				}
			} else {
				sender.sendMessage("Only players can issue this command");
				return true;
			}
		}
			
		return false;
	}

}
