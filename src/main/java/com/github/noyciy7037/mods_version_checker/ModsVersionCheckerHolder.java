package com.github.noyciy7037.mods_version_checker;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.NetworkModHolder;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;
import net.minecraftforge.fml.relauncher.Side;

import java.lang.reflect.Field;
import java.util.Map;

public class ModsVersionCheckerHolder extends NetworkModHolder {
    public ModsVersionCheckerHolder(ModContainer container) {
        super(container);
    }

    public class Checker extends NetworkModHolder.NetworkChecker {
        @Override
        public boolean check(Map<String, String> remoteVersions, Side side) {
            return checkCompatible(remoteVersions, side) == null;
        }

        @Override
        public String checkCompatible(Map<String, String> remoteVersions, Side side) {
            MVCConfig.ConfigData.ModInfo modInfo = MVCConfig.ConfigData.modInfo;
            int errorCount = 0;
            StringBuilder message = new StringBuilder("\n");
            Map<String, ModContainer> modList = Loader.instance().getIndexedModList();
            for (int i = 0; i < modInfo.modIDs.length; i++) {
                String modID = modInfo.modIDs[i];
                if (remoteVersions.containsKey(modID)) {
                    if (!modInfo.ranges[i].containsVersion(
                            new DefaultArtifactVersion(remoteVersions.get(modID)))) {
                        ++errorCount;
                        message.append(String.format(modInfo.messages[i].replace("\\n", "\n"), modID
                                        , modList.get(modID) != null ? modList.get(modID).getName() : modID,
                                        remoteVersions.get(modID),
                                        modInfo.versionSpecs[i]
                                ))
                                .append("\n");
                    }
                } else {
                    ++errorCount;
                    message.append(String.format(MVCConfig.ConfigData.modNotFoundMessage, modID
                            , modList.get(modID) != null ? modList.get(modID).getName() : modID,
                            modInfo.versionSpecs[i]));
                }
            }
            return errorCount == 0 ? null : message.toString();
        }

        @Override
        public String toString() {
            return "Checking Mod(s) version";
        }
    }

    public void register() throws NoSuchFieldException, IllegalAccessException {
        Class<NetworkModHolder> clazz = NetworkModHolder.class;
        Field checker = clazz.getDeclaredField("checker");
        checker.setAccessible(true);
        checker.set(NetworkRegistry.INSTANCE.registry().get(this.getContainer()), new Checker());
    }
}