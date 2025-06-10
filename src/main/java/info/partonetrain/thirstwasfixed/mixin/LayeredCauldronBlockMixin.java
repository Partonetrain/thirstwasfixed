package info.partonetrain.thirstwasfixed.mixin;

import dev.ghen.thirst.content.purity.WaterPurity;
import dev.ghen.thirst.foundation.common.capability.IThirst;
import dev.ghen.thirst.foundation.common.capability.ModAttachment;
import dev.ghen.thirst.foundation.config.CommonConfig;
import info.partonetrain.thirstwasfixed.Config;
import info.partonetrain.thirstwasfixed.ThirstWasFixedMod;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayeredCauldronBlock.class)
public class LayeredCauldronBlockMixin extends Block {
    public LayeredCauldronBlockMixin(Properties properties) {
        super(properties);
    }

    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(Config.DRINK_CAULDRONS.get() && player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()){
            int fullness = state.getValue(LayeredCauldronBlock.LEVEL);

            int purity = state.getValue(WaterPurity.BLOCK_PURITY);
            if(purity == 0){
                purity = CommonConfig.DEFAULT_PURITY.get();
            }

            purity--; //block purity is offset by 1
            IThirst thirst = player.getData(ModAttachment.PLAYER_THIRST);
            if(thirst.getThirst() == 20){
                return InteractionResult.FAIL;
            }
            else{
                thirst.drink(CommonConfig.HAND_DRINKING_HYDRATION.get().intValue(), CommonConfig.HAND_DRINKING_QUENCHED.get().intValue());
                WaterPurity.givePurityEffects(player, purity);
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0F, 1.0F);

                //lower cauldron level
                if (state.getValue(LayeredCauldronBlock.LEVEL) >= LayeredCauldronBlock.MIN_FILL_LEVEL){
                    int nextFullness = fullness - 1;
                    if(nextFullness == 0){
                        level.setBlock(hitResult.getBlockPos(), Blocks.CAULDRON.defaultBlockState(), 3);
                    }else{
                        level.setBlock(hitResult.getBlockPos(), state.setValue(LayeredCauldronBlock.LEVEL, nextFullness), 3);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Inject(method = "receiveStalactiteDrip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;gameEvent(Lnet/minecraft/core/Holder;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/gameevent/GameEvent$Context;)V", ordinal = 0))
    public void thristwasfixed$receiveStalactiteDrip(BlockState state, Level level, BlockPos pos, Fluid fluid, CallbackInfo ci){
        level.setBlock(pos, state.setValue(WaterPurity.BLOCK_PURITY, Config.DRIPSTONE_PURITY.getAsInt() + 1), 3);
    }

    @ModifyArg(method = "<init>", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/LayeredCauldronBlock;registerDefaultState(Lnet/minecraft/world/level/block/state/BlockState;)V"))
    public BlockState thirstwasfixed$registerDefaultState(BlockState state) {
        //set the default purity to the hardcoded constant
        //can't make this configurable unfortunately
        return state.setValue(WaterPurity.BLOCK_PURITY, ThirstWasFixedMod.DEFAULT_BLOCK_PURITY.getValue());
    }
}
