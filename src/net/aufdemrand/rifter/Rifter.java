package net.aufdemrand.rifter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Owner;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.java.JavaPlugin;

public class Rifter extends JavaPlugin {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {

		// Basic Handlers for Commands

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be in-game to execute commands.");
			return true;
		}

		if (args.length < 1) {
			sender.sendMessage(ChatColor.RED + "Use /rifter help for command reference.");
			return true;
		}

		Player player = (Player) sender;

		if (args[0].equalsIgnoreCase("help")) {
			player.sendMessage(ChatColor.GOLD + "----- Rifter -----");
			player.sendMessage(ChatColor.GREEN + "/rifter rift" + ChatColor.GRAY
					+ " -- Walkthrough for a new rift point.");
			return true;
		} 

		if (player.getMetadata("selected").isEmpty()) { 
			player.sendMessage(ChatColor.RED + "You must have a Rifter selected.");
			return true;
		}


		// Basics over, NPC Selected... but which one?

		int NPCSelected = player.getMetadata("selected").get(0).asInt();  // Gets NPC Citizens ID of selected
		NPC ThisNPC = CitizensAPI.getNPCManager().getNPC(player.getMetadata("selected").get(0).asInt());      // Gets NPC Citizens Entity of Selected
		Entity ThisBukkitNPC = ThisNPC.getBukkitEntity();  // Gets Bukkit Entity of Selected


		// Rifter Information Selected... now what?

		if (!ThisNPC.getTrait(Owner.class).getOwner().equals(player.getName())) {
			player.sendMessage(ChatColor.RED + "You must be the owner of the rifter to execute commands.");
			return true;
		}

		if (ThisNPC.getCharacter() == null || !ThisNPC.getCharacter().getName().equals("rifter")) {
			player.sendMessage(ChatColor.RED + "That command must be performed on a Rifter!");
			return true;
		}

		// Ownership confirmed, Rifter confirmed, now for commands!

		if (args[0].equalsIgnoreCase("save")) {
			player.sendMessage("Settings saved.");
			saveConfig();
			return true;
		}

		else if (args[0].equalsIgnoreCase("new") && args[1].equalsIgnoreCase("rift")) {

			//if (player.getMetadata("CreateRiftStatus").isEmpty()) { 

				player.sendMessage(ChatColor.RED + "-------- RIFT CREATER ----------");
				player.sendMessage(ChatColor.GREEN + "You have entered the " + ChatColor.MAGIC + "RIFT CREATER" + ChatColor.GREEN + ".");
				player.sendMessage(ChatColor.GREEN + "All chat is suspended for the duration");
				player.sendMessage(ChatColor.GREEN + "of the setup. Say 'exit setup' to cancel.");
				player.sendMessage(ChatColor.GREEN + "");
				ThisNPC.chat(player, "Where should the entry to the rift be located at?");
				player.sendMessage(ChatColor.GRAY + "Say 'here' to set the location beneath you.");

				String string1 = "This is a string.";
				
				player.setMetadata("CreateRiftStatus", new FixedMetadataValue(this, string1));

				return true;

			//}

			//else { 

			//	player.sendMessage(ChatColor.GRAY + "You must first finish the rift you are creating,");	
			//	player.sendMessage(ChatColor.GRAY + "or use '/rift creater reset' to start over.");

			//} 

			// return true;
		}

		else if (args[0].equalsIgnoreCase("creater") && args[1].equalsIgnoreCase("reset")) {
			
			player.removeMetadata("CreateRiftStatus", this);
			
			player.sendMessage(ChatColor.GRAY + "Rift creation process cleared.");
			player.sendMessage(ChatColor.GRAY + "Use '/rift create new' to start over.");
			
		}

		return true;
	}

	@Override
	public void onDisable() {
		getLogger().log(Level.INFO, " v" + getDescription().getVersion() + " disabled.");
	}

	@Override
	public void onEnable() {

		this.getConfig().options().copyDefaults(true);
		saveConfig();  

		CitizensAPI.getCharacterManager().register(RifterCharacter.class);
		getServer().getPluginManager().registerEvents(new RifterListener(this), this);


	}

}