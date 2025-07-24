package info.partonetrain.thirstwasfixed;

import com.mojang.logging.LogUtils;
import dev.ghen.thirst.content.purity.WaterPurity;
import dev.ghen.thirst.foundation.config.CommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;

@Mod(ThirstWasFixedMod.MODID)
public class ThirstWasFixedMod
{
    public static final String MODID = "thirstwasfixed";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final MutableInt DEFAULT_BLOCK_PURITY = new MutableInt(1);

    public static AttributeModifier thirstSpeedModifier = null;
    public ThirstWasFixedMod(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);

        if(ModList.get().isLoaded("ftbultimine")){
            ThirstWasFixedUltimineRestrictionHandler.register();
        }

        modContainer.registerConfig(ModConfig.Type.STARTUP, Config.SPEC);
        thirstSpeedModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MODID, "speed_modifier"), Config.THIRST_BONUS_VALUE.get(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        if(ModList.get().isLoaded("parcool")){
            ParCoolHelper.initModifiers();
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ThirstValues.load();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    //this is a really stupid way to do this. there's probably a better way
    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        if (Config.FIX_CAULDRONS.get() && event.getChunk() instanceof LevelChunk levelChunk) {
            if(levelChunk.getLevel() instanceof ServerLevel level){
                ChunkPos chunkPos = levelChunk.getPos();

                for (int x = chunkPos.getMinBlockX(); x <= chunkPos.getMaxBlockX(); x++) {
                    for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); y++) {
                        for (int z = chunkPos.getMinBlockZ(); z <= chunkPos.getMaxBlockZ(); z++) {
                            BlockPos pos = new BlockPos(x, y, z);
                            if (levelChunk.getBlockState(pos).is(Blocks.WATER_CAULDRON)) {
                                LOGGER.info("Water cauldron found at: " + pos);
                                BlockState state = level.getBlockState(pos);
                                int purity = state.getValue(WaterPurity.BLOCK_PURITY);
                                if(purity == 0) {
                                    LOGGER.info("Water cauldron at " + pos + " has purity 0, setting to default purity");
                                    level.setBlock(pos, state.setValue(WaterPurity.BLOCK_PURITY, CommonConfig.DEFAULT_PURITY.get()), 3);
                                    LOGGER.info("Water cauldron at " + pos + " set to purity " + CommonConfig.DEFAULT_PURITY.get());
                                }
                                else{
                                    LOGGER.info("Water cauldron at: " + pos + " has purity " + purity + ", leaving it alone");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
