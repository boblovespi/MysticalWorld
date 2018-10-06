package epicsquid.mysticalworld.block;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.block.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockThatch extends BlockBase {

  public BlockThatch(@Nonnull Material mat, @Nonnull SoundType type, float hardness, @Nonnull String name) {
    super(mat, type, hardness, name);
  }

  @Override
  public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face){
    return 20;
  }

  @Override
  public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face){
    return true;
  }

  @Override
  public boolean isFullCube(@Nonnull IBlockState state) {
    return true;
  }

  @Override
  public boolean isOpaqueCube(@Nonnull IBlockState state) {
    return true;
  }

  @Nonnull
  @Override
  public BlockRenderLayer getBlockLayer() {
    return BlockRenderLayer.CUTOUT_MIPPED; 
  }
}