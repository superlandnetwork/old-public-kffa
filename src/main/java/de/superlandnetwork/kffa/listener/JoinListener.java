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

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.superlandnetwork.API.PlayerAPI.PermEnum;
import de.superlandnetwork.API.PlayerAPI.PlayerAPI;
import de.superlandnetwork.API.ServerAPI.ServerAPI;
import de.superlandnetwork.API.StatsAPI.StatsAPI;
import de.superlandnetwork.API.StatsAPI.StatsEnum;
import de.superlandnetwork.API.Utils.Nick;
import de.superlandnetwork.API.Utils.Tablist;
import de.superlandnetwork.kffa.Main;
import de.superlandnetwork.kffa.Methods;

public class JoinListener implements Listener{

	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		PlayerAPI api = new PlayerAPI(e.getPlayer().getUniqueId());
		if(e.getResult() == Result.KICK_FULL) {
			if(!api.IsPlayerInGroup(1) && !api.IsPlayerInGroup(2) && !api.IsPlayerInGroup(3) && !api.IsPlayerInGroup(4))
				e.allow();
		}
		if(api.AutoNick()) {
			Nick nick = new Nick(e.getPlayer());
			if(nick.nick()) {
				//OK
				Main.getInstance().NickedPlayers.add(e.getPlayer().getUniqueId());
				Main.getInstance().NickInstances.put(e.getPlayer().getUniqueId(), nick);
			} else {
				//Nick Failded 1/3
				if(nick.nick()) {
					//OK
					Main.getInstance().NickedPlayers.add(e.getPlayer().getUniqueId());
					Main.getInstance().NickInstances.put(e.getPlayer().getUniqueId(), nick);
				} else {
					//Nick Failded 2/3
					if(nick.nick()) {
						//OK
						Main.getInstance().NickedPlayers.add(e.getPlayer().getUniqueId());
						Main.getInstance().NickInstances.put(e.getPlayer().getUniqueId(), nick);
					} else {
						//Nick Failed 3/3
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		Methods.RegisterTeams(p);
		if(Main.getInstance().NickedPlayers.contains(p.getUniqueId())) {
			p.sendMessage("§7[§5NICK§7] §4Du spielst als §e" + Main.getInstance().NickInstances.get(p.getUniqueId()).getNick());
			p.sendMessage(" ");
		}
		PlayerAPI api = new PlayerAPI(p.getUniqueId());
		if(Main.getInstance().NickedPlayers.contains(p.getUniqueId()))
			e.setJoinMessage("§a» " + PermEnum.SPIELER.getTabList() + p.getName() + " §7hat §7den §7Server §7betreten.");
		else
			e.setJoinMessage("§a» " + api.getTabPrefix() + " §7hat §7den §7Server §7betreten.");
		p.teleport(Main.Spawn.get(Main.MapID));
		p.setGameMode(GameMode.ADVENTURE);
		p.setFoodLevel(20);
		p.setHealth(20.0D);
		new Tablist(p, "§eSuperLandNetwork.de Netzwerk §7- §aKnockbackFFA", "§7Teamspeak: §eTs.SuperLandNetwork.de \n §7Shop: §eShop.SuperLandNetwork.de");
		setItems(p);
		for(Player all : Bukkit.getOnlinePlayers()){
			Methods.setPrefix(all);
		}		
		Main.SetScorbord(p);
		StatsAPI sapi = new StatsAPI(p.getUniqueId());
		sapi.InsertUserInDB(StatsEnum.KFFA);
		Main.Deaths.put(p, sapi.getStates(StatsEnum.KFFA_DEATHS));
		Main.Kills.put(p, sapi.getStates(StatsEnum.KFFA_KILLS));
		Main.getInstance().server.setPlayers_Online(Bukkit.getOnlinePlayers().size());
		new ServerAPI(Main.getInstance().server).update();
		p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, Integer.MAX_VALUE, 1, true, false));
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		Player p = e.getPlayer();
		PlayerAPI api = new PlayerAPI(p.getUniqueId());
		if(Main.getInstance().NickedPlayers.contains(p.getUniqueId()))
			e.setQuitMessage("§c« " + PermEnum.SPIELER.getTabList() + p.getName() + " §7hat §7den §7Server §7verlassen.");
		else
			e.setQuitMessage("§c« " + api.getTabPrefix() + " §7hat §7den §7Server §7verlassen.");
		Main.Deaths.remove(p);
		Main.Kills.remove(p);
		if(Main.getInstance().NickedPlayers.contains(p.getUniqueId())) {
			Main.getInstance().NickedPlayers.remove(p.getUniqueId());
			Main.getInstance().NickInstances.remove(p.getUniqueId());
		}
		Main.getInstance().server.setPlayers_Online(Bukkit.getOnlinePlayers().size()-1);
		new ServerAPI(Main.getInstance().server).update();	
	}
	
	public static void setItems(Player p) {
		p.getInventory().clear();
		ItemStack stick = new ItemStack(Material.STICK, 1);
		ItemMeta stickmeta = stick.getItemMeta();
		stickmeta.setDisplayName("§bKnockbackStab");
		stickmeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
		stick.setItemMeta(stickmeta);
		p.getInventory().setItem(0, stick);
		ItemStack item2 = new ItemStack(Material.GOLD_INGOT);
		ItemMeta itemMeta = item2.getItemMeta();
		itemMeta.setDisplayName("§b§lShop");
		item2.setItemMeta(itemMeta);
		p.getInventory().setItem(8, item2);
	}
	
}
