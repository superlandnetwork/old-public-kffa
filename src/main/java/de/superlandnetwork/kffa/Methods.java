//  _  __  ___   ___     _   
// | |/ / | __| | __|   /_\  
// | ' <  | _|  | _|   / _ \ 
// |_|\_\ |_|   |_|   /_/ \_\
//
// Copyright (C) 2017 - 2018 Filli IT (Einzelunternehmen) & Ursin Filli - All Rights Reserverd
// Unauthorized copying of the this file, via any medium is strictly prohibited
// Proprietary and confidential
// Written by Ursin Filli <ursin.filli@Filli-IT.ch>

package de.superlandnetwork.kffa;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import de.superlandnetwork.API.PlayerAPI.PermEnum;
import de.superlandnetwork.API.PlayerAPI.PlayerAPI;
import de.superlandnetwork.API.Utils.ScorbordManager;

public class Methods {

	public static void RegisterTeams(Player p) {
		Scoreboard bord = ScorbordManager.getScorebord(p);
		for(Team team : bord.getTeams()){
			team.unregister();
		}
		
		bord.registerNewTeam("0012Spieler").setPrefix(PermEnum.SPIELER.getTabList());
		bord.registerNewTeam("0011Premium").setPrefix(PermEnum.PREMIUM.getTabList());
		bord.registerNewTeam("0010PremiumPlus").setPrefix(PermEnum.PREMIUMPLUS.getTabList());
		bord.registerNewTeam("009YouTube").setPrefix(PermEnum.YOUTUBER.getTabList());
		bord.registerNewTeam("0008Builder").setPrefix(PermEnum.BUILDER.getTabList());
		bord.registerNewTeam("0008Builderin").setPrefix(PermEnum.BUILDERIN.getTabList());
		bord.registerNewTeam("0008Helfer").setPrefix(PermEnum.HELFER.getTabList());
		bord.registerNewTeam("0008Helferin").setPrefix(PermEnum.HELFERIN.getTabList());
		bord.registerNewTeam("0006Supporter").setPrefix(PermEnum.SUPPORTER.getTabList());
		bord.registerNewTeam("0006Supporterin").setPrefix(PermEnum.SUPPORTERIN.getTabList());
		bord.registerNewTeam("0005Moderator").setPrefix(PermEnum.MODERATOR.getTabList());
		bord.registerNewTeam("0005Moderatorin").setPrefix(PermEnum.MODERATORIN.getTabList());
		bord.registerNewTeam("0004SrModerator").setPrefix(PermEnum.SRMODERATOR.getTabList());
		bord.registerNewTeam("0004SrModeratin").setPrefix(PermEnum.SRMODERATORIN.getTabList());
		bord.registerNewTeam("0003Devloper").setPrefix(PermEnum.DEVELOPER.getTabList());
		bord.registerNewTeam("0003Devloperin").setPrefix(PermEnum.DEVELOPERIN.getTabList());
		bord.registerNewTeam("0002Admin").setPrefix(PermEnum.ADMINISTRATOR.getTabList());
		bord.registerNewTeam("0002Adminin").setPrefix(PermEnum.ADMINISTRATORIN.getTabList());
		bord.registerNewTeam("0001Owner").setPrefix(PermEnum.OWNER.getTabList());
		bord.registerNewTeam("0001Ownerin").setPrefix(PermEnum.OWNERIN.getTabList());
	}
	
	@SuppressWarnings("deprecation")
	public static void setPrefix(Player player) {
		Scoreboard bord = player.getScoreboard();
		for(Player all : Bukkit.getOnlinePlayers()) {
			String team = "0012Spieler";
			UUID UUID = all.getUniqueId();
			PlayerAPI api = new PlayerAPI(UUID);
			if(api.IsPlayerInGroup(PermEnum.PREMIUM.getId())) {
				team = "0011Premium";
			} else if(api.IsPlayerInGroup(PermEnum.PREMIUMPLUS.getId())) {
				team = "0010PremiumPlus";
			} else if(api.IsPlayerInGroup(PermEnum.YOUTUBER.getId())) {
				team = "0009YouTube";
			} else if(api.IsPlayerInGroup(PermEnum.BUILDER.getId())) {
				team = "0008Builder";
			} else if(api.IsPlayerInGroup(PermEnum.BUILDERIN.getId())) {
				team = "0008Builderin";
			} else if(api.IsPlayerInGroup(PermEnum.HELFER.getId())) {
				team = "0008Helfer";
			} else if(api.IsPlayerInGroup(PermEnum.HELFERIN.getId())) {
				team = "0008Helferin";
			} else if(api.IsPlayerInGroup(PermEnum.SUPPORTER.getId())) {
				team = "0006Supporter";
			} else if(api.IsPlayerInGroup(PermEnum.SUPPORTERIN.getId())) {
				team = "0006Supporterin";
			} else if(api.IsPlayerInGroup(PermEnum.MODERATOR.getId())) {
				team = "0005Moderator";
			} else if(api.IsPlayerInGroup(PermEnum.MODERATORIN.getId())) {
				team = "0005Moderatorin";
			} else if(api.IsPlayerInGroup(PermEnum.SRMODERATOR.getId())) {
				team = "0004SrModerator";
			} else if(api.IsPlayerInGroup(PermEnum.SRMODERATORIN.getId())) {
				team = "0004SrModeratin";
			} else if(api.IsPlayerInGroup(PermEnum.DEVELOPER.getId())) {
				team = "0003Devloper";
			} else if(api.IsPlayerInGroup(PermEnum.DEVELOPERIN.getId())) {
				team = "0003Devloperin";
			} else if(api.IsPlayerInGroup(PermEnum.ADMINISTRATOR.getId())) {
				team = "0002Admin";
			} else if(api.IsPlayerInGroup(PermEnum.ADMINISTRATORIN.getId())) {
				team = "0002Adminin";
			} else if(api.IsPlayerInGroup(PermEnum.OWNER.getId())) {
				team = "0001Owner";
			} else if(api.IsPlayerInGroup(PermEnum.OWNERIN.getId())) {
				team = "0001Ownerin";
			}
			if(Main.getInstance().NickedPlayers.contains(UUID))
				team = "0011Premium";
			bord.getTeam(team).addPlayer(all);
		}
		player.setScoreboard(bord);
	}
	
}
