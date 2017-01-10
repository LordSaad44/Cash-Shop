package me.lordsaad.cashshop.client.gui.trade;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.teamwizardry.librarianlib.LibrarianLib;
import com.teamwizardry.librarianlib.client.gui.GuiBase;
import com.teamwizardry.librarianlib.client.gui.components.ComponentList;
import com.teamwizardry.librarianlib.client.gui.components.ComponentSprite;
import com.teamwizardry.librarianlib.client.sprite.Sprite;
import com.teamwizardry.librarianlib.client.sprite.Texture;
import me.lordsaad.cashshop.api.ConfigValues;
import me.lordsaad.cashshop.api.Constants;
import me.lordsaad.cashshop.api.Utils;
import me.lordsaad.cashshop.api.capability.WalletCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LordSaad.
 */
public class GuiTrade extends GuiBase {

	public String name;
	public List<TradeInfo> trades;
	public static Texture spriteSheet = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/guis/gui_trade.png"));
	public static Sprite background = spriteSheet.getSprite("main_background", 177, 192);
	public int wallet = 0;

	public GuiTrade(String npcFile) {
		super(256, 256);

		if (!Minecraft.getMinecraft().player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
			Minecraft.getMinecraft().player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
			Minecraft.getMinecraft().player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setInteger("currency", ConfigValues.starterAmount);
		}

		wallet = WalletCapabilityProvider.get(Minecraft.getMinecraft().player).getWallet();

		ComponentSprite compBackground = new ComponentSprite(background,
				(getGuiWidth() / 2) - (background.getWidth() / 2),
				(getGuiHeight() / 2) - (background.getHeight() / 2));
		getMainComponents().add(compBackground);

		InputStream stream = LibrarianLib.PROXY.getResource(Constants.MOD_ID, "npcs/" + npcFile + ".json");
		if (stream != null) {
			InputStreamReader reader = new InputStreamReader(stream);
			JsonElement json = new JsonParser().parse(reader);

			name = json.getAsJsonObject().getAsJsonPrimitive("name") + "";
			trades = new ArrayList<>();

			if (json.isJsonObject() && json.getAsJsonObject().has("name") && json.getAsJsonObject().has("trades")
					&& json.getAsJsonObject().get("name").isJsonPrimitive()
					&& json.getAsJsonObject().get("trades").isJsonArray()) {

				JsonArray array = json.getAsJsonObject().getAsJsonArray("trades");
				for (JsonElement element : array) {
					if (element.isJsonObject()) {
						if (element.getAsJsonObject().has("output")
								&& element.getAsJsonObject().has("cost")
								&& element.getAsJsonObject().has("amount")) {

							List<ItemStack> outputs = new ArrayList<>();
							int cost = 0;

							// OUTPUTS //
							if (element.getAsJsonObject().get("output").isJsonPrimitive()) {
								ItemStack stack = Utils.getStackFromString(element.getAsJsonObject().getAsJsonPrimitive("output") + "");
								if (stack != null) {

									if (element.getAsJsonObject().get("amount").isJsonPrimitive())
										stack.stackSize = Integer.parseInt(element.getAsJsonObject().getAsJsonPrimitive("amount") + "");
									else if (element.getAsJsonObject().get("amount").isJsonArray())
										stack.stackSize = Integer.parseInt(element.getAsJsonObject().getAsJsonArray("amount").get(0) + "");

									outputs.add(stack);
								}
							} else if (element.getAsJsonObject().get("output").isJsonArray()) {

								for (JsonElement output : element.getAsJsonObject().getAsJsonArray("output"))
									if (output.isJsonPrimitive()) {
										ItemStack stack = Utils.getStackFromString(output.getAsJsonPrimitive() + "");
										if (stack != null)
											outputs.add(Utils.getStackFromString(output.getAsJsonPrimitive() + ""));
									}

								for (ItemStack stack : outputs)
									if (element.getAsJsonObject().get("amount").isJsonPrimitive())
										stack.stackSize = Integer.parseInt(element.getAsJsonObject().getAsJsonPrimitive("amount") + "");

									else if (element.getAsJsonObject().get("amount").isJsonArray())
										for (JsonElement elementAmount : element.getAsJsonObject().getAsJsonArray("amount"))
											if (elementAmount.isJsonPrimitive() && outputs.indexOf(stack) <= element.getAsJsonObject().getAsJsonArray("amount").size() - 1)
												stack.stackSize = Integer.parseInt(element.getAsJsonObject().getAsJsonArray("amount").get(outputs.indexOf(stack)) + "");
							}
							// OUTPUTS //

							// COST //
							if (element.getAsJsonObject().get("cost").isJsonPrimitive())
								cost = Integer.parseInt("" + element.getAsJsonObject().getAsJsonPrimitive("cost"));
							// COST //

							trades.add(new TradeInfo(outputs, cost));
						}
					}
				}
			}
		}

		ComponentList tradeColumn1 = new ComponentList(15, 28);
		for (TradeInfo info : trades) {
			TradeSlot slot = new TradeSlot(this, info);
			tradeColumn1.add(slot.component);
		}
		compBackground.add(tradeColumn1);
	}

	public class TradeInfo {

		public List<ItemStack> outputs;
		public int cost;

		public TradeInfo(List<ItemStack> outputs, int cost) {

			this.outputs = outputs;
			this.cost = cost;
		}
	}
}
