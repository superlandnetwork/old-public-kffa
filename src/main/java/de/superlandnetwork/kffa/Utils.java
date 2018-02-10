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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import de.superlandnetwork.API.API;

public class Utils {
	public static HashMap<Integer, UUID> getKillsOrdetDB(){
		HashMap<Integer, UUID> map = new HashMap<>();
		ResultSet rs = API.getInstance().getMySQL().getResult("SELECT * FROM `SLN_Stats_KFFA`  ORDER BY Kills DESC");
		try {
			int i = 1;
			while(rs.next()){
				map.put(i, UUID.fromString(rs.getString("UUID")));
				i++;
			}
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static int getRank(UUID uuid){
		HashMap<Integer, UUID> map = getKillsOrdetDB();
		for(int i=0; i<map.size(); i++){
			if(map.get(i) != uuid) continue;
			return i;
		}
		return 0;
	}
	
}
