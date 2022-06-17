package net.d_bor.projects.minecraft.plotsystemgb.area;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import net.d_bor.projects.minecraft.plotsystemgb.data.Values;

public final class Area {

	private PlotID id;

	private Player owner;

	private final Type type;

	private List<Player> trusted = new ArrayList<>();
	private final List<Player> added = new ArrayList<>();
	private final List<Player> denied = new ArrayList<>();

	private final World world;

	private final int minX;
	private final int minZ;
	private final int maxX;
	private final int maxZ;

	private boolean merged;

	public Area(World world, int x1, int z1, int x2, int z2, Type type) {

		this.world = world;
		this.minX = Math.min(x1, x2);
		this.minZ = Math.min(z1, z2);
		
		int maxX=Math.max(x1, x2);
		int maxZ=Math.max(z1, z2);
		
		this.maxX = maxX-((maxX==3)?3:2);
		this.maxZ = maxZ-((maxZ==3)?3:2);
		this.type = type;
		this.owner = null;
		this.merged = false;
		this.id = new PlotID(world, Values.getPlotXZ((x1 + x2) / 2, world), Values.getPlotXZ((z1 + z2) / 2, world),
				type);
		Values.areas.put(id.toString(), this);

	}

	private boolean isWall(int x, int z) {

		return (minX == x || maxX == x || minZ == z || maxZ == z);

	}

	private boolean isWall(Block block) {

		return isWall(block.getX(), block.getZ());

	}

	public boolean isClaimable() {

		return type == Type.PLOT && owner == null;

	}

	public boolean claim(Player player) {

		if (!isClaimable())
			return false;

		// TODO claim limit
		this.owner = player;

		player.sendMessage((maxX - minX) + " " + (maxZ - minZ));
		player.sendMessage(maxX + " " + minX + " " + maxZ + " " + minZ);

		for (int x = minX; x <= maxX; x++) {

			for (int z = minZ; z <= maxZ; z++) {

				if (isWall(x, z)) {

					world.getHighestBlockAt(x, z).setType(Values.wallClaimMaterials.get(world.getName()));
					;

				}
			}
		}

		return true;

	}

	public boolean isOwner(Player player) {

		if (owner == null && !merged)
			return false;

		return owner.equals(player);

	}

	public boolean isAdded(Player player) {
		return added.contains(player);
	}

	public boolean isTrusted(Player player) {
		return trusted.contains(player);
	}

	public boolean isDenied(Player player) {

		return denied.contains(player);

	}

	public boolean isAllowed(Player player) {

		return isOwner(player) || isAdded(player) || isTrusted(player);

	}

	public static boolean allowAction(Player player, Block block) {

		Area area = Values.areas.get(new PlotID(block).toString());

		return area.isAllowed(player) && !area.isWall(block);

	}

	public static boolean allowAction(Player player, Location loc) {

		return allowAction(player, loc.getBlock());

	}

	public World getWorld() {
		return world;
	}

	public int getMinX() {
		return minX;
	}

	public int getMinZ() {
		return minZ;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxZ() {
		return maxZ;
	}

	public String getID() {

		return id.toString();

	}

	public boolean isMerged() {
		return merged;
	}

}
