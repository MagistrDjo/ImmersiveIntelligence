package pl.pabilo8.immersiveintelligence.api.ammo.parts;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pl.pabilo8.immersiveintelligence.api.ammo.enums.EnumCoreTypes;
import pl.pabilo8.immersiveintelligence.api.ammo.enums.EnumFuseTypes;
import pl.pabilo8.immersiveintelligence.client.model.IBulletModel;
import pl.pabilo8.immersiveintelligence.common.entity.ammo.EntityAmmoBase;

import javax.annotation.Nonnull;

/**
 * @param <T> entity created by this ammo
 * @author Pabilo8
 * @updated 30.12.2023
 * @ii-approved 0.3.1
 * @since 31-07-2019
 */
public interface IAmmo<T extends EntityAmmoBase>
{
	/**
	 * @return Type Name, ie. revolver_1bCal for a Revolver Cartridge
	 */
	String getName();

	/**
	 * @return The mass of the casing (used to calculate gravity in combination with the components
	 */
	float getInitialMass();

	/**
	 * @return Default speed modifier of the projectile (blocks/tick)
	 * Guns can apply additional velocity by multiplying the result of this method.
	 */
	float getDefaultVelocity();

	/**
	 * @return How much damage this ammunition deals on contact (in half-hearts)
	 */
	float getDamage();

	/**
	 * @return Component amount multiplier, can influence explosion size, smoke cloud duration, etc.
	 */
	float getComponentMultiplier();

	/**
	 * @return Bullet caliber in Block-Caliber (bCal) = 1/16 of a block
	 */
	float getCaliber();

	/**
	 * @return Returns allowed core types, these affect the final damage, penetration and overall performance
	 * @see EnumCoreTypes
	 */
	EnumCoreTypes[] getAllowedCoreTypes();

	/**
	 * @param amount of items to return
	 * @return the casing ItemStack, often requested by guns after firing a bullet
	 */
	ItemStack getCasingStack(int amount);

	/**
	 * @return A bullet model, it's required to extend {@link IBulletModel},<br>
	 * {@link pl.pabilo8.immersiveintelligence.client.util.amt.AMT} is preferred, but you can use any renderer you like
	 */
	@Nonnull
	@SideOnly(Side.CLIENT)
	Class<? extends IBulletModel> getModel();

	//--- Suppression ---//

	/**
	 * @return A radius in which living entities will be given the {@link pl.pabilo8.immersiveintelligence.common.IIPotions#suppression} effect, 0 if there is no suppression effect.
	 */
	default float getSupressionRadius()
	{
		return 0;
	}

	/**
	 * @return Effect amplifier added by {@link #getSupressionRadius()}
	 */
	default int getSuppressionPower()
	{
		return 0;
	}

	T getBulletEntity(World world);

	/**
	 * Internal Item method
	 *
	 * @param stack bullet stack
	 * @return Bullet core from stack NBT
	 */
	IAmmoCore getCore(ItemStack stack);

	/**
	 * Internal Item method
	 *
	 * @param stack bullet stack
	 * @return bullet core type from stack NBT
	 */
	EnumCoreTypes getCoreType(ItemStack stack);

	/**
	 * Internal Item method
	 *
	 * @param stack bullet stack
	 * @return bullet components in appropriate order from stack NBT
	 */
	IAmmoComponent[] getComponents(ItemStack stack);

	/**
	 * Internal Item method
	 *
	 * @param stack        bullet stack
	 * @param component    added component
	 * @param componentNBT
	 * @return bullet components in appropriate order from stack NBT
	 */
	void addComponents(ItemStack stack, IAmmoComponent component, NBTTagCompound componentNBT);

	/**
	 * Internal Item method
	 *
	 * @param stack bullet stack
	 * @return NBT Tags for each bullet component in appropriate order from stack NBT
	 */
	NBTTagCompound[] getComponentsNBT(ItemStack stack);

	/**
	 * @param stack        bullet stack
	 * @param tagCompounds for each bullet component in appropriate order
	 * @return stack with bullet component
	 */
	ItemStack setComponentNBT(ItemStack stack, NBTTagCompound... tagCompounds);

	/**
	 * Internal Item method
	 *
	 * @param stack bullet stack
	 * @return paint color in RGBint format from stack NBT
	 */
	int getPaintColor(ItemStack stack);

	/**
	 * Internal Item method
	 *
	 * @param stack bullet stack
	 * @param color to be applied on the bullet stack
	 * @return painted bullet stack
	 */
	ItemStack setPaintColour(ItemStack stack, int color);

	float getMass(ItemStack stack);

	float getCoreMass(IAmmoCore core, IAmmoComponent[] components);

	/**
	 * @param stack
	 * @param type  type of the fuse
	 */
	void setFuseType(ItemStack stack, EnumFuseTypes type);

	/**
	 * @param stack
	 * @return fuse type used in the bullet
	 */
	EnumFuseTypes getFuseType(ItemStack stack);

	boolean hasFreeComponentSlots(ItemStack stack);

	int getFuseParameter(ItemStack stack);

	void setFuseParameter(ItemStack stack, int p);
}
