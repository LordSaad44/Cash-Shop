package me.lordsaad.cashshop.common.network;

import io.netty.buffer.ByteBuf;
import me.lordsaad.cashshop.api.capability.WalletCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Saad on 8/16/2016.
 */
public class MessageUpdateCapabilities implements IMessage {

	private NBTTagCompound tags;

	public MessageUpdateCapabilities() {
	}

	public MessageUpdateCapabilities(NBTTagCompound tag) {
		this.tags = tag;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		tags = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, tags);
	}

	public static class CapsMessageHandler implements IMessageHandler<MessageUpdateCapabilities, IMessage> {

		@Override
		public IMessage onMessage(final MessageUpdateCapabilities message, final MessageContext ctx) {
			IThreadListener mainThread = (ctx.side.isClient()) ? Minecraft.getMinecraft() : (WorldServer) ctx.getServerHandler().playerEntity.world;
			mainThread.addScheduledTask(() -> WalletCapabilityProvider.get(Minecraft.getMinecraft().player).loadNBTData(message.tags));
			return null;
		}


	}
}
