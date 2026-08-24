package info.partonetrain.thirstwasfixed.mixin.moonlight;

import cn.mlus.thirst.content.purity.WaterPurity;
import cn.mlus.thirst.content.registry.ThirstComponent;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import info.partonetrain.thirstwasfixed.Config;
import net.mehvahdjukaar.moonlight.api.fluids.MLBuiltinSoftFluids;
import net.mehvahdjukaar.moonlight.api.fluids.SoftFluidStack;
import net.mehvahdjukaar.moonlight.api.fluids.SoftFluidTank;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(SoftFluidTank.class)
public class SoftFluidTankMixin {
    @ModifyReturnValue(method = "isFluidCompatible", at=@At("RETURN"))
    public boolean thirstwasfixed$isFluidCompatible(boolean original, @Local(argsOnly = true) SoftFluidStack otherFluidStack){
        if(Config.SOFT_FLUID_WATER_MERGE.getAsBoolean() && WaterPurity.isEnabled()){
            SoftFluidTank self = (SoftFluidTank) (Object) this;
            if(self.getFluid().is(MLBuiltinSoftFluids.WATER) && otherFluidStack.is(MLBuiltinSoftFluids.WATER)){
                return true; //return true to ignore components
            }
        }

        return original;
    }

    @Inject(method = "addFluidOntoExisting", at=@At("HEAD"), cancellable = true)
    public void thirstwasfixed$addFluidOntoExisting(SoftFluidStack otherFluidStack, CallbackInfo ci){
        if(Config.SOFT_FLUID_WATER_MERGE.getAsBoolean() && WaterPurity.isEnabled()) {
            SoftFluidTank self = (SoftFluidTank) (Object) this;
            if(self.getFluid().is(MLBuiltinSoftFluids.WATER) && otherFluidStack.is(MLBuiltinSoftFluids.WATER)){
                Integer selfPurity = self.getFluid().get(ThirstComponent.PURITY);
                Integer incomingPurity = otherFluidStack.get(ThirstComponent.PURITY);
                if(selfPurity != null && incomingPurity != null && (!Objects.equals(selfPurity, incomingPurity))){
                    int minPurity = Math.min(selfPurity, incomingPurity);
                    self.getFluid().set(ThirstComponent.PURITY, minPurity);
                    self.getFluid().grow(otherFluidStack.getCount());
                    ci.cancel();
                }
            }
        }
    }
}
