/**
 * @author Ostrovskiy Dmitriy
 * @created 22.06.2020
 * TakenItem
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dinikos.dvd_sharing.backend.entities.TakenItem;
import ru.dinikos.dvd_sharing.backend.repository.TakenRepository;

import java.util.List;

@Service
public class TakenService implements TakenServiceInterface {

    private TakenRepository takenRepository;

    @Autowired
    public void setTakenRepository(TakenRepository takenRepository) {
        this.takenRepository = takenRepository;
    }

    @Override
    public List<TakenItem> findAllTakenItems() {
        return takenRepository.findAll();
    }

    /**
     * Метод возвращает список items по userId пользователя
     */
    @Override
    public List<TakenItem> findTakenItemsByUserId(Long userId) {
        return takenRepository.findTakenItemsByUserId(userId);
    }

    /**
     * Метод возвращает item по diskId диска
     */
    @Override
    public TakenItem findTakenItemByDiskId(Long diskId) {
        return takenRepository.findTakenItemByDiskId(diskId);
    }

    /**
     * Метод проверяет наличие взятия диска по userId и diskId
     */
    @Override
    public boolean isExists(Long userId, Long diskId) {
        return takenRepository.existsByUserIdAndDiskId(userId,diskId);
    }

    /**
     * Метод сохраняет userId пользователя взявшего диск
     * сохраняет diskId взятого диска
     */
    @Override
    public void saveTakenItem(Long userId, Long diskId) {
        TakenItem takenItem = new TakenItem();
        takenItem.setUserId(userId);
        takenItem.setDiskId(diskId);
        takenRepository.save(takenItem);
    }

    /**
     * Метод удаляет item по userId пользователя
     * из списка взятых
     */
    @Override
    public void deliteTakenItem(Long diskId) {
        takenRepository.deleteById(takenRepository.findTakenItemByDiskId(diskId).getId());
    }
}
