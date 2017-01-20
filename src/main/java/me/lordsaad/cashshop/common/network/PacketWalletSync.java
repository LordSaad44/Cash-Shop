package me.lordsaad.cashshop.common.network;

import io.netty.buffer.ByteBuf;
import me.lordsaad.cashshop.api.capability.CapabilityWallet;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Saad on 8/16/2016.
 */
public class PacketWalletSync implements IMessage {

	private int wallet;

	public PacketWalletSync() {
	}

	public PacketWalletSync(int wallet) {
		this.wallet = wallet;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		wallet = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(wallet);
	}

	public static class PacketWalletSyncHandler implements IMessageHandler<PacketWalletSync, IMessage> {
		@Override
		public IMessage onMessage(final PacketWalletSync message, final MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message, ctx));
			return null;
		}

		private void processMessage(PacketWalletSync message, MessageContext ctx) {
			Minecraft.getMinecraft().player.getCapability(CapabilityWallet.WALLET, null).setWallet(message.wallet);
		}
	}
}
