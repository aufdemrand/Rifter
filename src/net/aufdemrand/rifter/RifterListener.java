package net.aufdemrand.rifter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.metadata.MetadataValue;

public class RifterListener implements Listener {

	Rifter plugin;
	public RifterListener(Rifter instance) { plugin = instance; }
	
	@EventHandler
    public void RifterChatListener(PlayerChatEvent event) {     // Now we're listening to chat.

		Player player = event.getPlayer();   // Get the player name
 
		Location PlayerLoc = player.getLocation();

	}

	public void RifterPortalListener(PlayerPortalEvent event) {
		
	}
	
}