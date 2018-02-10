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

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

@SuppressWarnings("deprecation")
public class DisabledListener implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		e.setCancelled(true);
	}
	  
	@EventHandler
	public void onDrop(PlayerPickupItemEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onWheter(WeatherChangeEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onLeave(LeavesDecayEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onGrow(BlockGrowEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onIgnite(BlockIgniteEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPhysics(BlockPhysicsEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onCraft(CraftItemEvent e){
		e.setCancelled(true);
	}
	
}
