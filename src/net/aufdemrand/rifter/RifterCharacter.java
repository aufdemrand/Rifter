package net.aufdemrand.rifter;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.citizensnpcs.api.exception.NPCLoadException;
import net.citizensnpcs.api.trait.Character;
import net.citizensnpcs.api.trait.SaveId;
import net.citizensnpcs.api.util.DataKey;
import net.citizensnpcs.api.exception.NPCLoadException;
import net.citizensnpcs.api.npc.NPC;

@SaveId("rifter")
public class RifterCharacter extends Character {
	
	Rifter plugin;
	public RifterCharacter(Rifter instance) { plugin = instance; }


	@Override
	public void load(DataKey arg0) throws NPCLoadException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(DataKey arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
    public void onRightClick(NPC npc, Player player) {
		
		if (!plugin.RifterInterface.containsKey(player) || plugin.RifterInterface.get(player).toString().equals("OK")) {
		
			Entity ThisBukkitNPC = npc.getBukkitEntity();  // Gets Bukkit Entity of Selected
	
			npc.chat(player, "Which rift shall I summon?");
			player.sendMessage(ChatColor.GRAY + "Say the name of the rift, or say 'list'");
			player.sendMessage(ChatColor.GRAY + "to see rift points available.");

			plugin.RifterInterface.put(player, "Waiting on which rift to summon.");
			
			return;
			
		}
		
	}
}
