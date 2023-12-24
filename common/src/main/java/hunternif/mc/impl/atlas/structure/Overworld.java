package hunternif.mc.impl.atlas.structure;

import hunternif.mc.impl.atlas.AntiqueAtlasMod;
import hunternif.mc.impl.atlas.core.TileIdMap;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureType;

import java.util.Collection;
import java.util.Collections;

public class Overworld {

    public static void registerPieces() {
        StructureHandler.registerTile(
                StructurePieceType.RUINED_PORTAL,
                10,
                TileIdMap.RUINED_PORTAL,
                Overworld::aboveGround
        );

        if (AntiqueAtlasMod.CONFIG.autoShipwreckMarkers) {
            StructureHandler.registerTile(
                    StructurePieceType.SHIPWRECK,
                    10,
                    TileIdMap.SHIPWRECK
            );
        }
    }

    private static Collection<ChunkPos> aboveGround(World world, @SuppressWarnings("unused") StructurePoolElement structurePoolElement, BlockBox blockBox, StructurePiece piece) {
        BlockPos center = new BlockPos(blockBox.getCenter());
        if (world.getSeaLevel() - 4 <= center.getY()) {
            return Collections.singleton(new ChunkPos(center));
        }

        return Collections.emptyList();
    }
}
