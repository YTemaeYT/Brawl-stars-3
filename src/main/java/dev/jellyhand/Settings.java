package dev.jellyhand;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Settings {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Path file = FabricLoader.getInstance().getConfigDir().resolve("jellyhand.json");
    public boolean jellyAnimation = true;
    public boolean handPosition = true;
    public double animationSpeed = 0.65;
    public double handX = 0.0, handY = 0.0, handZ = 0.0;
    public void load() { try { if (Files.exists(file)) { Settings s = GSON.fromJson(Files.readString(file), Settings.class); if (s != null) { jellyAnimation=s.jellyAnimation; handPosition=s.handPosition; animationSpeed=s.animationSpeed; handX=s.handX; handY=s.handY; handZ=s.handZ; } } } catch (Exception ignored) {} }
    public void save() { try { Files.createDirectories(file.getParent()); Files.writeString(file, GSON.toJson(this)); } catch (Exception ignored) {} }
}
