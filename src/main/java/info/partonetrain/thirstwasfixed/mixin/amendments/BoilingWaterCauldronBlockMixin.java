package info.partonetrain.thirstwasfixed.mixin.amendments;

import cn.mlus.thirst.content.purity.WaterPurity;
import info.partonetrain.thirstwasfixed.Config;
import info.partonetrain.thirstwasfixed.ThirstWasFixedMod;
import net.mehvahdjukaar.amendments.common.block.BoilingWaterCauldronBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BoilingWaterCauldronBlock.class)
public class BoilingWaterCauldronBlockMixin extends LayeredCauldronBlock {

    public BoilingWaterCauldronBlockMixin(Biome.Precipitation precipitationType, CauldronInteraction.InteractionMap interactions, Properties properties) {
        super(precipitationType, interactions, properties);
    }

    @ModifyArg(method = "<init>", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/LayeredCauldronBlock;<init>(Lnet/minecraft/world/level/biome/Biome$Precipitation;Lnet/minecraft/core/cauldron/CauldronInteraction$InteractionMap;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V"), index = 2)
    private static Properties trains_tweask$init(Properties properties){
        //commented out config because this value is not initialized yet when it's needed.
        //if amendments is installed cauldrons will always tick, sorry
        //if(Config.AMENDMENTS_BOIL_PURITY_CHANCE.getAsInt() != 0){
            return properties.randomTicks();
        //}
        //return properties;
    }

    @Unique
    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random){
        //ThirstWasFixedMod.LOGGER.info("Amendments water cauldron tick " + level.isClientSide() + " pos: " + pos);
        if(state.getValue(BoilingWaterCauldronBlock.BOILING)) { //not 100% sure why this is necessary, but this code is called in nonboiling cauldrons
            if (!level.isClientSide() && Config.AMENDMENTS_BOIL_PURITY_CHANCE.getAsInt() != 0) {
                int rand = Config.AMENDMENTS_BOIL_PURITY_CHANCE.getAsInt() == 1 ? 0 : level.random.nextInt(Config.AMENDMENTS_BOIL_PURITY_CHANCE.getAsInt() - 1);
                //ThirstWasFixedMod.LOGGER.info("Amendments water cauldron rolled " + rand);
                if (rand == 0) {
                    //ThirstWasFixedMod.LOGGER.info("Amendments water cauldron rolled at: " + pos);
                    int purity = state.getValue(WaterPurity.BLOCK_PURITY);
                    if (purity == 0) {
                        ThirstWasFixedMod.LOGGER.info("Amendments water cauldron at " + pos + " has purity 0, setting to default purity");
                        level.setBlock(pos, state.setValue(WaterPurity.BLOCK_PURITY, Config.DEFAULT_PURITY.get()), 3);
                        ThirstWasFixedMod.LOGGER.info("Amendments water cauldron at " + pos + " set to purity " + Config.DEFAULT_PURITY.get());
                    } else if (purity == 4) {
                        //ThirstWasFixedMod.LOGGER.info("Amendments water cauldron was already max purity");
                    } else {
                        //ThirstWasFixedMod.LOGGER.info("Amendments water cauldron purity before: " + purity);
                        purity++; //increase the purity
                        BlockState newBlockState = level.getBlockState(pos);
                        level.setBlock(pos, newBlockState.setValue(WaterPurity.BLOCK_PURITY, purity), 3);
                        //ThirstWasFixedMod.LOGGER.info("Amendments water cauldron purity after: " + level.getBlockState(pos).getValue(WaterPurity.BLOCK_PURITY));
                    }
                }
            }
            super.tick(state, level, pos, random);
        }
    }
}
