package me.lordsaad.cashshop.client.gui.trade;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.teamwizardry.librarianlib.LibrarianLib;
import com.teamwizardry.librarianlib.client.gui.GuiBase;
import me.lordsaad.cashshop.api.Constants;
import me.lordsaad.cashshop.api.Utils;
import net.minecraft.item.ItemStack;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LordSaad.
 */
public class GuiTrade extends GuiBase {

    public String name;
    public List<TradeObject> trades;

    public GuiTrade(String npcFile) {
        super(256, 256);

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

                            trades.add(new TradeObject(outputs, cost));
                        }
                    }
                }
            }
        }
    }

    public class TradeObject {

        public List<ItemStack> outputs;
        public int cost;

        public TradeObject(List<ItemStack> outputs, int cost) {

            this.outputs = outputs;
            this.cost = cost;
        }
    }
}
