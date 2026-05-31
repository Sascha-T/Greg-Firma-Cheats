package de.saschat.tfcc;

import de.saschat.tfcc.integrations.tfc.Main;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkConstants;

import java.util.HashMap;
import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("terrafirmacheats")
public class TFCCMod {

    public static final String[] INTEGRATIONS = new String[] {
            "tfc",
            "alekiships"
    };
    public static final Map<String, Object> INTEGRATION_MAP = new HashMap<>();
    public TFCCMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));

        for (String integration : INTEGRATIONS) {
            if(ModList.get().isLoaded(integration)) {
                System.out.printf("Found %s. Loading integration...%n", integration);
                try {
                    Class<?> clazz = Class.forName("de.saschat.tfcc.integrations." + integration + ".Main");
                    Object obj = clazz.newInstance();
                    clazz.getMethod("register").invoke(obj);
                    INTEGRATION_MAP.put(integration, obj);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

}
