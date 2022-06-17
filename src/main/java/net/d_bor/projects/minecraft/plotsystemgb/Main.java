package net.d_bor.projects.minecraft.plotsystemgb;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;

import net.d_bor.projects.minecraft.plotsystemgb.area.Type;
import net.d_bor.projects.minecraft.plotsystemgb.commands.*;
import net.d_bor.projects.minecraft.plotsystemgb.data.Values;
import net.d_bor.projects.minecraft.plotsystemgb.generator.PlotGenerator;
import net.d_bor.projects.minecraft.plotsystemgb.listener.BuildListener;
import net.d_bor.projects.minecraft.plotsystemgb.listener.DestroyListener;
import net.d_bor.projects.minecraft.plotsystemgb.listener.InteractListener;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {

		getClipboards();
		commands();
		listener();
	}

	@Override
	public void onDisable() {
	}

	private void commands() {
		
		Bukkit.getPluginCommand("plot").setExecutor(new PlotCommand());
		
	}

	private void listener() {
		Bukkit.getPluginManager().registerEvents(new BuildListener(), this);
		Bukkit.getPluginManager().registerEvents(new DestroyListener(), this);
		Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {

		return new PlotGenerator();

	}

	private void getClipboards() {

		for (File file : getDataFolder().listFiles()) {

			if (file.getName().endsWith(".schem")) {

				ClipboardFormat format = ClipboardFormats.findByFile(file);
				try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {

					Values.clipboards.put(file.getName().replaceAll(".schem", ""), reader.read());

				} catch (Exception e) {

					e.printStackTrace();

				}
			}
		}

		HashMap<String, Integer> buffer = new HashMap<>();

		for (String names : Values.clipboards.keySet()) {

			if (names.endsWith(Type.PLOT.getEnding())) {

				int pWidth = Values.clipboards.get(names).getDimensions().getBlockX();

				if (buffer.containsKey(names.replaceAll(Type.PLOT.getEnding(), Type.CORNER.getEnding()))) {

					Values.worldWidths.put(names.replaceAll(Type.PLOT.getEnding(), ""),
							pWidth + buffer.get(names.replaceAll(Type.PLOT.getEnding(), Type.CORNER.getEnding())) - 3);

				} else {

					buffer.put(names, pWidth);

				}

			} else if (names.endsWith(Type.CORNER.getEnding())) {

				int cWidth = Values.clipboards.get(names).getDimensions().getBlockX();

				if (buffer.containsKey(names.replaceAll(Type.CORNER.getEnding(), Type.PLOT.getEnding()))) {

					Values.worldWidths.put(names.replaceAll(Type.CORNER.getEnding(), ""),
							cWidth + buffer.get(names.replaceAll(Type.CORNER.getEnding(), Type.PLOT.getEnding())) - 3);

				} else {

					buffer.put(names, cWidth);

				}

				Values.worldWidths.put(names.replaceAll(Type.CORNER.getEnding(), "-r"), cWidth - 1);

			}

		}

		Values.wallUnclaimMaterials.put("world", Material.QUARTZ_SLAB);
		Values.wallClaimMaterials.put("world", Material.SANDSTONE_SLAB);

	}
}
