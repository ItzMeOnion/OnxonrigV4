package com.onxon.marlows;

import net.minecraft.client.MinecraftClient;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public final class MarlowsConfig {
    private static final Properties DATA = new Properties();
    private static final java.nio.file.Path FILE = MinecraftClient.getInstance().runDirectory.toPath()
        .resolve("config/marlows-crystal-optimiser.properties");
    private MarlowsConfig() {}
    public static String get(String key) { return DATA.getProperty(key, "minecraft:map"); }
    public static void load() {
        try {
            Files.createDirectories(FILE.getParent());
            if (Files.exists(FILE)) try (var in = Files.newInputStream(FILE)) { DATA.load(in); }
        } catch (IOException e) { System.err.println("Marlow config load failed: " + e.getMessage()); }
    }
    public static void put(String key, String value) { DATA.setProperty(key, value); }
    public static void save() {
        try {
            Files.createDirectories(FILE.getParent());
            try (var out = Files.newOutputStream(FILE)) { DATA.store(out, "Marlow's crystal optimiser"); }
        } catch (IOException e) { System.err.println("Marlow config save failed: " + e.getMessage()); }
    }
}
