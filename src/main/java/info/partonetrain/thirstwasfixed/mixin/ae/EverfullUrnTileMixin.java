package info.partonetrain.thirstwasfixed.mixin.ae;

import alexthw.ars_elemental.common.blocks.EverfullUrnTile;
import dev.ghen.thirst.content.purity.WaterPurity;
import info.partonetrain.thirstwasfixed.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EverfullUrnTile.class)
public class EverfullUrnTileMixin {

    //set cauldron purity
    @Inject(method="tryRefill", at=@At(value = "INVOKE_ASSIGN", target = "Lnet/neoforged/neoforge/fluids/capability/IFluidHandler;fill(Lnet/neoforged/neoforge/fluids/FluidStack;Lnet/neoforged/neoforge/fluids/capability/IFluidHandler$FluidAction;)I", ordinal = 1), cancellable = true)
    private void tryRefill(Level level, BlockPos toPos, CallbackInfoReturnable<Boolean> cir){
        if(level.getBlockState(toPos).is(Blocks.WATER_CAULDRON)){
            BlockState state = level.getBlockState(toPos);
            level.setBlock(toPos, state.setValue(WaterPurity.BLOCK_PURITY, Config.AE_EVERFULL_PURITY.getAsInt() + 1), 3);
            //block purity of offset by 1
        }
    }
}
