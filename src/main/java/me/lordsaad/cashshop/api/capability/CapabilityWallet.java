package me.lordsaad.cashshop.api.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

/**
 * Created by LordSaad.
 */
public class CapabilityWallet {

	@CapabilityInject(IWalletCapability.class)
	public static Capability<IWalletCapability> WALLET = null;

	public static void register() {
		CapabilityManager.INSTANCE.register(IWalletCapability.class, new Capability.IStorage<IWalletCapability>() {
			@Override
			public NBTBase writeNBT(Capability<IWalletCapability> capability, IWalletCapability instance, EnumFacing side) {
				return null;
			}

			@Override
			public void readNBT(Capability<IWalletCapability> capability, IWalletCapability instance, EnumFacing side, NBTBase nbt) {
			}
		}, () -> null);
	}
}
