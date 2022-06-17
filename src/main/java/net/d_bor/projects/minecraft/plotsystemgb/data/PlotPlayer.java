package net.d_bor.projects.minecraft.plotsystemgb.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import net.d_bor.projects.minecraft.plotsystemgb.area.Area;

public class PlotPlayer {

	private final List<Area> owns = new ArrayList<>();
	private final List<Area> added = new ArrayList<>();
	private final List<Area> denied = new ArrayList<>();
	private List<Area> trusted = new ArrayList<>();

}
