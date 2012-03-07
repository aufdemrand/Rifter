package net.aufdemrand.rifter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

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

		plugin.getServer().broadcastMessage("" + player.getMetadata("CreateRiftStatus").size());
		
		
		if (player.getMetadata("CreateRiftStatus").isEmpty()) { 
			return;
		}

		else if (player.getMetadata("CreateRiftStatus").contains("WAITING FOR ENTRY POINT")) {

			String IncomingText = event.getMessage();

			int NPCSelected = player.getMetadata("selected").get(0).asInt();  // Gets NPC Citizens ID of selected
			NPC ThisNPC = CitizensAPI.getNPCManager().getNPC(player.getMetadata("selected").get(0).asInt());      // Gets NPC Citizens Entity of Selected
			Entity ThisBukkitNPC = ThisNPC.getBukkitEntity();  // Gets Bukkit Entity of Selected

			if (IncomingText.contains("here")) { 

				ThisNPC.chat(player, "Okay, here will do nicely!");
				ThisNPC.chat(player, "Where should I make the rift exit?");
				
				player.setMetadata("CreateRiftStatus", new FixedMetadataValue(plugin, "WAITING FOR EXIT POINT"));
		        
				
				event.setCancelled(true);
			}

			player.sendMessage("Waiting on a location for the rift. Type 'help' for help.");

			event.setCancelled(true);

		}
	}


	@EventHandler
	public void RifterPortalListener(PlayerPortalEvent event) {



		plugin.getServer().broadcastMessage("Player entered Portal!");
		event.setCancelled(true);
	}

}