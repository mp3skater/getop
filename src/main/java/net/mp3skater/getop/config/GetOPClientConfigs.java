package net.mp3skater.getop.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GetOPClientConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("Configs for the Get OP Mod");

        //Forge-Configs

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
