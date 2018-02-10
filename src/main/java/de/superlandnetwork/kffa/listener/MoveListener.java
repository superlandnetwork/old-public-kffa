//  _  __  ___   ___     _   
// | |/ / | __| | __|   /_\  
// | ' <  | _|  | _|   / _ \ 
// |_|\_\ |_|   |_|   /_/ \_\
//
// Copyright (C) Filli-IT (Einzelunternehmen) & Ursin Filli - All Rights Reserverd
// Unauthorized copying of the this file, via any medium is strictly prohibited
// Proprietary and confidential
// Written by Ursin Filli <ursin.filli@Filli-IT.ch>

package de.superlandnetwork.kffa.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.superlandnetwork.kffa.Main;

public class MoveListener implements Listener{

	@EventHandler
	public void onMove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if (p.getLocation().getY() >= Main.TopGround.get(Main.MapID)){
			if (DamageListener.lastDamager.containsKey(p)){
				DamageListener.lastDamager.remove(p);
			}
			p.teleport(Main.Spawn.get(Main.MapID));
			p.setGameMode(GameMode.ADVENTURE);
			p.setFoodLevel(20);
			p.setHealth(20.0D);
			p.sendMessage("§7[§3KnockbackFFA§7] §7Du §7bist §7gestorben.");
		} else if (p.getLocation().getY() <= Main.Ground.get(Main.MapID)){
			if (!p.isDead()) {
				p.setHealth(0.0D);
				Location loc = p.getLocation();
				loc.subtract(0.0D, 250.0D, 0.0D);
				p.teleport(loc);
			}
		}
	}
	
}
