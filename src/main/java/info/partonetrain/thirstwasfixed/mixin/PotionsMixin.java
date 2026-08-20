package info.partonetrain.thirstwasfixed.mixin;

import info.partonetrain.thirstwasfixed.EarlyConfigHelper;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Potions.class)
public class PotionsMixin {
    @ModifyArg(method = "<clinit>", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffectInstance;<init>(Lnet/minecraft/core/Holder;I)V", ordinal = 6), index = 1)
    private static int thirstwasfixed$clinit1(int duration){
        EarlyConfigHelper.readConfigsEarly();
        if(EarlyConfigHelper.fireResDuration != -1){
            return EarlyConfigHelper.fireResDuration;
        }
        return duration;
    }

    @ModifyArg(method = "<clinit>", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffectInstance;<init>(Lnet/minecraft/core/Holder;I)V", ordinal = 7), index = 1)
    private static int thirstwasfixed$clinit2(int duration){
        EarlyConfigHelper.readConfigsEarly();
        if(EarlyConfigHelper.longFireResDuration != -1){
            return EarlyConfigHelper.longFireResDuration;
        }
        return duration;
    }
}
