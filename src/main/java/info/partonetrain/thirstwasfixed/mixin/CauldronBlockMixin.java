package info.partonetrain.thirstwasfixed.mixin;

import dev.ghen.thirst.content.purity.WaterPurity;
import info.partonetrain.thirstwasfixed.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CauldronBlock.class)
public class CauldronBlockMixin {
    @Inject(method = "handlePrecipitation", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;gameEvent(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/Holder;Lnet/minecraft/core/BlockPos;)V", ordinal = 0))
    public void thristwasfixed$handlePrecipitation(BlockState state, Level level, BlockPos pos, Biome.Precipitation precipitation, CallbackInfo ci){
        BlockState newBlockState = level.getBlockState(pos); //it will have changed to a water cauldron by this point
        level.setBlock(pos, newBlockState.setValue(WaterPurity.BLOCK_PURITY, Config.RAINWATER_PURITY.getAsInt() + 1), 3);
    }

    @Inject(method = "receiveStalactiteDrip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;gameEvent(Lnet/minecraft/core/Holder;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/gameevent/GameEvent$Context;)V", ordinal = 0))
    public void thristwasfixed$receiveStalactiteDrip(BlockState state, Level level, BlockPos pos, Fluid fluid, CallbackInfo ci){
        BlockState newBlockState = level.getBlockState(pos);
        level.setBlock(pos, newBlockState.setValue(WaterPurity.BLOCK_PURITY, Config.DRIPSTONE_PURITY.getAsInt() + 1), 3);
    }
}
