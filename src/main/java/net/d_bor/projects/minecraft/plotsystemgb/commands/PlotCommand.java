package net.d_bor.projects.minecraft.plotsystemgb.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.d_bor.projects.minecraft.plotsystemgb.area.Area;
import net.d_bor.projects.minecraft.plotsystemgb.area.PlotID;
import net.d_bor.projects.minecraft.plotsystemgb.data.Values;

public class PlotCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {

			Player player = (Player) sender;

			if (args.length == 1) {

				//TODO Permission
				if (args[0].equalsIgnoreCase("claim")) {

					Area area = Values.areas.get(new PlotID(player.getLocation()).toString());
					if (area.isClaimable()) {

						if (area.claim(player)) {

							// TODO Message
							player.sendMessage("PLot geclaimt");
							return true;
						} else {

							// TODO Message
							player.sendMessage("Du darfst dieses plot nicht claimen");
							return true;
						}
					} else {

						// TODO Message
						player.sendMessage("Plot ist schon geclaimt");
						return true;

					}

				} else {

					player.sendMessage("unbekanntes argument");
					return false;
				}

			} else {

				player.sendMessage("Unbekannte argumenten anzahl");
				return false;
			}

		} else {
			sender.sendMessage("Dieser command darf nur von einem spieler ausgeführt werden");
			return false;
		}
	}

}
