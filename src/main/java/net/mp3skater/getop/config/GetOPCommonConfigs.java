package net.mp3skater.getop.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GetOPCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Double> END_SCEPTRE_REACH_DISTANCE;
    public static final ForgeConfigSpec.ConfigValue<Double> DEATH_SWORD_REACH_DISTANCE;
    public static final ForgeConfigSpec.ConfigValue<Double> HEROBRINE_SWORD_REACH_DISTANCE;
    public static final ForgeConfigSpec.ConfigValue<Double> ICE_SCYTHE_RADIUS;

    public static final ForgeConfigSpec.ConfigValue<Integer> PAINITE_ORE_VEIN_SIZE;

    static {
        BUILDER.push("Configs for the Get OP Mod");

        END_SCEPTRE_REACH_DISTANCE = BUILDER.comment("How long the reach distance of the teleport ability!")
                .defineInRange("Reach Distance", 30d, 8d, 75d);
        DEATH_SWORD_REACH_DISTANCE = BUILDER.comment("How long the reach distance of death-ray ability!")
                .defineInRange("Reach Distance", 20d, 5d, 50d);
        ICE_SCYTHE_RADIUS = BUILDER.comment("How big the radius of freeze ability!")
                .defineInRange("Radius", 8d, 4d, 20d);
        HEROBRINE_SWORD_REACH_DISTANCE = BUILDER.comment("How long the reach distance of herobrine ability!")
                .defineInRange("Reach Distance", 30d, 5d, 50d);
        PAINITE_ORE_VEIN_SIZE = BUILDER.comment("How much Painite spawns in your world!")
                .defineInRange("Veins per Chunk(1 is very rare!)", 3, 1, 15);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
