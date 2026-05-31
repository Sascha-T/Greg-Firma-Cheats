package de.saschat.tfcc.integrations.tfc;

import com.mojang.blaze3d.platform.InputConstants;
import de.saschat.tfcc.integrations.Integration;
import net.dries007.tfc.common.recipes.AnvilRecipe;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

public class Main implements Integration {

    public static final KeyMapping TFC_FORGE_KEY = new KeyMapping("key.terrafirmacheats.tfc.forge", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_P, "key.categories.terrafirmacheats");
    public static AnvilRecipe LAST_RECIPE = null;


    public void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(TFC_FORGE_KEY);
    }

    @Override
    public void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::registerBindings);
    }
}
