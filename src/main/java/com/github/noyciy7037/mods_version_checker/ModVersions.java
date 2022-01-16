package com.github.noyciy7037.mods_version_checker;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraftforge.fml.common.versioning.InvalidVersionSpecificationException;
import net.minecraftforge.fml.common.versioning.VersionRange;

public class ModVersions {
    public static void setupMods() {
        MVCConfig.ConfigData.ModInfo modInfo = MVCConfig.ConfigData.modInfo;
        if (modInfo.modIDs.length != modInfo.versionSpecs.length || modInfo.modIDs.length != modInfo.messages.length) {
            CrashReport crashReport = CrashReport.makeCrashReport(new Exception("Illegal config length"), "Illegal config length");
            throw new ReportedException(crashReport);
        }
        modInfo.ranges = new VersionRange[modInfo.modIDs.length];
        for (int i = 0; i < modInfo.modIDs.length; i++) {
            try {
                modInfo.ranges[i] = VersionRange.createFromVersionSpec(modInfo.versionSpecs[i]);
            } catch (InvalidVersionSpecificationException e) {
                e.printStackTrace();
                CrashReport crashReport = CrashReport.makeCrashReport(e, "Invalid version specification");
                CrashReportCategory crashReportCategory = crashReport.makeCategory("ID of the target mod");
                int finalI = i;
                crashReportCategory.addDetail("Mod ID", () -> modInfo.modIDs[finalI]);
                throw new ReportedException(crashReport);
            }
        }
    }
}
