package net.d_bor.projects.minecraft.plotsystemgb.listener;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import net.d_bor.projects.minecraft.plotsystemgb.area.Area;
import net.d_bor.projects.minecraft.plotsystemgb.generator.PlotGenerator;

public class BuildListener implements Listener {

	@EventHandler
	public void onDestroy(BlockPlaceEvent e) {

		Player player = e.getPlayer();
		Block block = e.getBlock();

		if (block.getLocation().getWorld().getGenerator() instanceof PlotGenerator) {

			// TODO Admin mode if

			if (!Area.allowAction(player, block)) {

				e.setCancelled(true);

				// TODO Message
				e.getPlayer().sendMessage("Nicht bauen du dürfen"/* Values.notClaimed */);

			}
		}
	}
}
