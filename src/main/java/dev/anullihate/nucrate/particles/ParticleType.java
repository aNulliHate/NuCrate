package dev.anullihate.nucrate.particles;

public enum ParticleType {
    CLOUD_RAIN("CloudRain"),
    CROWN("Crown"),
    DOUBLE_HELIX("DoubleHelix"),
    HELIX("Helix"),
    TING("Ting");

    private final String particleType;

    private ParticleType(String particleType) {
        this.particleType = particleType;
    }

    public String getParticleType() {
        return particleType;
    }
}
