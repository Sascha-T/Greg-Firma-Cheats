package de.saschat.tfcc.integrations.alekiships;

import com.mojang.blaze3d.platform.InputConstants;
import de.saschat.tfcc.integrations.Integration;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

public class Main implements Integration {
    public static final KeyMapping TFC_ALEKIS_KEY = new KeyMapping("key.terrafirmacheats.alekiships.toggle", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F5, "key.categories.terrafirmacheats");
    public static boolean isEnabled = false;
    public void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(TFC_ALEKIS_KEY);
    }

    @Override
    public void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::registerBindings);
        MinecraftForge.EVENT_BUS.addListener(this::onKeyPress);
    }

    private boolean wasPressed = false;

    @SubscribeEvent
    public void onKeyPress(InputEvent.Key event) {
        if(TFC_ALEKIS_KEY.isDown()) {
            if(!wasPressed) {
                isEnabled = !isEnabled;
                System.out.printf("Toggled Alekis Autosteer State: %b%n", isEnabled);
            }
            wasPressed = true;
        } else wasPressed = false;

    }
}
