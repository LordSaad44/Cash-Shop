package me.lordsaad.cashshop.api.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

/**
 * Created by Saad on 8/16/2016.
 */
public class WalletCapabilityStorage implements IStorage<IWalletCapability> {

	public static final WalletCapabilityStorage INSTANCE = new WalletCapabilityStorage();

	@Override
	public NBTBase writeNBT(Capability<IWalletCapability> capability, IWalletCapability instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("wallet", instance.getWallet());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IWalletCapability> capability, IWalletCapability instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		((DefaultWalletCapability) instance).wallet = tag.getInteger("wallet");
	}
}
