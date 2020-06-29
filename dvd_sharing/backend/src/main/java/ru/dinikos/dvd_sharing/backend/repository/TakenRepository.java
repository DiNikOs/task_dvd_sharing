/**
 * @author Ostrovskiy Dmitriy
 * @created 22.06.2020
 * TakenRepository
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dinikos.dvd_sharing.backend.entities.TakenItem;

import java.util.List;

@Repository
public interface TakenRepository extends JpaRepository<TakenItem, Long> {

    List<TakenItem> findTakenItemsByUserId(Long userId);
    TakenItem findTakenItemByDiskId(Long diskId);
    boolean existsByUserIdAndDiskId(Long userId, Long diskId);
    void deleteByDiskId(Long diskId);
    void deleteById(Long id);

}
