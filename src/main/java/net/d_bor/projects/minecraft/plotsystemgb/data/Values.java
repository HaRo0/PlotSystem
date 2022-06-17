package net.d_bor.projects.minecraft.plotsystemgb.data;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.World;

import com.sk89q.worldedit.extent.clipboard.Clipboard;

import net.d_bor.projects.minecraft.plotsystemgb.area.Area;
import net.d_bor.projects.minecraft.plotsystemgb.area.Type;

public class Values {

	public static final HashMap<String, Clipboard> clipboards = new HashMap<>();
	public static final HashMap<String, Integer> worldWidths = new HashMap<>();
	public static final HashMap<String, Material> wallUnclaimMaterials = new HashMap<>();
	public static final HashMap<String, Material> wallClaimMaterials = new HashMap<>();
	public static final HashMap<String, Area> areas = new HashMap<>();
	public static final HashMap<UUID, PlotPlayer> players = new HashMap<>();

	// TODO Messages

	public static int getPlotXZ(int worldXZ, World world) {

		return ((worldXZ < 0) ? -1 : 0) + (worldXZ / worldWidths.get(world.getName()));

	}

	public static Type getPlotType(int worldX, int worldZ, World world) {

		int width = Values.worldWidths.get(world.getName());
		int rWidth = Values.worldWidths.get(world.getName() + "-r");

		Type ending;
		int x = ((worldX < 0) ? width - 1 : 0) + (worldX) % width;
		int z = ((worldZ < 0) ? width - 1 : 0) + (worldZ) % width;

		if (x >= rWidth && z >= rWidth) {

			x -= rWidth;
			z -= rWidth;
			ending = Type.PLOT;

		} else if (x >= rWidth && z < rWidth) {

			x -= rWidth;
			ending = Type.SOUTH;

		} else if (x < rWidth && z >= rWidth) {

			z -= rWidth;
			ending = Type.EAST;

		} else {

			ending = Type.CORNER;

		}

		return ending;

	}

}
