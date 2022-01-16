package com.github.noyciy7037.mods_version_checker;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.versioning.VersionRange;

public class MVCConfig {
    @Config(modid = ModMVC.MOD_ID, type = Config.Type.INSTANCE)
    public static class ConfigData {
        @Config.Comment({"An array that specifies Mod information such as Mod ID and version range.",
                "The same index corresponds to each."})
        public static ModInfo modInfo = new ModInfo();
        @Config.Comment({"Message when there is no mod on the client side.",
                "arguments:",
                "%1$s: Mod ID",
                "%2$s: Mod Name(If null, ID)",
                "%3$s: VersionSpec"})
        public static String modNotFoundMessage = "%2$s could not be found, please install %2$s(%1$s%3$s).";

        public static class ModInfo {
            public String[] modIDs = {"forge"};
            public String[] versionSpecs = {"[14.23.5.2857,)"};
            @Config.Comment({"Specify the error message for each Mod.",
                    "arguments:",
                    "%1$s: Mod ID",
                    "%2$s: Mod Name(If null, ID)",
                    "%3$s: Client Version",
                    "%4$s: VersionSpec"})
            public String[] messages = {"%2$s version %3$s was rejected due to a Log4Shell vulnerability.\\n"
                    + "Use the following: %1$s%4$s"};
            @Config.Ignore
            public VersionRange[] ranges;
        }
    }
}
