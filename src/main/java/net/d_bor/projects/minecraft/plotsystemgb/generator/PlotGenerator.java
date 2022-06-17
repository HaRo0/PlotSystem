package net.d_bor.projects.minecraft.plotsystemgb.generator;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.ChunkGenerator;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;

import net.d_bor.projects.minecraft.plotsystemgb.area.Area;
import net.d_bor.projects.minecraft.plotsystemgb.area.Type;
import net.d_bor.projects.minecraft.plotsystemgb.data.Values;

public class PlotGenerator extends ChunkGenerator {

	@Override
	public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {

		ChunkData chunk = createChunkData(world);

		int worldX = chunkX * 16;
		int worldZ = chunkZ * 16;

		for (short x = 0; x < 16; x++) {

			for (short z = 0; z < 16; z++) {

				Object[] data = getSchemXZ(world, worldX, x, worldZ, z);
				int schemX = (int) data[0];
				int schemZ = (int) data[1];
				Type type = (Type) data[2];
				Clipboard clip = Values.clipboards.get(world.getName() + type.getEnding());

				if (schemX == 0 && schemZ == 0) {

					new Area(world, worldX + x, worldZ + z, worldX + x + clip.getDimensions().getBlockX(),
							worldZ + z + clip.getDimensions().getBlockZ(), type);

				}

				for (short y = 0; y < 256; y++) {

					biome.setBiome(x, y, z, Biome.PLAINS);

					BlockData block = BukkitAdapter.adapt(clip.getFullBlock(BlockVector3.at(schemX, y, schemZ)));

					chunk.setBlock(x, y, z, block);

				}
			}
		}
		return chunk;
	}

	static Object[] getSchemXZ(World world, int worldX, int chunkX, int worldZ, int chunkZ) {

		Object[] data = new Object[3];

		int width = Values.worldWidths.get(world.getName());
		int rWidth = Values.worldWidths.get(world.getName() + "-r");

		Type ending;
		int x = ((worldX < 0) ? width - 1 : 0) + (worldX + chunkX) % width;
		int z = ((worldZ < 0) ? width - 1 : 0) + (worldZ + chunkZ) % width;

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

		data[0] = x;
		data[1] = z;
		data[2] = ending;

		return data;
	}

}
