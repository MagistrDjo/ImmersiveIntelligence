package pl.pabilo8.immersiveintelligence.common.entity.ammo.types;

import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import pl.pabilo8.immersiveintelligence.ImmersiveIntelligence;
import pl.pabilo8.immersiveintelligence.common.IIConfigHandler.IIConfig.Weapons;
import pl.pabilo8.immersiveintelligence.common.IISounds;
import pl.pabilo8.immersiveintelligence.common.network.IIPacketHandler;

/**
 * @author Pabilo8
 * @ii-approved 0.3.1
 * @since 02.02.2024
 */
public class EntityAmmoArtilleryProjectile extends EntityAmmoProjectile
{
	//--- Properties ---//

	/**
	 * Whether this artillery shell played its flyby sound
	 */
	boolean flybySound = false;
	/**
	 * The chunk ticket for this shell
	 */
	private Ticket ticket = null;

	//--- Constructor ---//

	public EntityAmmoArtilleryProjectile(World world)
	{
		super(world);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		ticket = ForgeChunkManager.requestTicket(ImmersiveIntelligence.INSTANCE, world, ForgeChunkManager.Type.ENTITY);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		//Play artillery shell impact sound
		if(Weapons.artilleryImpactSound&&!flybySound&&ticksExisted > 5)
		{
			if(motionY < 0)
			{
				BlockPos top = world.getTopSolidOrLiquidBlock(new BlockPos(getNextPositionVector())).up();
				IIPacketHandler.playRangedSound(world, new Vec3d(top), IISounds.artilleryImpact, SoundCategory.PLAYERS, 24, 0.75f, 1.3f);
				flybySound = true;
			}
		}

		//Force chunk loading
		if(!world.isRemote)
			ForgeChunkManager.forceChunk(this.ticket, this.world.getChunkFromBlockCoords(this.getPosition()).getPos());
	}

	@Override
	protected boolean shouldDecay()
	{
		return posY < 0;
	}
}
