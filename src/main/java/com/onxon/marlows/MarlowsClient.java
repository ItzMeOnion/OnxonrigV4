package com.onxon.marlows;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class MarlowsClient implements ClientModInitializer {
    public static KeyBinding OPEN_MENU;
    @Override public void onInitializeClient() {
        OPEN_MENU = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.marlows.menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_O, "key.categories.marlows"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (OPEN_MENU.wasPressed()) client.setScreen(new MarlowsMenuScreen());
        });
    }
}
