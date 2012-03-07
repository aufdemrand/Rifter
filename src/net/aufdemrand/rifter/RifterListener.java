package net.aufdemrand.rifter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class RifterListener implements Listener {

	Rifter plugin;
	public RifterListener(Rifter instance) { plugin = instance; }

	@EventHandler
	public void RifterChatCommandListener(PlayerChatEvent event) {

		Player player = event.getPlayer();

		if (!plugin.RifterInterface.containsKey(player) || plugin.RifterInterface.get(player).toString().equals("OK")) {

			// Do nothing, no interference.

			return;
		}

		else if (plugin.RifterInterface.get(player).toString().equals("Waiting on name of rift.")) {

			// Let's get the name of the rift point we are to be working with.
			
			String IncomingText = event.getMessage();

			int NPCSelected = player.getMetadata("selected").get(0).asInt();  // Gets NPC Citizens ID of selected
			NPC ThisNPC = CitizensAPI.getNPCManager().getNPC(player.getMetadata("selected").get(0).asInt());  // Gets NPC Citizens Entity of Selected
			Entity ThisBukkitNPC = ThisNPC.getBukkitEntity();  // Gets Bukkit Entity of Selected

			if (IncomingText.length() > 40) { player.sendMessage(ChatColor.GRAY + "The name must be under 40 characters"); event.setCancelled(true); return; }
			
			plugin.CurrentPlayerRift.put(player, IncomingText);
			
			ThisNPC.chat(player, "That name will do nicely.");
			ThisNPC.chat(player, "Where should the entry to the rift be located at?");
			player.sendMessage(ChatColor.GRAY + "Say 'here' to set the block beneath you.");
			player.sendMessage(ChatColor.GRAY + "Say 'there' to set the block you are looking at.");

			plugin.RifterInterface.put(player, "Waiting on entry point.");

			event.setCancelled(true);
			
			return;
			
		}
		
		
		else if (plugin.RifterInterface.get(player).toString().equals("Waiting on entry point.")) {

			// Entry point interface

			String IncomingText = event.getMessage(); // Easier to remember String -- This is our incoming text.

			int NPCSelected = player.getMetadata("selected").get(0).asInt();  // Gets NPC Citizens ID of selected
			NPC ThisNPC = CitizensAPI.getNPCManager().getNPC(player.getMetadata("selected").get(0).asInt());  // Gets NPC Citizens Entity of Selected
			Entity ThisBukkitNPC = ThisNPC.getBukkitEntity();  // Gets Bukkit Entity of Selected

			if (IncomingText.contains("here")) { 

				plugin.getConfig().set("Stored Rifts." + plugin.CurrentPlayerRift.get(player) + ".Entry Location", player.getLocation().toString());
				plugin.saveConfig();
				
				ThisNPC.chat(player, "Okay, here will do nicely!");
				ThisNPC.chat(player, "Where should I make the rift exit?");
				player.sendMessage(ChatColor.GRAY + "Say 'here' to set the block beneath you.");
				player.sendMessage(ChatColor.GRAY + "Say 'there' to set the block you are looking at.");

				plugin.RifterInterface.put(player, "Waiting for exit point.");
	
				event.setCancelled(true);
				return;
			}

			else if (IncomingText.contains("there")) {

				plugin.getConfig().set("Stored Rifts." + plugin.CurrentPlayerRift.get(player) + ".Entry Location", player.getEyeLocation().toString());
				plugin.saveConfig();
				
				ThisNPC.chat(player, "Okay, there will do nicely!");
				
				ThisNPC.chat(player, "Now, where should I make the rift exit?");

				player.sendMessage(ChatColor.GRAY + "Say 'here' to set the block beneath you.");
				player.sendMessage(ChatColor.GRAY + "Say 'there' to set the block you are looking at.");

				
				
				plugin.RifterInterface.put(player, "Waiting for exit point.");
				
				event.setCancelled(true);
				return;
			}

			player.sendMessage("Waiting on a location for the rift. Type 'help' for help.");

			event.setCancelled(true);

			return;
		}
	

		// Now the exit point interface!
		
		else if (plugin.RifterInterface.get(player).toString().equals("Waiting for exit point.")) {

			// Exit point interface

			String IncomingText = event.getMessage(); // Easier to remember String -- This is our incoming text.

			int NPCSelected = player.getMetadata("selected").get(0).asInt();  // Gets NPC Citizens ID of selected
			NPC ThisNPC = CitizensAPI.getNPCManager().getNPC(player.getMetadata("selected").get(0).asInt());  // Gets NPC Citizens Entity of Selected
			Entity ThisBukkitNPC = ThisNPC.getBukkitEntity();  // Gets Bukkit Entity of Selected

			if (IncomingText.contains("here")) { 

				plugin.getConfig().set("Stored Rifts." + plugin.CurrentPlayerRift.get(player) + ".Exit Location", player.getLocation().toString());
				plugin.saveConfig();
				
				ThisNPC.chat(player, "Okay, here will do nicely!");
				ThisNPC.chat(player, "We're all finished up!");
			
				plugin.RifterInterface.put(player, "OK");
	
				event.setCancelled(true);
				return;
			}

			else if (IncomingText.contains("there")) {

				plugin.getConfig().set("Stored Rifts." + plugin.CurrentPlayerRift.get(player) + ".Exit Location", player.getEyeLocation().toString());
				plugin.saveConfig();
				
				ThisNPC.chat(player, "Okay, there will do nicely!");
				
				ThisNPC.chat(player, "We're all finished up!");

				plugin.RifterInterface.put(player, "OK");
				
				event.setCancelled(true);
				return;
			}

			player.sendMessage("Waiting on a location for the rift exit. Type 'help' for help.");

			event.setCancelled(true);

			return;
		}

		else if (plugin.RifterInterface.get(player).toString().equals("Waiting on which rift to summon.")) {
			

			String IncomingText = event.getMessage(); // Easier to remember String -- This is our incoming text.

			
			if (!plugin.getConfig().getString("Stored Rifts." + IncomingText + ".Entry Location").isEmpty()) { 
				
				plugin.getServer().broadcastMessage("message");

				 
				
			}
			
			
			
			
		}
		
		event.setCancelled(true);
	
		return;
	
	}


	@EventHandler
	public void RifterPortalListener(PlayerPortalEvent event) {



		plugin.getServer().broadcastMessage("Player entered Portal!");
		event.setCancelled(true);
	}

}