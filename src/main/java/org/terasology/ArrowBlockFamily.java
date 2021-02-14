// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology;

import org.joml.Vector3i;
import org.joml.Vector3ic;
import org.terasology.math.Rotation;
import org.terasology.math.Side;
import org.terasology.math.SideBitFlag;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockBuilderHelper;
import org.terasology.world.block.BlockUri;
import org.terasology.world.block.family.BlockFamily;
import org.terasology.world.block.family.BlockSections;
import org.terasology.world.block.family.MultiConnectFamily;
import org.terasology.world.block.family.RegisterBlockFamily;
import org.terasology.world.block.loader.BlockFamilyDefinition;

@RegisterBlockFamily("arrow")
@BlockSections({"no_connections", "left_end", "right_end", "front_end", "back_end", "line_connection", "2d_corner",
        "2d_t", "cross"})
public class ArrowBlockFamily extends MultiConnectFamily {

    public ArrowBlockFamily(BlockFamilyDefinition definition, BlockBuilderHelper blockBuilder) {
        super(definition, blockBuilder);

        BlockUri blockUri = new BlockUri(definition.getUrn());

        this.registerBlock(blockUri, definition, blockBuilder, "no_connections", (byte) 0,
                Rotation.horizontalRotations());
        this.registerBlock(blockUri, definition, blockBuilder, "left_end", SideBitFlag.getSide(Side.RIGHT),
                Rotation.horizontalRotations());
        this.registerBlock(blockUri, definition, blockBuilder, "right_end", SideBitFlag.getSide(Side.LEFT),
                Rotation.horizontalRotations());
        this.registerBlock(blockUri, definition, blockBuilder, "front_end", SideBitFlag.getSide(Side.BACK),
                Rotation.horizontalRotations());
        this.registerBlock(blockUri, definition, blockBuilder, "back_end", SideBitFlag.getSide(Side.FRONT),
                Rotation.horizontalRotations());
        this.registerBlock(blockUri, definition, blockBuilder, "line_connection", SideBitFlag.getSides(Side.FRONT,
                Side.BACK), Rotation.horizontalRotations());
        this.registerBlock(blockUri, definition, blockBuilder, "2d_corner", SideBitFlag.getSides(Side.LEFT,
                Side.BACK), Rotation.horizontalRotations());
        this.registerBlock(blockUri, definition, blockBuilder, "2d_t", SideBitFlag.getSides(Side.LEFT, Side.BACK,
                Side.RIGHT), Rotation.horizontalRotations());
        this.registerBlock(blockUri, definition, blockBuilder, "cross", SideBitFlag.getSides(Side.LEFT, Side.FRONT,
                Side.RIGHT, Side.BACK), Rotation.horizontalRotations());
    }

    @Override
    protected boolean connectionCondition(Vector3ic blockLocation, Side connectSide) {
        Vector3i neighborLocation = new Vector3i(blockLocation);
        neighborLocation.add(connectSide.direction());
        if (worldProvider.isBlockRelevant(neighborLocation)) {
            Block neighborBlock = worldProvider.getBlock(neighborLocation);
            final BlockFamily blockFamily = neighborBlock.getBlockFamily();
            if (blockFamily instanceof ArrowBlockFamily) {
                return true;
            }
        }
        return false;
    }

    @Override
    public byte getConnectionSides() {
        return SideBitFlag.getSides(Side.FRONT, Side.BACK, Side.LEFT, Side.RIGHT);
    }

    @Override
    public Block getArchetypeBlock() {
        return blocks.get((byte) 0);
    }

}
