//  _  __  ___   ___     _   
// | |/ / | __| | __|   /_\  
// | ' <  | _|  | _|   / _ \ 
// |_|\_\ |_|   |_|   /_/ \_\
//
// Copyright (C) 2017 - 2018 Filli IT (Einzelunternehmen) & Ursin Filli - All Rights Reserverd
// Unauthorized copying of the this file, via any medium is strictly prohibited
// Proprietary and confidential
// Written by Ursin Filli <ursin.filli@Filli-IT.ch>

package de.superlandnetwork.kffa.listener;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import de.superlandnetwork.API.PlayerAPI.PermEnum;
import de.superlandnetwork.API.PlayerAPI.PlayerAPI;
import de.superlandnetwork.API.StatsAPI.StatsAPI;
import de.superlandnetwork.API.StatsAPI.StatsEnum;
import de.superlandnetwork.kffa.Main;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;

public class DeathListener implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		Player k = (Player)DamageListener.lastDamager.get(p);
		e.setDeathMessage(null);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			public void run()
			{
				PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
				((CraftPlayer)p).getHandle().playerConnection.a(packet);
			}
		}, 1);
		e.setKeepInventory(true);
		if (!DamageListener.lastDamager.containsKey(p)) {
			p.sendMessage("§7[§3KnockbackFFA§7] §7Du §7bist §7gestorben.");
			StatsAPI sapi = new StatsAPI(p.getUniqueId());
			sapi.setStates(StatsEnum.KFFA_DEATHS, Main.Deaths.get(p) + 1);
			Main.Deaths.replace(p, Main.Deaths.get(p) + 1);
			if (DamageListener.lastDamager.containsKey(p)) {
				DamageListener.lastDamager.remove(p);
			}
			if (Main.KillStreak.containsKey(p)) {
				Main.KillStreak.remove(p);
			}
		} else {
			if(e.getEntity().getLastDamageCause().getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.VOID) {
				p.sendMessage("§7[§3KnockbackFFA§7] §7Du §7bist §7gestorben.");
				StatsAPI sapi = new StatsAPI(p.getUniqueId());
				sapi.setStates(StatsEnum.KFFA_DEATHS, Main.Deaths.get(p) + 1);
				Main.Deaths.replace(p, Main.Deaths.get(p) + 1);
				if (DamageListener.lastDamager.containsKey(p)) {
					DamageListener.lastDamager.remove(p);
				}
				if (Main.KillStreak.containsKey(p)) {
					Main.KillStreak.remove(p);
				}
			} else {
				DamageListener.lastDamager.remove(p);
				if(Main.KillStreak.containsKey(p)){
					Main.KillStreak.remove(p);
				}
				PlayerAPI pAPI = new PlayerAPI(p.getUniqueId());
				PlayerAPI kAPI = new PlayerAPI(k.getUniqueId());
				if(Main.getInstance().NickedPlayers.contains(k.getUniqueId())) {
					p.sendMessage("§7[§3KnockbackFFA§7] §7Du §7wurdest §7von " + PermEnum.PREMIUM.getTabList() + k.getName() + " §7getötet.");
				} else {
					p.sendMessage("§7[§3KnockbackFFA§7] §7Du §7wurdest §7von " + kAPI.getTabPrefix() + " §7getötet.");
				}
				if(Main.getInstance().NickedPlayers.contains(p.getUniqueId())) {
					k.sendMessage("§7[§3KnockbackFFA§7] §7Du §7hast " + PermEnum.PREMIUM.getTabList() + p.getName() + " §7getötet.");
				} else {
					k.sendMessage("§7[§3KnockbackFFA§7] §7Du §7hast " + pAPI.getTabPrefix() + " §7getötet.");
				}
				if (Main.KillStreak.containsKey(k)) {
					int i = Main.KillStreak.get(k)+1;
					Main.KillStreak.remove(k);
					Main.KillStreak.put(k, i);
				} else {
					Main.KillStreak.put(k, 1);
				}
				PlayerAPI api = new PlayerAPI(k.getUniqueId());
				api.addCoins(20);
				StatsAPI sapi = new StatsAPI(p.getUniqueId());
				sapi.setStates(StatsEnum.KFFA_DEATHS, Main.Deaths.get(p) + 1);
				Main.Deaths.replace(p, Main.Deaths.get(p) + 1);
				StatsAPI sapi2 = new StatsAPI(k.getUniqueId());
				sapi2.setStates(StatsEnum.KFFA_KILLS, Main.Kills.get(k) + 1);
				Main.Kills.replace(k, Main.Kills.get(k) + 1);
				//-----------------
				String name = k.getName();
				if(Main.getInstance().NickedPlayers.contains(p.getUniqueId())) {
					name = PermEnum.PREMIUM.getTabList() + p.getName();
				} else {
					name = kAPI.getTabPrefix();
				}
				if(Main.KillStreak.get(k) == 5){
					Bukkit.broadcastMessage("§7[§3KnockbackFFA§7] " + name + " §7Hat §7eine §15er §7Killstreak");
				}
				if(Main.KillStreak.get(k) == 10){
					Bukkit.broadcastMessage("§7[§3KnockbackFFA§7] " + name + " §7Hat §7eine §a10er §7Killstreak");
				}
				if(Main.KillStreak.get(k) == 15){
					Bukkit.broadcastMessage("§7[§3KnockbackFFA§7] " + name + " §7Hat §7eine §315er §7Killstreak");
				}
				if(Main.KillStreak.get(k) == 20){
					Bukkit.broadcastMessage("§7[§3KnockbackFFA§7] " + name + " §7Hat §7eine §420er §7Killstreak");
				}
				if(Main.KillStreak.get(k) == 25){
					Bukkit.broadcastMessage("§7[§3KnockbackFFA§7] " + name + " §7Hat §7eine §525er §7Killstreak");
				}
				if(Main.KillStreak.get(k) == 30){
					Bukkit.broadcastMessage("§7[§3KnockbackFFA§7] " + name + " §7Hat §7eine §630er §7Killstreak");
				}
				if(Main.KillStreak.get(k) == 35){
					Bukkit.broadcastMessage("§7[§3KnockbackFFA§7] " + name + " §7Hat §7eine §635er §7Killstreak");
				}
				if(Main.KillStreak.get(k) == 40){
					Bukkit.broadcastMessage("§7[§3KnockbackFFA§7] " + name + " §7Hat §7eine §640er §7Killstreak");
				}
				if(Main.KillStreak.get(k) == 45){
					Bukkit.broadcastMessage("§7[§3KnockbackFFA§7] " + name + " §7Hat §7eine §645er §7Killstreak");
				}
				if(Main.KillStreak.get(k) == 50){
					Bukkit.broadcastMessage("§7[§3KnockbackFFA§7] " + name + " §7Hat §7eine §650er §7Killstreak");
				}
				if(Main.KillStreak.get(k) == 55){
					Bukkit.broadcastMessage("§7[§3KnockbackFFA§7] " + name + " §7Hat §7eine §455er §7Killstreak");
				}
				if(Main.KillStreak.get(k) == 60){
					Bukkit.broadcastMessage("§7[§3KnockbackFFA§7] " + name + " §7Hat §7eine §460er §7Killstreak");
				}
				if(Main.KillStreak.get(k) == 65){
					Bukkit.broadcastMessage("§7[§3KnockbackFFA§7] " + name + " §7Hat §7eine §665er §7Killstreak");
				}
				if(Main.KillStreak.get(k) == 70){
					Bukkit.broadcastMessage("§7[§3KnockbackFFA§7] " + name + " §7Hat §7eine §b70er §7Killstreak");
				}
				//-----------------
				k.playSound(k.getLocation(), Sound.LEVEL_UP, 10F, 1F);
			}
		}
		JoinListener.setItems(p);
		Main.SetScorbord(p);
		Main.SetScorbord(k);
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e){
		e.setRespawnLocation(Main.Spawn.get(Main.MapID));
		JoinListener.setItems(e.getPlayer());
	}
	
}
