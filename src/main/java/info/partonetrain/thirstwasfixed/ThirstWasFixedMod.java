package info.partonetrain.thirstwasfixed;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.slf4j.Logger;

@Mod(ThirstWasFixedMod.MODID)
public class ThirstWasFixedMod
{
    public static final String MODID = "thirstwasfixed";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final MutableInt DEFAULT_BLOCK_PURITY = new MutableInt(1);
    public static AttributeModifier thirstSpeedModifier = null;

    static final ArtifactVersion MIN_THIRST_VERSION = new DefaultArtifactVersion("3.0.0");

    public ThirstWasFixedMod(IEventBus modEventBus, ModContainer modContainer)
    {
        //check to make sure we're using ThirstWasReclaimed.
        //turns out this doesn't work because mixins
        //checkForThirstWasReclaimed();

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

    /*
    public static boolean checkForThirstWasReclaimed() {
        Optional<? extends ModContainer> thirstModContainer = ModList.get().getModContainerById("thirst");
        if(thirstModContainer.isPresent()){

            if(thirstModContainer.get().getModInfo().getVersion().compareTo(MIN_THIRST_VERSION) < 0
                    || thirstModContainer.get().getModInfo().getOwningFile().getFile().getFileName().toLowerCase().contains("wastaken")) //they have the same modID, so check the file name
            {
                throw new UnsupportedOperationException("As of Thirst Was Fixed 2.0, Thirst Was Taken is no longer compatible"
                + "\nUse Thirst Was Reclaimed by mlus instead. "
                + "\nCurseForge: https://www.curseforge.com/minecraft/mc-mods/thirst-was-reclaimed"
                + "\nModrinth: https://modrinth.com/mod/thirst-was-reclaimed");
            }
        }
        else{
            throw new UnsupportedOperationException("Thirst Was Fixed requires Thirst Was Reclaimed to be installed");
            //this shouldn't happen because of neoforge.mods.toml but eh why not check for it ig
        }
        return true;
    }

     */

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ThirstValues.load();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }


    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        ChunkLoadHelper.onChunkLoad(event);
    }

}
