package pl.pabilo8.immersiveintelligence.common.block.multiblock.gate_multiblock.multiblock;

import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import pl.pabilo8.immersiveintelligence.ImmersiveIntelligence;
import pl.pabilo8.immersiveintelligence.common.IIContent;
import pl.pabilo8.immersiveintelligence.common.IISounds;
import pl.pabilo8.immersiveintelligence.common.block.fortification.BlockIIWoodenChainFence.WoodenFortifications;
import pl.pabilo8.immersiveintelligence.common.block.multiblock.gate_multiblock.BlockIIGateMultiblock.IIBlockTypes_FenceGate;
import pl.pabilo8.immersiveintelligence.common.block.multiblock.gate_multiblock.multiblock.MultiblockWoodenFenceGate.TileEntityWoodenFenceGate;
import pl.pabilo8.immersiveintelligence.common.block.multiblock.gate_multiblock.tileentity.TileEntityGateBase;

import javax.annotation.Nullable;

/**
 * @author Pabilo8
 * @since 23.12.2021
 */
public class MultiblockWoodenFenceGate extends MultiblockFenceGateBase<TileEntityWoodenFenceGate>
{
	public static MultiblockWoodenFenceGate INSTANCE;

	public MultiblockWoodenFenceGate()
	{
		super(new ResourceLocation(ImmersiveIntelligence.MODID, "multiblocks/wooden_fence_gate"));
		INSTANCE = this;
	}

	@Override
	protected int getMeta()
	{
		return IIBlockTypes_FenceGate.WOODEN.getMeta();
	}

	@Override
	protected TileEntityWoodenFenceGate getMBInstance()
	{
		return new TileEntityWoodenFenceGate();
	}

	public static class TileEntityWoodenFenceGate extends TileEntityGateBase<TileEntityWoodenFenceGate>
	{
		public TileEntityWoodenFenceGate()
		{
			super(INSTANCE);
		}

		@Override
		protected SoundEvent getOpeningSound()
		{
			return IISounds.gateWoodenOpen;
		}

		@Override
		protected SoundEvent getClosingSound()
		{
			return IISounds.gateWoodenClose;
		}

		@Override
		public IBlockState getFenceState(@Nullable EnumFacing facingConnected)
		{
			return IIContent.blockWoodenFortification.getStateFromMeta(WoodenFortifications.WOODEN_STEEL_CHAIN_FENCE.getMeta())
					.withProperty(BlockFence.EAST, facingConnected==EnumFacing.EAST)
					.withProperty(BlockFence.WEST, facingConnected==EnumFacing.WEST)
					.withProperty(BlockFence.NORTH, facingConnected==EnumFacing.NORTH)
					.withProperty(BlockFence.SOUTH, facingConnected==EnumFacing.SOUTH)
					;
		}
	}
}
