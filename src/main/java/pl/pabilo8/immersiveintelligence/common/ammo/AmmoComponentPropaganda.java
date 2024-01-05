package pl.pabilo8.immersiveintelligence.common.ammo;

import blusunrize.immersiveengineering.api.crafting.IngredientStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import pl.pabilo8.immersiveintelligence.api.ammo.IIAmmoUtils;
import pl.pabilo8.immersiveintelligence.api.ammo.enums.EnumComponentRole;
import pl.pabilo8.immersiveintelligence.api.ammo.enums.EnumCoreTypes;
import pl.pabilo8.immersiveintelligence.api.ammo.parts.IAmmoComponent;
import pl.pabilo8.immersiveintelligence.common.IIContent;

/**
 * @author Pabilo8
 * @since 10.07.2021
 */
public class AmmoComponentPropaganda implements IAmmoComponent
{
	@Override
	public String getName()
	{
		return "propaganda";
	}

	@Override
	public IngredientStack getMaterial()
	{
		return new IngredientStack(new ItemStack(IIContent.itemPrintedPage, 1, 1));
	}

	@Override
	public float getDensity()
	{
		return 0.65f;
	}

	@Override
	public void onEffect(World world, Vec3d pos, Vec3d dir, float multiplier, NBTTagCompound tag, EnumCoreTypes coreType, Entity owner)
	{
		ItemStack stack = new ItemStack(IIContent.itemPrintedPage, 1, 1);
		stack.setTagCompound(tag);
		for(int i = 0; i < multiplier*8; i++)
		{
			Vec3d v = new Vec3d(1, 0, 0).rotateYaw(i/(multiplier*16)*360f);
			EntityItem ei = new EntityItem(world, pos.x+.5+v.x*2, pos.y+.5, pos.z+.5+v.z*2, stack.copy());
			ei.motionY = 0.035;
			ei.motionX = 0.075F*v.x;
			ei.motionZ = 0.075F*v.z;

			world.spawnEntity(ei);
		}
		IIAmmoUtils.suppress(world, pos.x, pos.y, pos.z, 4f*multiplier, (int)(4*multiplier));

		// TODO: 11.07.2021 add the advancement
	}

	@Override
	public EnumComponentRole getRole()
	{
		return EnumComponentRole.SPECIAL;
	}

	@Override
	public int getColour()
	{
		return 0xbaafa4;
	}
}
