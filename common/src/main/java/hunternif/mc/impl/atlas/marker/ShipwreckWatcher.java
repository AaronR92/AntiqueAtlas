package hunternif.mc.impl.atlas.marker;

import hunternif.mc.api.AtlasAPI;
import hunternif.mc.impl.atlas.AntiqueAtlasMod;
import hunternif.mc.impl.atlas.registry.MarkerType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class ShipwreckWatcher {

    /** Put the Portal marker at the player's current coordinates into all
     * atlases that he is carrying, if the same marker is not already there. */
    private void addShipwreckMarkerIfNone(PlayerEntity player) {
        if (!AntiqueAtlasMod.CONFIG.autoShipwreckMarkers || player.getEntityWorld().isClient) {
            return;
        }

        // Due to switching dimensions this player entity's worldObj is lagging.
        // We need the very specific dimension each time.
        World world = player.getEntityWorld();

        addShipwreckMarkerIfNone(player, world, AtlasAPI.getPlayerAtlasId(player));
    }

    private void addShipwreckMarkerIfNone(PlayerEntity player, World world, int atlasID) {
        MarkerType shipwreck = MarkerType.REGISTRY.get(AntiqueAtlasMod.id("shipwreck"));
        if (shipwreck == null) {
            return;
        }

        // Can't use entity.dimension here, because its value has already been updated!
        DimensionMarkersData data = AntiqueAtlasMod.markersData.getMarkersData(atlasID, world)
                .getMarkersDataInWorld(world.getRegistryKey());

        int x = (int)player.getX();
        int z = (int)player.getZ();

        // Check if the marker already exists:
        List<Marker> markers = data.getMarkersAtChunk((x >> 4) / MarkersData.CHUNK_STEP, (z >> 4) / MarkersData.CHUNK_STEP);
        if (markers != null) {
            for (Marker marker : markers) {
                if (marker.getType().equals("antiqueatlas:shipwreck")) {
                    // Found the marker.
                    return;
                }
            }
        }

        // Marker not found, place new one:
        AtlasAPI.getMarkerAPI().putMarker(world, false, atlasID, MarkerType.REGISTRY.getId(shipwreck), Text.translatable("gui.antiqueatlas.marker.shipwreck"), x, z);
    }

}
