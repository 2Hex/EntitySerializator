package me.hex.entityserializer.core.impl;

import me.hex.entityserializer.core.interfaces.Factory;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.structure.Structure;
import org.bukkit.structure.StructureManager;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of Factory, a Structure Factory that produces Structures.
 */
public class StructureFactory implements Factory<Entity, NamespacedKey, Structure> {

    private final StructureManager manager;

    public StructureFactory(StructureManager manager) {
        this.manager = manager;
    }

    /**
     * IMPORTANT NOTE: DO NOT USE THIS METHOD, IT'S FOR THE API INTERNALS.
     * Creates a structure around the entity.
     * Note that this method uses 1.17.1 Structures API.
     *
     * @param entity Entity to create a structure around.
     * @return Structure created..
     */
    @Override
    public Structure create(Entity entity, NamespacedKey keyToStruct, boolean removeAfter) {

        Structure struct = manager.createStructure();

        struct.fill(entity.getLocation(), entity.getLocation()
                .add(1, 1, 1), true);

        manager.registerStructure(keyToStruct, struct);

        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
            try {
                manager.saveStructure(keyToStruct, struct);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }).toCompletableFuture();

        try {
            if (removeAfter && future.get()) {
                future.thenApply((e) -> {
                    entity.remove();
                    return null;
                });
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return struct;
    }

    /**
     * Destroys a Structure
     *
     * @param key Key of structure to destroy.
     * @return true if successful, false otherwise.
     */
    @Override
    public CompletableFuture<Boolean> destroy(NamespacedKey key) {

        manager.unregisterStructure(key);

        return CompletableFuture.supplyAsync(() -> {
            try {
                manager.deleteStructure(key);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }).toCompletableFuture();
    }
}
