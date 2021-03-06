package epicsquid.mysticalworld.item;

import epicsquid.mysticallib.model.IModeledObject;
import epicsquid.mysticallib.util.Util;
import epicsquid.mysticalworld.MysticalWorld;
import epicsquid.mysticalworld.config.ConfigManager;
import epicsquid.mysticalworld.entity.EntitySpiritBeetle;
import epicsquid.mysticalworld.entity.EntitySpiritDeer;
import epicsquid.mysticalworld.entity.model.armor.ModelBeetleMask;
import epicsquid.mysticalworld.init.ModItems;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SuppressWarnings("NullableProblems")
public class ItemBeetleMask extends ItemArmor implements IModeledObject {
  public ItemBeetleMask(ArmorMaterial materialIn, String name) {
    super(materialIn, 0, EntityEquipmentSlot.HEAD);
    setTranslationKey(name);
    setRegistryName(new ResourceLocation(MysticalWorld.MODID, name));
    setMaxDamage(399);
    setCreativeTab(MysticalWorld.tab);
    MinecraftForge.EVENT_BUS.register(this);
  }

  @SubscribeEvent
  public static void onAttackEntity(AttackEntityEvent event) {
    if (ConfigManager.hats.maskChance == -1) {
      return;
    }

    EntityPlayer player = event.getEntityPlayer();
    if (player.world.isRemote) {
      return;
    }
    Entity entityTarget = event.getTarget();
    if (!entityTarget.isDead && entityTarget instanceof EntityLivingBase) {
      EntityLivingBase target = (EntityLivingBase) entityTarget;
      ItemStack mask = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
      if (mask.getItem() == ModItems.beetle_mask) {
        if (Util.rand.nextInt(ConfigManager.hats.maskChance) == 0) {
          World world = player.world;
          BlockPos playerPos = player.getPosition();
          if (world.getEntitiesWithinAABB(EntitySpiritDeer.class, ItemAntlerHat.BOX.offset(playerPos)).size() >= 3) {
            return;
          }

          BlockPos pos;

          int tries = 100;
          while (true) {
            tries--;
            if (tries <= 0) {
              return;
            }
            pos = playerPos.add(Util.rand.nextInt(8) - 8, 0, Util.rand.nextInt(8) - 8);
            if (!world.isAirBlock(pos)) {
              continue;
            }
            if (target.getDistanceSq(pos) < 9) {
              continue;
            }

            break;
          }
          EntitySpiritBeetle spiritBeetle = new EntitySpiritBeetle(world);
          spiritBeetle.setAttackTarget(target);
          spiritBeetle.setDropItemsWhenDead(false);
          spiritBeetle.setPositionAndRotation(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, player.rotationYaw, player.rotationPitch);
          spiritBeetle.noClip = true;
          world.spawnEntity(spiritBeetle);
          ItemStack head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
          if (ConfigManager.hats.maskDurabilityDamage != -1) {
            head.damageItem(ConfigManager.hats.maskDurabilityDamage, player);
          }
        }
      }
    }
  }

  @Override
  public EnumRarity getRarity(ItemStack stack) {
    return EnumRarity.RARE;
  }

  @Override
  public void initModel() {
    ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "handler"));
  }

  @Nullable
  @Override
  public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
    return MysticalWorld.MODID + ":textures/model/armor/beetle_mask.png";
  }

  @Nullable
  @Override
  @SideOnly(Side.CLIENT)
  public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
    return ModelBeetleMask.instance;
  }
}
