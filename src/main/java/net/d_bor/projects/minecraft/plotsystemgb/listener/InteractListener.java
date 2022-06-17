package net.d_bor.projects.minecraft.plotsystemgb.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import net.d_bor.projects.minecraft.plotsystemgb.area.Area;
import net.d_bor.projects.minecraft.plotsystemgb.generator.PlotGenerator;

public class InteractListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {

		Player player = e.getPlayer();
		Block block = e.getClickedBlock();

		if (e.getAction() != Action.RIGHT_CLICK_BLOCK)

			return;

		if (block.getType() == Material.AIR)
			return;

		if (player.getLocation().getWorld().getGenerator() instanceof PlotGenerator) {

			// TODO Not Admin if

			if (!Area.allowAction(player, block)) {

				if (e.getClickedBlock().getType().isInteractable()) {

					e.setCancelled(true);
					
					//TODO Message
					e.getPlayer().sendMessage("Nicht interakten hier du darft"/*Values.notClaimed*/);

				}
			}
		}
	}
	
	//TODO Entitys
	
}
