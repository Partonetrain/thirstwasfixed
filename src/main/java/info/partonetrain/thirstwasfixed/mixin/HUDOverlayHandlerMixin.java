package info.partonetrain.thirstwasfixed.mixin;

import cn.mlus.thirst.foundation.gui.appleskin.HUDOverlayHandler;
import info.partonetrain.thirstwasfixed.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HUDOverlayHandler.class)
public class HUDOverlayHandlerMixin {
    @ModifyVariable(method = "drawSaturationOverlay", at= @At(value = "HEAD"), index = 3, argsOnly = true)
    private static int thirstwasfixed$renderThirstOverlay(int arg3){
        return arg3 + Config.QUENCH_OVERLAY_RIGHT_OFFSET.getAsInt();
    }

    @ModifyVariable(method = "drawSaturationOverlay", at= @At(value = "HEAD"), index = 4, argsOnly = true)
    private static int thirstwasfixed$renderThirstOverlay2(int arg4){
        return arg4 + Config.QUENCH_OVERLAY_TOP_OFFSET.getAsInt();
    }

    @ModifyVariable(method = "drawHungerOverlay", at= @At(value = "HEAD"), index = 3, argsOnly = true)
    private static int thirstwasfixed$drawHungerOverlay(int arg3){
        return arg3 + Config.QUENCH_OVERLAY_RIGHT_OFFSET.getAsInt();
    }

    @ModifyVariable(method = "drawHungerOverlay", at= @At(value = "HEAD"), index = 4, argsOnly = true)
    private static int thirstwasfixed$drawHungerOverlay2(int arg4){
        return arg4 + Config.QUENCH_OVERLAY_TOP_OFFSET.getAsInt();
    }

    @ModifyVariable(method = "drawExhaustionOverlay", at= @At(value = "HEAD"), index = 2, argsOnly = true)
    private static int thirstwasfixed$drawExhaustionOverlay(int arg2){
        return arg2 + Config.QUENCH_OVERLAY_RIGHT_OFFSET.getAsInt();
    }

    @ModifyVariable(method = "drawExhaustionOverlay", at= @At(value = "HEAD"), index = 3, argsOnly = true)
    private static int thirstwasfixed$drawExhaustionOverlay2(int arg3){
        return arg3 + Config.QUENCH_OVERLAY_TOP_OFFSET.getAsInt();
    }
}
