package pl.pabilo8.immersiveintelligence.common.commands.ii.item;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import pl.pabilo8.immersiveintelligence.api.ammo.IIAmmoRegistry;
import pl.pabilo8.immersiveintelligence.api.ammo.enums.EnumCoreTypes;
import pl.pabilo8.immersiveintelligence.api.ammo.parts.IAmmoComponent;
import pl.pabilo8.immersiveintelligence.api.ammo.parts.IAmmoCore;
import pl.pabilo8.immersiveintelligence.api.ammo.parts.IAmmoItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Pabilo8
 * @since 23-06-2020
 */
public class CommandIIGiveBullet extends CommandBase
{
	/**
	 * Gets the name of the command
	 */
	@Nonnull
	@Override
	public String getName()
	{
		return "bullet";
	}

	/**
	 * Gets the usage string for the command.
	 */
	@Nonnull
	@Override
	public String getUsage(@Nonnull ICommandSender sender)
	{
		return "Gives a bullet, usage: ii bullet <receiver> <casing> <core> <coreType> <fuse> [component] [nbt]";
	}

	/**
	 * Callback for when the command is executed
	 */
	@Override
	public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException
	{
		if(args.length > 3)
		{
			EntityPlayerMP player = CommandBase.getPlayer(server, sender, args[0]);
			IAmmoItem casing = IIAmmoRegistry.getBulletItem(args[1]);
			IAmmoCore core = IIAmmoRegistry.getCore(args[2]);
			EnumCoreTypes coreType = EnumCoreTypes.v(args[3]);
			ArrayList<IAmmoComponent> components = new ArrayList<>();
			for(int i = 4; i < args.length; i++)
				components.add(IIAmmoRegistry.getComponent(args[i]));

			if(casing!=null&&core!=null)
			{

				player.addItemStackToInventory(casing.getBulletWithParams(core, coreType, components.toArray(new IAmmoComponent[0])));
				sender.sendMessage(new TextComponentString("Bullets given!"));
			}
			else
				throw new WrongUsageException(getUsage(sender));
		}
		else
			throw new WrongUsageException(getUsage(sender));
	}

	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel()
	{
		return 4;
	}

	/**
	 * Get a list of options for when the user presses the TAB key
	 */
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
	{
		if(args.length==1)
		{
			return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
		}
		else if(args.length==2)
		{
			return getListOfStringsMatchingLastWord(args, IIAmmoRegistry.registeredAmmoItems.keySet());
		}
		else if(args.length==3)
		{
			return getListOfStringsMatchingLastWord(args, IIAmmoRegistry.registeredBulletCores.keySet());
		}
		else if(args.length==4)
		{
			IAmmoItem bullet = IIAmmoRegistry.registeredAmmoItems.get(args[1]);
			return getListOfStringsMatchingLastWord(args, bullet==null?Collections.emptyList(): Arrays.stream(bullet.getAllowedCoreTypes()).map(EnumCoreTypes::getName).collect(Collectors.toList()));
		}
		else if(args.length > 4)
		{
			return getListOfStringsMatchingLastWord(args, IIAmmoRegistry.registeredComponents.keySet());
		}
		else
			return Collections.emptyList();
	}

	/**
	 * Return whether the specified command parameter index is a username parameter.
	 */
	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return index==0;
	}
}
