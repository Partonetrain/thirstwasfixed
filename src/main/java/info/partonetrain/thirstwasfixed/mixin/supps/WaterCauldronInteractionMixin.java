//package info.partonetrain.thirstwasfixed.mixin.supps;
//
//import dev.ghen.thirst.content.purity.WaterPurity;
//import dev.ghen.thirst.content.registry.ThirstComponent;
//import dev.ghen.thirst.foundation.config.CommonConfig;
//import info.partonetrain.thirstwasfixed.Config;
//import net.mehvahdjukaar.supplementaries.common.block.faucet.FluidOffer;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.LayeredCauldronBlock;
//import net.minecraft.world.level.block.state.BlockState;
//import org.spongepowered.asm.mixin.Mixin;
//import net.mehvahdjukaar.supplementaries.common.block.faucet.WaterCauldronInteraction;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//import net.minecraft.world.level.Level;
//@Mixin(WaterCauldronInteraction.class)
//public class WaterCauldronInteractionMixin {
//
//    //filling empty cauldron
//    @Inject(method="fill(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/mehvahdjukaar/supplementaries/common/block/faucet/FluidOffer;)Ljava/lang/Integer;", at= @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 0))
//    private void thirstwasfixed$fillCauldron(Level level, BlockPos pos, BlockState state, FluidOffer offer, CallbackInfoReturnable<Integer> cir) {
//        int purity;
//        try {
//            purity = offer.fluid().get(ThirstComponent.PURITY);
//        } catch (NullPointerException e) {
//            //No purity? set to default
//            purity = CommonConfig.DEFAULT_PURITY.get();
//        }
//        level.setBlock(pos, state.setValue(WaterPurity.BLOCK_PURITY, purity + 1), 3);
//        //block purity is offset by 1
//    }
//
//    //filling water cauldron
//    @Inject(method="fill(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/mehvahdjukaar/supplementaries/common/block/faucet/FluidOffer;)Ljava/lang/Integer;", at= @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 1))
//    private void thirstwasfixed$fillCauldron2(Level level, BlockPos pos, BlockState state, FluidOffer offer, CallbackInfoReturnable<Integer> cir) {
//        //etc
//    }
//}
//
