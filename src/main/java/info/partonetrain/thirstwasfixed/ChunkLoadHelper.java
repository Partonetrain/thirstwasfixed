package info.partonetrain.thirstwasfixed;

import cn.mlus.thirst.content.purity.WaterPurity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.event.level.ChunkEvent;

public class ChunkLoadHelper {

    //this is a really stupid way to do this. there's probably a better way
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (Config.FIX_CAULDRONS.get() && event.getChunk() instanceof LevelChunk levelChunk) {
            if(levelChunk.getLevel() instanceof ServerLevel level){
                ChunkPos chunkPos = levelChunk.getPos();

                for (int x = chunkPos.getMinBlockX(); x <= chunkPos.getMaxBlockX(); x++) {
                    for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); y++) {
                        for (int z = chunkPos.getMinBlockZ(); z <= chunkPos.getMaxBlockZ(); z++) {
                            BlockPos pos = new BlockPos(x, y, z);
                            if (levelChunk.getBlockState(pos).is(Blocks.WATER_CAULDRON)) {
                                ThirstWasFixedMod.LOGGER.info("Water cauldron found at: " + pos);
                                BlockState state = level.getBlockState(pos);
                                int purity = state.getValue(WaterPurity.BLOCK_PURITY);
                                if(purity == 0) {
                                    ThirstWasFixedMod.LOGGER.info("Water cauldron at " + pos + " has purity 0, setting to default purity");
                                    level.setBlock(pos, state.setValue(WaterPurity.BLOCK_PURITY, Config.DEFAULT_PURITY.get()), 3);
                                    ThirstWasFixedMod.LOGGER.info("Water cauldron at " + pos + " set to purity " + Config.DEFAULT_PURITY.get());
                                }
                                else{
                                    ThirstWasFixedMod.LOGGER.info("Water cauldron at: " + pos + " has purity " + purity + ", leaving it alone");
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
