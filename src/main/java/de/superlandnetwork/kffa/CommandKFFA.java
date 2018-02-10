//  _  __  ___   ___     _   
// | |/ / | __| | __|   /_\  
// | ' <  | _|  | _|   / _ \ 
// |_|\_\ |_|   |_|   /_/ \_\
//
// Copyright (C) Filli-IT (Einzelunternehmen) & Ursin Filli - All Rights Reserverd
// Unauthorized copying of the this file, via any medium is strictly prohibited
// Proprietary and confidential
// Written by Ursin Filli <ursin.filli@Filli-IT.ch>

package de.superlandnetwork.kffa;

import java.text.DecimalFormat;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.superlandnetwork.API.PlayerAPI.PlayerAPI;
import de.superlandnetwork.API.ServerAPI.ServerAPI;
import de.superlandnetwork.API.StatsAPI.StatsAPI;
import de.superlandnetwork.API.StatsAPI.StatsEnum;

public class CommandKFFA implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
			return true;
		Player p = (Player)sender;
		if(cmd.getName().equalsIgnoreCase("stats")) {
			
			StatsAPI api = new StatsAPI(p.getUniqueId());
			p.sendMessage("§9»§3 --§c ×§3 --§9 « §eStats §9»§3 --§c ×§3 --§9 «");
			int kills = api.getStates(StatsEnum.KFFA_KILLS);
			int deaths = api.getStates(StatsEnum.KFFA_DEATHS);
			if(kills != 0 && deaths != 0) {
				double kd = (double) kills / (double) deaths;
				DecimalFormat twoDigit = new DecimalFormat("#,##0.00");
				kd = Double.valueOf(twoDigit.format(kd));
				p.sendMessage("§9»§3 K§8/§3D§c " + kd);
			} else {
				p.sendMessage("§9»§3 K§8/§3D§c 0.00");
			}
			p.sendMessage("§9»§3 Rang§c " + Utils.getRank(p.getUniqueId()));
			p.sendMessage("§9»§3 Coins§c " + new PlayerAPI(p.getUniqueId()).getCoins());
			p.sendMessage("§9»§3 Tode§c " + deaths);
			p.sendMessage("§9»§3 Kills§c " + kills);
			p.sendMessage("§9»§3 --§c ×§3 --§9 « §eStats §9»§3 --§c ×§3 --§9 «");
		} else if(cmd.getName().equalsIgnoreCase("forcemap")) {
			PlayerAPI api = new PlayerAPI(p.getUniqueId());
			if(!api.IsPlayerInGroup(1) && !api.IsPlayerInGroup(2)){
				return true;
			}
			if(args.length != 1) {
				sender.sendMessage("§cMaps:");
				sender.sendMessage("§cKFFA1 - by SuperLandNetwork.de Team");
				sender.sendMessage("§cKFFA2 - by The_German_F");
				sender.sendMessage("§cKFFA3 - by SuperHuhnYT");
				sender.sendMessage("§cKFFA4 - by TexnoGaming & M4_LOL");
				sender.sendMessage("§c/forcemap <Map>");
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("KFFA1")) {
					if(Main.MapID == 1) {
						sender.sendMessage("§7[§3KnockbackFFA§7] §cKFFA1 wierd Bereits genutzt!");
					} else {
						Main.MapID = 1;
						Bukkit.broadcastMessage("§7[§3KnockbackFFA§7] §eDie Map wurde von §6" + sender.getName() + "§e zu §6KFFA1 gewechselt!");
						tp(1);
						Main.getInstance().server.setMapID(1);
						new ServerAPI(Main.getInstance().server).update();
					}
				} else if(args[0].equalsIgnoreCase("KFFA2")) {
					if(Main.MapID == 2) {
						sender.sendMessage("§7[§3KnockbackFFA§7] §cKFFA2 wierd Bereits genutzt!");
					} else {
						Main.MapID = 2;
						Bukkit.broadcastMessage("§7[§3KnockbackFFA§7] §eDie Map wurde von §6" + sender.getName() + "§e zu §6KFFA2 gewechselt!");
						tp(2);
						Main.getInstance().server.setMapID(2);
						new ServerAPI(Main.getInstance().server).update();
					}
				} else if(args[0].equalsIgnoreCase("KFFA3")) {
//					if(Main.MapID == 3) {
//						sender.sendMessage("§cKFFA3 wierd Bereits genutzt!");
//					} else {
//						Main.MapID = 3;
//						Bukkit.broadcastMessage("§cDie Map wurde von §6" + sender.getName() + "§c zu §6KFFA3 gewechselt!");
//						tp(3);
//						Main.getInstance().server.setMapID(3);
//						new ServerAPI(Main.getInstance().server).update();
//					}
				} else if(args[0].equalsIgnoreCase("KFFA4")) {
					if(Main.MapID == 4) {
						sender.sendMessage("§7[§3KnockbackFFA§7] §cKFFA4 wierd Bereits genutzt!");
					} else {
						Main.MapID = 4;		
						Bukkit.broadcastMessage("§7[§3KnockbackFFA§7] §eDie Map wurde von §6" + sender.getName() + "§e zu §6KFFA4 gewechselt!");
						tp(4);
						Main.getInstance().server.setMapID(4);
						new ServerAPI(Main.getInstance().server).update();
					}
				} else {
					sender.sendMessage("§cMaps:");
					sender.sendMessage("§cKFFA1 - by SuperLandNetwork.de Team");
					sender.sendMessage("§cKFFA2 - by The_German_F");
					sender.sendMessage("§cKFFA3 - by SuperHuhnYT");
					sender.sendMessage("§cKFFA4 - by TexnoGaming & M4_LOL");
					sender.sendMessage("§c/forcemap <Map>");
				}
			}
		} else if (cmd.getName().equalsIgnoreCase("nick")) {
			if (sender.hasPermission("ccl.nick")) {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("list")) {
						if (Main.getInstance().NickedPlayers.size() == 0) {
							sender.sendMessage("§7[§5NICK§7] §eEs §eist §eNimmand §eGenickt!");
							return true;
						}
						sender.sendMessage("§7[§5NICK§7] §6Genickte Spieler");
						for(UUID uuid : Main.getInstance().NickedPlayers) {
							if(uuid != null) {
								PlayerAPI api = new PlayerAPI(uuid);
								if(api.IsUserInDB1()) {
									sender.sendMessage("§7- " + api.getChatPrefix() + " : §6" + Main.getInstance().NickInstances.get(uuid).getNick());
								} else {
									sender.sendMessage("§7- §cFehler : §6" + Main.getInstance().NickInstances.get(uuid).getNick());
								}
							} else {
								sender.sendMessage("§7- §cFehler : §6" + Main.getInstance().NickInstances.get(uuid).getNick());
							}
						}
						return true;
					} else if (args[0].equalsIgnoreCase("unnick")) {
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.hidePlayer(((Player) sender));
						}
						Main.getInstance().NickInstances.get(((Player) sender).getUniqueId()).unNick();
						Main.getInstance().NickedPlayers.remove(((Player) sender).getUniqueId());
						Main.getInstance().NickInstances.remove(((Player) sender).getUniqueId());
						Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								for(Player all : Bukkit.getOnlinePlayers()) {
									all.showPlayer(((Player) sender));
								}
							}
						}, 20L);
						return true;
					} else {
						//TODO: Nick Command  Messages | KFFA - SLN
						//Help Anzeigen
						return true;
					}
				} else {
					//Help Anzeigen
					return true;
				}
			} else {
				//Keine Rechte
				return true;
			}
		}
		return true;
	}

	/**
	 * @param i
	 */
	protected void tp(int i) {
		for(Player all : Bukkit.getOnlinePlayers()) {
			all.teleport(Main.Spawn.get(i));
		}
	}
}
