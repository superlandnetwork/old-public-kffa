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

import java.util.HashMap;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

	public static HashMap<Player, Player> lastDamager = new HashMap<Player, Player>();
	
	@EventHandler
	public void onDamge(EntityDamageEvent e){
		if(e.getEntityType() == EntityType.PLAYER){
			if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDamgeByEntity(EntityDamageByEntityEvent e){
		if(e.getEntityType() == EntityType.PLAYER){
			if(e.getEntity().getLocation().getY() >= 81D){
				e.setCancelled(true);
				return;
			}
			if(e.getDamager().getType() != EntityType.PLAYER)
				return;
			Player p = (Player)e.getEntity();
			Player killer = (Player)e.getDamager();
			if(lastDamager.containsKey(p)){
				lastDamager.remove(p);
				lastDamager.put(p, killer);
			}else{
				lastDamager.put(p, killer);
			}
		}
	}
}
