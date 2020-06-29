/**
 * @author Ostrovskiy Dmitriy
 * @created 22.06.2020
 * TakenItemInterface
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.services;

import ru.dinikos.dvd_sharing.backend.entities.TakenItem;

import java.util.List;

public interface TakenServiceInterface {

    void saveTakenItem(Long userId, Long diskId);
    void deliteTakenItem(Long userId);

    boolean isExists(Long userId, Long diskId);
    TakenItem findTakenItemByDiskId(Long id);
    List<TakenItem> findTakenItemsByUserId(Long id);
    List<TakenItem> findAllTakenItems();

}
