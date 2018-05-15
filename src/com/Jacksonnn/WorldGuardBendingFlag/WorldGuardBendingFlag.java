package com.Jacksonnn.WorldGuardBendingFlag;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class WorldGuardBendingFlag extends JavaPlugin implements Listener {
	
	public WorldGuardPlugin wgPlugin;
	private ArrayList<Player> entered = new ArrayList<>();
    private ArrayList<Player> left = new ArrayList<>();
	
	public void onEnable() {
		wgPlugin = getWorldGuard();
	}
	
	public void enterRegion(Player player) {
        LocalPlayer localPlayer = wgPlugin.wrapPlayer(player);
        Vector playerVector = localPlayer.getPosition();
        RegionManager regionManager = wgPlugin.getRegionManager(player.getWorld());
        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(playerVector);
        
        for (ProtectedRegion regions : applicableRegionSet) {
            if (regions.contains(playerVector)) {
                if (!entered.contains(player)) {
                    try {
                        left.remove(player);
                        entered.add(player);

                        //DO STUFF HERE
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        if (!left.contains(player)) {
            if (applicableRegionSet.size() == 0) {
                entered.remove(player);
                left.add(player);
                
            }
        }
    }
	
	public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = this.getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }

}