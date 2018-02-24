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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import de.superlandnetwork.API.PlayerAPI.PlayerAPI;

public class InteractListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			return;
		}
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getMaterial() == Material.GOLD_INGOT) {
				if(e.getItem().getItemMeta().getDisplayName() == "§b§lShop"){
					List<String> list = new ArrayList<>();
					list.add("§cPreis: 50 Coins");
					List<String> list2 = new ArrayList<>();
					list2.add("§cPreis: 100 Coins");
					Inventory inv = Bukkit.createInventory(null, 9, "§b§lShop");
					ItemStack knock2= new ItemStack(Material.BLAZE_ROD);
					ItemMeta knock2meta = knock2.getItemMeta();
					knock2meta.setDisplayName("§b§lKnockbackStab II");
					knock2meta.setLore(list);
					knock2meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
					knock2.setItemMeta(knock2meta);
					
					ItemStack rod = new ItemStack(Material.FISHING_ROD);
					ItemMeta rodmeta = rod.getItemMeta();
					rodmeta.setDisplayName("§6§lFishing Rod");
					rodmeta.setLore(list);
					rod.setItemMeta(rodmeta);
					
					ItemStack Ep = new ItemStack(Material.ENDER_PEARL);
					ItemMeta epmeta = Ep.getItemMeta();
					epmeta.setDisplayName("§6§lEnderperle");
					epmeta.setLore(list);
					Ep.setItemMeta(epmeta);
					
					ItemStack poition1 = new ItemStack(Material.POTION, 1, (short) 8226);
					PotionMeta poition1Meta = (PotionMeta) poition1.getItemMeta();
					poition1Meta.setDisplayName("§6§lSpeed");
					poition1Meta.setLore(list);
					poition1.setItemMeta(poition1Meta);	
					
					ItemStack knock3 = new ItemStack(Material.BLAZE_ROD);
					ItemMeta knock3meta = knock3.getItemMeta();
					knock3meta.setDisplayName("§b§lKnockbackStab III");
					knock3meta.setLore(list2);
					knock3meta.addEnchant(Enchantment.KNOCKBACK, 3, true);
					knock3.setItemMeta(knock3meta);
					
					inv.setItem(0, knock2);
					inv.setItem(2, rod);
					inv.setItem(4, Ep);
					inv.setItem(6, poition1);
					inv.setItem(8, knock3);
					e.getPlayer().openInventory(inv);
					e.setCancelled(true);
					return;
				}
				return;
			}
			return;
		}
		return;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		if(p.getGameMode() == GameMode.CREATIVE) {
			return;
		}
		if(e.getInventory().getHolder() != null)
			return;
		if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lEnderperle")) {
			if(e.getInventory().getHolder() != null)
				return;
			e.getView().close();
			PlayerAPI api = new PlayerAPI(p.getUniqueId());
			ItemStack Ep = new ItemStack(Material.ENDER_PEARL);
			ItemMeta epmeta = Ep.getItemMeta();
			epmeta.setDisplayName("§6§lEnderperle");
			Ep.setItemMeta(epmeta);
			if (api.getCoins() >= 50) {
				p.getInventory().addItem(Ep);
				api.setCoins(api.getCoins() - 50);
				p.sendMessage("§7[§3KnockbackFFA§7] §7Du §7hast §7dir §7dieses §7Item §7erfolgreich §7gekauft.");
			} else {
				p.sendMessage("§7[§3KnockbackFFA§7] §7Du §7hast §7nicht §7genug §7Coins.");	
			}
			e.setCancelled(true);
			return;
		}
		if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§b§lKnockbackStab II") && e.getInventory().getHolder() == null){
			e.getView().close();
			PlayerAPI api = new PlayerAPI(p.getUniqueId());
			ItemStack knock3 = new ItemStack(Material.BLAZE_ROD);
			ItemMeta knock3meta = knock3.getItemMeta();
			knock3meta.setDisplayName("§b§lKnockbackStab II");
			knock3meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
			knock3.setItemMeta(knock3meta);
			if (api.getCoins() >= 50) {
				p.getInventory().setItem(0, knock3);
				api.setCoins(api.getCoins() - 50);
				p.sendMessage("§7[§3KnockbackFFA§7] §7Du §7hast §7dir §7dieses §7Item §7erfolgreich §7gekauft.");
			} else {
				p.sendMessage("§7[§3KnockbackFFA§7] §7Du §7hast §7nicht §7genug §7Coins.");	
			}
			e.setCancelled(true);
			return;
		}
		if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§b§lKnockbackStab III") && e.getInventory().getHolder() == null){
			e.getView().close();
			PlayerAPI api = new PlayerAPI(p.getUniqueId());
			ItemStack knock3 = new ItemStack(Material.BLAZE_ROD);
			ItemMeta knock3meta = knock3.getItemMeta();
			knock3meta.setDisplayName("§bKnockbackStab III");
			knock3meta.addEnchant(Enchantment.KNOCKBACK, 3, true);
			knock3.setItemMeta(knock3meta);
			if (api.getCoins() >= 100) {
				p.getInventory().setItem(0, knock3);
				api.setCoins(api.getCoins() - 100);
				p.sendMessage("§7[§3KnockbackFFA§7] §7Du §7hast §7dir §7dieses §7Item §7erfolgreich §7gekauft.");
			} else {
				p.sendMessage("§7[§3KnockbackFFA§7] §7Du §7hast §7nicht §7genug §7Coins.");	
			}
			e.setCancelled(true);
			return;
		}
		if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lFishing Rod") && e.getInventory().getHolder() == null){
			e.getView().close();
			PlayerAPI api = new PlayerAPI(p.getUniqueId());
			ItemStack knock3 = new ItemStack(Material.FISHING_ROD);
			if (api.getCoins() >= 50) {
				p.getInventory().addItem(knock3);
				api.setCoins(api.getCoins() - 50);
				p.sendMessage("§7[§3KnockbackFFA§7] §7Du §7hast §7dir §7dieses §7Item §7erfolgreich §7gekauft.");
			} else {
				p.sendMessage("§7[§3KnockbackFFA§7] §7Du §7hast §7nicht §7genug §7Coins.");	
			}
			e.setCancelled(true);
			return;
		}
		if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lSpeed") && e.getInventory().getHolder() == null){
			e.getView().close();
			PlayerAPI api = new PlayerAPI(p.getUniqueId());
			ItemStack poition1 = new ItemStack(Material.POTION, 1, (short) 8226);
			if (api.getCoins() >= 50) {
				p.getInventory().addItem(poition1);
				api.setCoins(api.getCoins() - 50);
				p.sendMessage("§7[§3KnockbackFFA§7] §7Du §7hast §7dir §7dieses §7Item §7erfolgreich §7gekauft.");
			} else {
				p.sendMessage("§7[§3KnockbackFFA§7] §7Du §7hast §7nicht §7genug §7Coins.");	
			}
			e.setCancelled(true);
			return;
		}
		return;
	}

}
