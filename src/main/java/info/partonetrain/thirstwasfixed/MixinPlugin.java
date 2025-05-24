package info.partonetrain.thirstwasfixed;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String[] packageTree = mixinClassName.split("\\.");

        if (Arrays.asList(packageTree).contains("an")) {
            return LoadingModList.get().getModFileById("ars_nouveau") != null;
        }
        if (Arrays.asList(packageTree).contains("ae")) {
            return LoadingModList.get().getModFileById("ars_elemental") != null;
        }
        if (Arrays.asList(packageTree).contains("ftbum")) {
            return LoadingModList.get().getModFileById("ftbultimine") != null;
        }
        if (Arrays.asList(packageTree).contains("supps")) {
            return LoadingModList.get().getModFileById("supplementaries") != null;
        }

        return true;
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
}
