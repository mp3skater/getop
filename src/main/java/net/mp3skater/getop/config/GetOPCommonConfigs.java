package net.mp3skater.getop.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GetOPCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Double> END_SCEPTRE_REACH_DISTANCE;
    public static final ForgeConfigSpec.ConfigValue<Integer> PAINITE_ORE_VEIN_SIZE;

    static {
        BUILDER.push("Configs for the Get OP Mod");

        END_SCEPTRE_REACH_DISTANCE = BUILDER.comment("How long the reach distance of the teleport function!")
                .defineInRange("Reach Distance", 50d, 6d, 100d);
        PAINITE_ORE_VEIN_SIZE = BUILDER.comment("How much Painite spawns in your world!")
                .defineInRange("Veins per Chunk(1 is really rare!)", 3, 1, 10);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
