package net.d_bor.projects.minecraft.plotsystemgb.area;

public enum Type {
	

	PLOT, CORNER, SOUTH, EAST;

	private final String ending="-"+this.name().toLowerCase();

	public String getEnding() {
		return ending;
	}
}
