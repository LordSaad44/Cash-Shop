package me.lordsaad.cashshop;

import me.lordsaad.cashshop.api.Constants;
import me.lordsaad.cashshop.common.CommonProxy;
import me.lordsaad.cashshop.common.command.CommandToggleTrade;
import me.lordsaad.cashshop.common.command.CommandWallet;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(
		modid = Constants.MOD_ID,
		name = Constants.MOD_NAME,
		version = Constants.VERSION,
		dependencies = Constants.DEPENDENCIES
)
public class Cashshop {

	@SidedProxy(clientSide = Constants.CLIENT, serverSide = Constants.SERVER)
	public static CommonProxy proxy;
	@Mod.Instance
	public static Cashshop instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@Mod.EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandToggleTrade());
		event.registerServerCommand(new CommandWallet());
	}
}
