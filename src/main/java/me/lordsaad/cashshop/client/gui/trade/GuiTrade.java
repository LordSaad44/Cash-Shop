package me.lordsaad.cashshop.client.gui.trade;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.teamwizardry.librarianlib.LibrarianLib;
import com.teamwizardry.librarianlib.client.gui.GuiBase;
import com.teamwizardry.librarianlib.client.gui.GuiComponent;
import com.teamwizardry.librarianlib.client.gui.components.ComponentList;
import com.teamwizardry.librarianlib.client.gui.components.ComponentSprite;
import com.teamwizardry.librarianlib.client.gui.components.ComponentText;
import com.teamwizardry.librarianlib.client.gui.components.ComponentVoid;
import com.teamwizardry.librarianlib.client.gui.mixin.ButtonMixin;
import com.teamwizardry.librarianlib.client.sprite.Sprite;
import com.teamwizardry.librarianlib.client.sprite.Texture;
import me.lordsaad.cashshop.api.Constants;
import me.lordsaad.cashshop.api.Utils;
import me.lordsaad.cashshop.api.capability.CapabilityWallet;
import me.lordsaad.cashshop.api.capability.IWalletCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LordSaad.
 */
public class GuiTrade extends GuiBase {

	public static final Texture spriteSheet = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/guis/gui_trade.png"));
	public static final Sprite background = spriteSheet.getSprite("main_background", 177, 192);
	public static final Sprite sprArrowRight = spriteSheet.getSprite("arrow_right", 24, 19);
	public static final Sprite sprArrowRightHover = spriteSheet.getSprite("arrow_right_hover", 24, 19);
	public static final Sprite sprArrowLeft = spriteSheet.getSprite("arrow_left", 24, 19);
	public static final Sprite sprArrowLeftHover = spriteSheet.getSprite("arrow_left_hover", 24, 19);
	public static final Sprite sprGoldSign = spriteSheet.getSprite("gold_sign", 48, 32);
	public static final Sprite sprCoin = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/items/currency_coin.png")).getSprite("coin", 16, 16);
	public String name;
	public List<TradeInfo> trades;
	public List<List<List<ComponentVoid>>> tradeComponents = new ArrayList<>();
	public int page = 0;

	public GuiTrade(String npcFile) {
		super(256, 256);

		ComponentSprite compBackground = new ComponentSprite(background,
				(getGuiWidth() / 2) - (background.getWidth() / 2),
				(getGuiHeight() / 2) - (background.getHeight() / 2));
		getMainComponents().add(compBackground);

		InputStream stream = LibrarianLib.PROXY.getResource(Constants.MOD_ID, "npcs/" + npcFile + ".json");
		if (stream != null) {
			InputStreamReader reader = new InputStreamReader(stream);
			JsonElement json = new JsonParser().parse(reader);

			name = json.getAsJsonObject().getAsJsonPrimitive("name").getAsString();
			trades = new ArrayList<>();

			if (json.isJsonObject() && json.getAsJsonObject().has("name") && json.getAsJsonObject().has("trades")
					&& json.getAsJsonObject().get("name").isJsonPrimitive()
					&& json.getAsJsonObject().get("trades").isJsonArray()) {

				JsonArray array = json.getAsJsonObject().getAsJsonArray("trades");
				for (JsonElement element : array) {
					if (element.isJsonObject()) {
						if (element.getAsJsonObject().has("locked")
								&& element.getAsJsonObject().get("locked").isJsonPrimitive()
								&& element.getAsJsonObject().getAsJsonPrimitive("locked").getAsBoolean()) continue;
						if (element.getAsJsonObject().has("output")
								&& element.getAsJsonObject().has("cost")
								&& element.getAsJsonObject().has("amount")) {

							List<ItemStack> outputs = new ArrayList<>();
							int cost = 0;

							// OUTPUTS //
							if (element.getAsJsonObject().get("output").isJsonPrimitive()) {
								ItemStack stack = Utils.getStackFromString(element.getAsJsonObject().getAsJsonPrimitive("output").getAsString());
								if (stack != null) {
									if (element.getAsJsonObject().get("amount").isJsonPrimitive())
										stack.stackSize = element.getAsJsonObject().getAsJsonPrimitive("amount").getAsInt();
									else if (element.getAsJsonObject().get("amount").isJsonArray())
										stack.stackSize = element.getAsJsonObject().getAsJsonArray("amount").get(0).getAsInt();

									outputs.add(stack);
								}
							} else if (element.getAsJsonObject().get("output").isJsonArray()) {

								for (JsonElement output : element.getAsJsonObject().getAsJsonArray("output"))
									if (output.isJsonPrimitive()) {
										ItemStack stack = Utils.getStackFromString(output.getAsJsonPrimitive().getAsString());
										if (stack != null)
											outputs.add(stack);
									}

								for (ItemStack stack : outputs)
									if (element.getAsJsonObject().get("amount").isJsonPrimitive())
										stack.stackSize = element.getAsJsonObject().getAsJsonPrimitive("amount").getAsInt();

									else if (element.getAsJsonObject().get("amount").isJsonArray())
										for (JsonElement elementAmount : element.getAsJsonObject().getAsJsonArray("amount"))
											if (elementAmount.isJsonPrimitive() && outputs.indexOf(stack) <= element.getAsJsonObject().getAsJsonArray("amount").size() - 1)
												stack.stackSize = element.getAsJsonObject().getAsJsonArray("amount").get(outputs.indexOf(stack)).getAsInt();
							}
							// OUTPUTS //

							// COST //
							if (element.getAsJsonObject().get("cost").isJsonPrimitive())
								cost = element.getAsJsonObject().getAsJsonPrimitive("cost").getAsInt();
							// COST //

							trades.add(new TradeInfo(outputs, cost));
						}
					}
				}
			}
		}

		List<ComponentVoid> slots = new ArrayList<>();
		for (TradeInfo info : trades) {
			TradeSlot slot = new TradeSlot(this, info);
			slots.add(slot.component);
		}

		// Split trades into 10s
		List<List<ComponentVoid>> splitPages = Lists.partition(slots, 10);

		List<List<List<ComponentVoid>>> splitColumns = new ArrayList<>();
		for (List<ComponentVoid> tradeComp : splitPages)
			splitColumns.add(Lists.partition(tradeComp, 5));
		tradeComponents.addAll(splitColumns);

		for (List<List<ComponentVoid>> pages : tradeComponents) {
			for (List<ComponentVoid> columnComps : pages) {
				ComponentList column = new ComponentList(15 + (85 * pages.indexOf(columnComps)), 27);

				for (ComponentVoid tradeComp : columnComps) column.add(tradeComp);

				new ButtonMixin<>(column, () -> {
				});
				column.BUS.hook(GuiComponent.ComponentTickEvent.class, (componentTickEvent -> {
					if (page == tradeComponents.indexOf(pages)) {
						column.setEnabled(true);
						column.setVisible(true);
					} else {
						column.setEnabled(false);
						column.setVisible(false);
					}
				}));

				compBackground.add(column);
			}
		}

		ComponentSprite nextComp = new ComponentSprite(sprArrowRight, 129 - (sprArrowRight.getWidth() / 2), 192);
		new ButtonMixin<>(nextComp, () -> {
		});
		nextComp.BUS.hook(GuiComponent.MouseInEvent.class, (mouseInEvent -> {
			nextComp.setSprite(sprArrowRightHover);
		}));
		nextComp.BUS.hook(GuiComponent.MouseOutEvent.class, (mouseOutEvent -> {
			nextComp.setSprite(sprArrowRight);
		}));
		nextComp.BUS.hook(GuiComponent.MouseClickEvent.class, (mouseClickEvent -> {
			if (page < tradeComponents.size() - 1) page++;
		}));
		compBackground.add(nextComp);

		ComponentSprite backComp = new ComponentSprite(sprArrowLeft, 45 - (sprArrowLeft.getWidth() / 2), 192);
		new ButtonMixin<>(backComp, () -> {
		});
		backComp.BUS.hook(GuiComponent.MouseInEvent.class, (mouseInEvent -> {
			backComp.setSprite(sprArrowLeftHover);
		}));
		backComp.BUS.hook(GuiComponent.MouseOutEvent.class, (mouseOutEvent -> {
			backComp.setSprite(sprArrowLeft);
		}));
		backComp.BUS.hook(GuiComponent.MouseClickEvent.class, (mouseClickEvent -> {
			if (page > 0) page--;
		}));
		compBackground.add(backComp);

		ComponentSprite walletSign = new ComponentSprite(sprGoldSign, 177, 8);
		ComponentSprite coin = new ComponentSprite(sprCoin, -2, 17);
		ComponentText amount = new ComponentText(11, 22 + (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2), ComponentText.TextAlignH.LEFT, ComponentText.TextAlignV.MIDDLE);
		amount.BUS.hook(GuiComponent.ComponentTickEvent.class, componentTickEvent -> {
			IWalletCapability walletCap = Minecraft.getMinecraft().player.getCapability(CapabilityWallet.WALLET, null);
			amount.getText().setValue(walletCap.getWallet() + "");
		});
		walletSign.add(coin, amount);
		compBackground.add(walletSign);

		int center = Minecraft.getMinecraft().fontRendererObj.getStringWidth(name);
		ComponentText title = new ComponentText(17 + (142 / 2) - (center / 2), 8 + (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2), ComponentText.TextAlignH.LEFT, ComponentText.TextAlignV.MIDDLE);
		title.getText().setValue(name);
		compBackground.add(title);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
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
