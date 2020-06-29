/**
 * @author Ostrovskiy Dmitriy
 * @created 20.06.2020
 * DiskService
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.services;

import ru.dinikos.dvd_sharing.backend.controllers.dto.DiskDto;
import ru.dinikos.dvd_sharing.backend.entities.Disk;
import ru.dinikos.dvd_sharing.backend.entities.User;

import java.util.List;

public interface DiskServiceInterface {

    boolean save (DiskDto diskDto);
    void delete (Disk disk);
    void deleteUserDisk(Long diskId, User user);
    boolean existsById(Long diskId);

    Disk findById (Long id);
    List<Disk> findAllDisk();
    List<Disk> findFreeDisks();
    List<Disk> findDisksByUser(User user);
    List<Disk> findDiskByUserName(String username);
    List<Disk> findTakenDisksByUser(User user);
    List<Disk> findTakenDisksByUserName(String username);
    List<Disk> findGivenDisksByUser(User user);

}
