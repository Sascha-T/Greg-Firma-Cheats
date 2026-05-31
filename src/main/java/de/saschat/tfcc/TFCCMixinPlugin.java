package de.saschat.tfcc;

import net.minecraftforge.fml.ModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.service.MixinService;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class TFCCMixinPlugin implements IMixinConfigPlugin {
    public static final String MIXIN_BASE_PACKAGE = "de.saschat.tfcc.mixin";
    public static final Map<String, String> TESTERS = Map.of(
            "tfc", "net.dries007.tfc.TerraFirmaCraft",
            "alekiships", "com.alekiponi.alekiships.AlekiShips"
    );

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String modName = mixinClassName.substring(MIXIN_BASE_PACKAGE.length()+1).split("\\.")[0];
        boolean load = hasClass(TESTERS.get(modName));
        System.out.printf("Checking whether to apply mixin %s for mod %s: %b%n", mixinClassName, modName, load);
        return load;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    private static boolean hasClass(String name) {
        try {
            MixinService.getService().getBytecodeProvider().getClassNode(name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
