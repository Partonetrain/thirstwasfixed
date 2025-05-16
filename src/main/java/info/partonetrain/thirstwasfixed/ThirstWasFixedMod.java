package info.partonetrain.thirstwasfixed;

import dev.ghen.thirst.content.purity.WaterPurity;
import dev.ghen.thirst.foundation.config.CommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.event.level.ChunkEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(ThirstWasFixedMod.MODID)
public class ThirstWasFixedMod
{
    public static final String MODID = "thirstwasfixed";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public ThirstWasFixedMod(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);


        modContainer.registerConfig(ModConfig.Type.STARTUP, Config.SPEC);
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
