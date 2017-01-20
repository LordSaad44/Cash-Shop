package me.lordsaad.cashshop.api.capability;

import me.lordsaad.cashshop.api.ConfigValues;
import me.lordsaad.cashshop.api.Constants;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * Created by LordSaad.
 */
public class WalletSerializer implements ICapabilitySerializable<NBTTagInt> {
	public static final ResourceLocation CAP_KEY = new ResourceLocation(Constants.MOD_ID, "wallet");

	private IWalletCapability wallet;

	public WalletSerializer() {
		wallet = new DefaultWalletCap();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityWallet.WALLET;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityWallet.WALLET)
			return CapabilityWallet.WALLET.cast(wallet);
		return null;
	}


	@Override
	public NBTTagInt serializeNBT() {
		return wallet.serializeNBT();
	}

	@Override
	public void deserializeNBT(NBTTagInt nbt) {
		wallet.deserializeNBT(nbt);
	}

	public class DefaultWalletCap implements IWalletCapability {
		private int wallet = ConfigValues.starterAmount;

		@Override
		public int getWallet() {
			return wallet;
		}

		@Override
		public void setWallet(int money) {
			this.wallet = money;
		}

		@Override
		public NBTTagInt serializeNBT() {
			return new NBTTagInt(wallet);
		}

		@Override
		public void deserializeNBT(NBTTagInt nbt) {
			wallet = nbt.getInt();
		}
	}
}
