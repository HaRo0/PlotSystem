package net.d_bor.projects.minecraft.plotsystemgb.area;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import net.d_bor.projects.minecraft.plotsystemgb.data.Values;

public class PlotID {

	private final World world;
	private final int plotX;
	private final int plotZ;
	private final Type type;

	public PlotID(World world, int plotX, int plotZ, Type type) {

		this.world = world;
		this.plotX = plotX;
		this.plotZ = plotZ;
		this.type = type;

	}

	public PlotID(World world, int worldX, int worldZ) {

		this(world, Values.getPlotXZ(worldX, world), Values.getPlotXZ(worldZ, world),
				Values.getPlotType(worldX, worldZ, world));

	}

	public PlotID(Block block) {

		this(block.getWorld(), block.getX(), block.getZ());

	}

	public PlotID(Location loc) {

		this(loc.getBlock());

	}

	@Override
	public boolean equals(Object o) {

		if (o == null || !(o instanceof PlotID))
			return false;

		PlotID id = (PlotID) o;
		return id.world == world && id.plotX == plotX && id.plotZ == plotZ && id.type == type;

	}

	@Override
	public String toString() {

		String id = world.getName() + "-" + plotX + ";" + plotZ;

		switch (type) {
		case CORNER: {
			id += "-C";
			break;
		}
		case SOUTH: {
			id += "-S";
			break;
		}
		case EAST: {
			id += "-E";
			break;
		}
		case PLOT: {

			break;
		}
		}

		return id;

	}

	public World getWorld() {

		return world;

	}

	public int getPlotX() {

		return plotX;

	}

	public int getPlotZ() {

		return plotZ;

	}
}
