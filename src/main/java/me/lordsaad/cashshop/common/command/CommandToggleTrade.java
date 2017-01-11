package me.lordsaad.cashshop.common.command;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.teamwizardry.librarianlib.LibrarianLib;
import com.teamwizardry.librarianlib.client.core.JsonGenerationUtils;
import me.lordsaad.cashshop.api.Constants;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import org.jetbrains.annotations.NotNull;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LordSaad.
 */
public class CommandToggleTrade extends CommandBase {
	@NotNull
	@Override
	public String getName() {
		return "toggletrade";
	}

	@NotNull
	@Override
	public String getUsage(@NotNull ICommandSender sender) {
		return "Will enable/disable a trade of a given npc";
	}

	@Override
	public void execute(@NotNull MinecraftServer server, @NotNull ICommandSender sender, @NotNull String[] args) throws CommandException {
		if (args.length < 2) {
			sender.sendMessage(new TextComponentString("Too few arguments. Usage: /toggletrade example_npc tradeIndex enable/disable"));
			return;
		}
		InputStream stream = LibrarianLib.PROXY.getResource(Constants.MOD_ID, "npcs/" + args[0] + ".json");
		if (stream != null) {

			InputStreamReader reader = new InputStreamReader(stream);
			JsonElement json = new JsonParser().parse(reader);
			if (json.isJsonObject() && json.getAsJsonObject().has("trades") && json.getAsJsonObject().get("trades").isJsonArray()) {
				JsonArray array = json.getAsJsonObject().getAsJsonArray("trades");

				List<JsonObject> trades = new ArrayList<>();
				for (JsonElement element : array)
					if (element.isJsonObject()) trades.add(element.getAsJsonObject());

				int trade = parseInt(args[1]);
				JsonObject child = trades.remove(trade);
				JsonObject modifiedjson = new JsonObject();
				modifiedjson.add("output", child.get("output"));
				modifiedjson.add("amount", child.get("amount"));
				modifiedjson.add("cost", child.get("cost"));
				if (args[2].equalsIgnoreCase("disable")) modifiedjson.addProperty("locked", true);
				else if (args[2].equalsIgnoreCase("enable")) modifiedjson.addProperty("locked", false);
				trades.add(modifiedjson);

				String name = json.getAsJsonObject().getAsJsonPrimitive("name").getAsString();

				JsonObject object = new JsonObject();
				object.addProperty("name", name);
				JsonArray newArray = new JsonArray();
				for (JsonObject newObject : trades) newArray.add(newObject);
				object.add("trades", newArray);

				String string = object.toString();

				try {
					FileWriter writer = new FileWriter(JsonGenerationUtils.INSTANCE.getAssetPath(Constants.MOD_ID) + "/npcs/" + args[0] + ".json");
					writer.write(string);
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else sender.sendMessage(new TextComponentString("NPC file name not found: " + args[0]));
	}
}
