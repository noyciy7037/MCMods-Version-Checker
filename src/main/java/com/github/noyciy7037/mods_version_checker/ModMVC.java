package com.github.noyciy7037.mods_version_checker;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModMVC.MOD_ID, name = "Mods Version Checker", version = "1.0.0")
public class ModMVC {
    public static final String MOD_ID = "mods_version_checker";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        ModVersions.setupMods();
        ModsVersionCheckerHolder fc = new ModsVersionCheckerHolder(Loader.instance().getIndexedModList().get(MOD_ID));
        try {
            fc.register();
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }
}
