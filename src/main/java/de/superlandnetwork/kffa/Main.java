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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import de.superlandnetwork.API.API;
import de.superlandnetwork.API.ServerAPI.Server;
import de.superlandnetwork.API.ServerAPI.ServerAPI;
import de.superlandnetwork.API.StatsAPI.StatsAPI;
import de.superlandnetwork.API.StatsAPI.StatsEnum;
import de.superlandnetwork.API.Utils.Nick;
import de.superlandnetwork.API.Utils.ScorbordManager;
import de.superlandnetwork.API.WorldAPI.WorldAPI;
import de.superlandnetwork.kffa.listener.ChatListener;
import de.superlandnetwork.kffa.listener.DamageListener;
import de.superlandnetwork.kffa.listener.DeathListener;
import de.superlandnetwork.kffa.listener.DisabledListener;
import de.superlandnetwork.kffa.listener.InteractListener;
import de.superlandnetwork.kffa.listener.JoinListener;
import de.superlandnetwork.kffa.listener.MoveListener;

public class Main extends JavaPlugin{

	public List<UUID> NickedPlayers = new ArrayList<>();
	public HashMap<UUID, Nick> NickInstances = new HashMap<>();

	private static Main instance;
		
	public static HashMap<Player, Integer> KillStreak = new HashMap<>();
	
	public static int MapID;
	public static HashMap<Integer, Location> Spawn = new HashMap<>();
	public static HashMap<Integer, Double> Ground = new HashMap<>();
	public static HashMap<Integer, Double> TopGround = new HashMap<>();
	
	public static HashMap<Player, Integer> Kills = new HashMap<>();
	public static HashMap<Player, Integer> Deaths = new HashMap<>();
	
	public Server server;
	
	public void onEnable() {
		MapID = 1;
		loadCords();
		instance = this;
		Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
		Bukkit.getPluginManager().registerEvents(new DisabledListener(), this);
		Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
		Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
		Bukkit.getPluginManager().registerEvents(new MoveListener(), this);
		Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
		CommandKFFA CMD = new CommandKFFA();
		getCommand("stats").setExecutor(CMD);
		getCommand("forcemap").setExecutor(CMD);
		getCommand("nick").setExecutor(CMD);
		/* KFFA1 */
		if(Bukkit.getWorld("KFFA") != null)
			Bukkit.unloadWorld("KFFA", true);
		WorldAPI w = new WorldAPI("KFFA");
		w.createCleanWorld();
		Bukkit.getWorld("KFFA").setAutoSave(false);
		Bukkit.getWorld("KFFA").setThundering(false);
		Bukkit.getWorld("KFFA").setStorm(false);
		Bukkit.getWorld("KFFA").setTime(0L);
		Bukkit.getWorld("KFFA").setGameRuleValue("doDaylightCycle", "false");
		Bukkit.getWorld("KFFA").setGameRuleValue("doFireTick", "false");//
		Bukkit.getWorld("KFFA").setGameRuleValue("doMobSpawning", "false");
		server = new Server(1, 1, 1, API.getInstance().ServerID, 1, true, 0, 16);
		new ServerAPI(server).update();
	}
	
	public void onDisable() {
		server.setOnline(false);
		new ServerAPI(server).update();
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	private void loadCords(){
		Spawn.put(1, new Location(Bukkit.getWorld("KFFA"), 0D, 96D, 0D));
		Spawn.put(2, new Location(Bukkit.getWorld("KFFA"), 1000D, 96D, 1000D));
		Spawn.put(3, new Location(Bukkit.getWorld("KFFA"), 2000D, 99D, 2000D));
		Ground.put(1, 43D);
		Ground.put(2, 43D);
		Ground.put(3, 43D);
		TopGround.put(1, 119D);
		TopGround.put(2, 119D);
		TopGround.put(3, 119D);
	}
	
	public static void SetScorbord(Player p){
		Scoreboard bord = ScorbordManager.getScorebord(p);
		if(bord.getObjective("aaa") != null) {
			bord.getObjective("aaa").unregister();
		}
		StatsAPI sAPI = new StatsAPI(p.getUniqueId());
		if(!Kills.containsKey(p))
			Kills.put(p, sAPI.getStates(StatsEnum.KFFA_KILLS));
		if(!Deaths.containsKey(p))
			Deaths.put(p, sAPI.getStates(StatsEnum.KFFA_DEATHS));
		Objective score = bord.registerNewObjective("aaa", "dumy");
		score.setDisplayName("§eSuperLandNetwork§7.§bde");
		score.setDisplaySlot(DisplaySlot.SIDEBAR);
		score.getScore("    ").setScore(12);
		score.getScore("§6Kills:").setScore(11);
		score.getScore("§c" + Kills.get(p)).setScore(10);
		score.getScore("   ").setScore(9);
		score.getScore("§6Tode:").setScore(8);
		score.getScore("§a" + Deaths.get(p)).setScore(7);
		score.getScore("  ").setScore(6);
		score.getScore("§6K/D:").setScore(5);
		if(Kills.get(p) != 0 && Deaths.get(p) != 0) {
			double kd = (double) Kills.get(p) / (double) Deaths.get(p);
			DecimalFormat twoDigit = new DecimalFormat("#,##0.00");
			kd = Double.valueOf(twoDigit.format(kd));
			score.getScore("§e" + kd).setScore(4);
		} else {
			score.getScore("§e" + 0.00).setScore(4);
		}
		score.getScore(" ").setScore(3);
		score.getScore("§6Map:").setScore(2);
		String MapName = "";
		if(MapID == 1)
			MapName = "KFFA1";
		if(MapID == 2)
			MapName = "KFFA2";
		if(MapID == 3)
			MapName = "KFFA3";
		score.getScore("§b" + MapName).setScore(1);
		p.setScoreboard(bord);
	}
}
