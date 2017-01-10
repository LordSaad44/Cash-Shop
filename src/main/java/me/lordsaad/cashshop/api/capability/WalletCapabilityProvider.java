package me.lordsaad.cashshop.api.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

/**
 * Created by Saad on 8/16/2016.
 */
public class WalletCapabilityProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound> {

	@CapabilityInject(IWalletCapability.class)
	public static final Capability<IWalletCapability> walletCapability = null;
	private final IWalletCapability capability;

	public WalletCapabilityProvider() {
		capability = new DefaultWalletCapability();
	}

	public WalletCapabilityProvider(IWalletCapability capability) {
		this.capability = capability;
	}

	public static IWalletCapability get(EntityPlayer player) {
		return ((player != null) && player.hasCapability(walletCapability, null)) ? player.getCapability(walletCapability, null) : null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == walletCapability;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if ((walletCapability != null) && (capability == walletCapability)) return (T) this.capability;
		return null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return capability.saveNBTData();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		capability.loadNBTData(nbt);
	}

}
