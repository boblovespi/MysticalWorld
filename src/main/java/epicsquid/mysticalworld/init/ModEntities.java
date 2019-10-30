package epicsquid.mysticalworld.init;

import epicsquid.mysticalworld.MysticalWorld;
import epicsquid.mysticalworld.config.ConfigManager;
import epicsquid.mysticalworld.entity.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@SuppressWarnings({"WeakerAccess", "ConstantConditions", "unchecked"})
public class ModEntities {
  private static final String BEETLE_ID = "beetle";
  private static final String DEER_ID = "deer";
  private static final String FROG_ID = "frog";
  private static final String SILVER_FOX_ID = "silver_fox";
  private static final String SPROUT_ID = "sprout";

  private static EntityType<BeetleEntity> BEETLE_TYPE = (EntityType<BeetleEntity>) EntityType.Builder.create(BeetleEntity::new, EntityClassification.CREATURE).size(0.75f, 0.75f).setTrackingRange(64).setShouldReceiveVelocityUpdates(true).setUpdateInterval(3).build(BEETLE_ID).setRegistryName(MysticalWorld.MODID, BEETLE_ID);
  private static EntityType<DeerEntity> DEER_TYPE = (EntityType<DeerEntity>) EntityType.Builder.create(DeerEntity::new, EntityClassification.CREATURE).size(1.0f, 1.0f).setTrackingRange(64).setShouldReceiveVelocityUpdates(true).setUpdateInterval(3).build(DEER_ID).setRegistryName(MysticalWorld.MODID, DEER_ID);
  private static EntityType<FrogEntity> FROG_TYPE = (EntityType<FrogEntity>) EntityType.Builder.create(FrogEntity::new, EntityClassification.AMBIENT).size(0.5f, 0.5f).setTrackingRange(64).setShouldReceiveVelocityUpdates(true).setUpdateInterval(3).build(FROG_ID).setRegistryName(MysticalWorld.MODID, FROG_ID);
  private static EntityType<FoxEntity> SILVER_FOX_TYPE = (EntityType<FoxEntity>) EntityType.Builder.create(FoxEntity::new, EntityClassification.CREATURE).size(0.75f, 0.75f).setTrackingRange(64).setShouldReceiveVelocityUpdates(true).setUpdateInterval(3).build(SILVER_FOX_ID).setRegistryName(MysticalWorld.MODID, SILVER_FOX_ID);
  private static EntityType<SproutEntity> SPROUT_TYPE = (EntityType<SproutEntity>) EntityType.Builder.create(SproutEntity::new, EntityClassification.CREATURE).size(0.5f, 1.0f).setTrackingRange(64).setShouldReceiveVelocityUpdates(true).setUpdateInterval(3).build(SPROUT_ID).setRegistryName(MysticalWorld.MODID, SPROUT_ID);

  public static Supplier<EntityType<BeetleEntity>> BEETLE = () -> BEETLE_TYPE;
  public static Supplier<EntityType<DeerEntity>> DEER = () -> DEER_TYPE;
  public static Supplier<EntityType<FrogEntity>> FROG = () -> FROG_TYPE;
  public static Supplier<EntityType<FoxEntity>> SILVER_FOX = () -> SILVER_FOX_TYPE;
  public static Supplier<EntityType<SproutEntity>> SPROUT = () -> SPROUT_TYPE;

  public static RegistryObject<SpawnEggItem> SPAWN_BEETLE = MysticalWorld.REGISTRY.registerItem(BEETLE_ID + "_spawn_egg", () -> new SpawnEggItem(BEETLE_TYPE, 0x418594, 0x211D15, ModRegistries.SIG.get()));
  public static RegistryObject<SpawnEggItem> SPAWN_DEER = MysticalWorld.REGISTRY.registerItem(DEER_ID + "_spawn_egg", () -> new SpawnEggItem(DEER_TYPE, 0xa18458, 0x5e4d33, ModRegistries.SIG.get()));
  public static RegistryObject<SpawnEggItem> SPAWN_FROG = MysticalWorld.REGISTRY.registerItem(FROG_ID + "_spawn_egg", () -> new SpawnEggItem(FROG_TYPE, 0x285234, 0xDBE697, ModRegistries.SIG.get()));
  public static RegistryObject<SpawnEggItem> SPAWN_SPROUT = MysticalWorld.REGISTRY.registerItem(SPROUT_ID + "_spawn_egg", () -> new SpawnEggItem(SPROUT_TYPE, 0xe8f442, 0xd11f5a, ModRegistries.SIG.get()));
  public static RegistryObject<SpawnEggItem> SPAWN_SILVER_FOX = MysticalWorld.REGISTRY.registerItem(SILVER_FOX_ID + "_spawn_egg", () -> new SpawnEggItem(SILVER_FOX_TYPE, 0x9e9088, 0xF5E0D3, ModRegistries.SIG.get()));

  public static void load() {}

  public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
    IForgeRegistry<EntityType<?>> registry = event.getRegistry();
    registry.registerAll(BEETLE_TYPE, DEER_TYPE, FROG_TYPE, SILVER_FOX_TYPE, SPROUT_TYPE);

    Set<Biome> biomes = new HashSet<>();

    if (ConfigManager.DEER_CONFIG.shouldRegister()) {
      for (String biomeName : ConfigManager.DEER_CONFIG.getBiomes()) {
        biomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.getType(biomeName)));
      }
      biomes.forEach(biome -> biome.getSpawns(EntityClassification.CREATURE).add(
          new Biome.SpawnListEntry(DEER.get(), ConfigManager.DEER_CONFIG.getChance(), ConfigManager.DEER_CONFIG.getMin(),
              ConfigManager.DEER_CONFIG.getMax())));
    }

    biomes.clear();

    if (ConfigManager.SPROUT_CONFIG.shouldRegister()) {
      for (String biomeName : ConfigManager.SPROUT_CONFIG.getBiomes()) {
        biomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.getType(biomeName)));
      }
      biomes.forEach(biome -> biome.getSpawns(EntityClassification.CREATURE).add(
          new Biome.SpawnListEntry(SPROUT.get(), ConfigManager.SPROUT_CONFIG.getChance(), ConfigManager.SPROUT_CONFIG.getMin(),
              ConfigManager.SPROUT_CONFIG.getMax())));
    }

    biomes.clear();

    if (ConfigManager.BEETLE_CONFIG.shouldRegister()) {
      for (String biomeName : ConfigManager.BEETLE_CONFIG.getBiomes()) {
        biomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.getType(biomeName)));
      }
      biomes.forEach(biome -> biome.getSpawns(EntityClassification.CREATURE).add(
          new Biome.SpawnListEntry(BEETLE.get(), ConfigManager.BEETLE_CONFIG.getChance(), ConfigManager.BEETLE_CONFIG.getMin(),
              ConfigManager.BEETLE_CONFIG.getMax())));
    }

    biomes.clear();

    if (ConfigManager.FROG_CONFIG.shouldRegister()) {
      for (String biomeName : ConfigManager.FROG_CONFIG.getBiomes()) {
        biomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.getType(biomeName)));
      }
      biomes.forEach(biome -> biome.getSpawns(EntityClassification.CREATURE).add(
          new Biome.SpawnListEntry(FROG.get(), ConfigManager.FROG_CONFIG.getChance(), ConfigManager.FROG_CONFIG.getMin(),
              ConfigManager.FROG_CONFIG.getMax())));
    }

    biomes.clear();

    if (ConfigManager.SILVER_FOX_CONFIG.shouldRegister()) {
      for (String biomeName : ConfigManager.SILVER_FOX_CONFIG.getBiomes()) {
        biomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.getType(biomeName)));
      }
      biomes.forEach(biome -> biome.getSpawns(EntityClassification.CREATURE).add(
          new Biome.SpawnListEntry(SILVER_FOX.get(), ConfigManager.SILVER_FOX_CONFIG.getChance(), ConfigManager.SILVER_FOX_CONFIG.getMin(),
              ConfigManager.SILVER_FOX_CONFIG.getMax())));
    }

    biomes.clear();

    EntitySpawnPlacementRegistry.register(DEER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
        AnimalEntity::func_223316_b);
    EntitySpawnPlacementRegistry.register(FROG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
        AnimalEntity::func_223316_b);
    EntitySpawnPlacementRegistry.register(SPROUT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
        AnimalEntity::func_223316_b);
    EntitySpawnPlacementRegistry.register(SILVER_FOX.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
        AnimalEntity::func_223316_b);
    EntitySpawnPlacementRegistry.register(BEETLE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
        AnimalEntity::func_223316_b);
  }
}
