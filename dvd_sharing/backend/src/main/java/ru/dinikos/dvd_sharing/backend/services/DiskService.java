/**
 * @author Ostrovskiy Dmitriy
 * @created 20.06.2020
 * ArticleService
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dinikos.dvd_sharing.backend.controllers.dto.DiskDto;
import ru.dinikos.dvd_sharing.backend.controllers.dto.DiskResponseDto;
import ru.dinikos.dvd_sharing.backend.controllers.rest.UserRestController;
import ru.dinikos.dvd_sharing.backend.entities.Disk;
import ru.dinikos.dvd_sharing.backend.entities.User;
import ru.dinikos.dvd_sharing.backend.repository.DiskRepository;
import ru.dinikos.dvd_sharing.backend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiskService implements DiskServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    private DiskRepository diskRepository;
    private UserRepository userRepository;
    private TakenService takenService;

    public DiskService() {

    }

    @Autowired
    public DiskService(DiskRepository diskRepository, UserRepository userRepository, TakenService takenService) {
        this.diskRepository = diskRepository;
        this.userRepository = userRepository;
        this.takenService = takenService;
    }

    /**
     * Метод, возвращающий список дисков
     */
    @Override
    @Transactional
    public List<Disk> findAllDisk() {
        return diskRepository.findAll();
    }

    /**
     * Метод, возвращающий диск по id
     */
    @Override
    public Disk findById(Long id) {
        Disk disk = diskRepository.findById(id).orElse(null);
        return disk;
    }

    /**
     * Метод возвращает диски по объекту пользователь
     */
    @Override
    @Transactional
    public List<Disk> findDisksByUser(User user) {
        return diskRepository.findDisksByUser(user);
    }

    /**
     * Метод возвращает диски по имени пользователя
     */
    @Override
    @Transactional
    public List<Disk> findDiskByUserName(String username) {
        return diskRepository.findDisksByUser(userRepository.findOneByUsername(username));
    }

    /**
     * Метод возвращает  свободные диски
     * Происходит сравнение взятых items и всех items
     */
    @Override
    @Transactional
    public List<Disk> findFreeDisks() {
        List<Disk> diskFree = diskRepository.findFreeDisksQuery();
        logger.info("Free disks= " + diskFree.toString());
        return diskFree;
    }

    /**
     * Метод возвращает взятые по имени пользователя диски
     */
    @Override
    @Transactional
    public List<Disk> findTakenDisksByUser(User user) {
        return diskRepository.findDisksByUserTaken(user);
    }

    /**
     * Метод возвращает взятые по имени пользователя диски
     */
    @Override
    @Transactional
    public List<Disk> findTakenDisksByUserName(String username) {
        return diskRepository.findDisksByUserTaken(userRepository.findOneByUsername(username));
    }

    /**
     * Метод возвращает взятые у пользователя (отданные пользователем) диски
     */
    @Override
    @Transactional
    public List<Disk> findGivenDisksByUser(User user) {
        List<Disk> listGivenDisk = new ArrayList<>();
        Long id = user.getId();
        listGivenDisk = diskRepository.findGivenDisksQuery(id);
        logger.info("Given disks= " + listGivenDisk.toString());
        return listGivenDisk;
    }

    @Override
    @Transactional
    public boolean save(DiskDto diskDto) {
        User user = userRepository.findOneById(diskDto.getUserId());
        Disk disk = new Disk(diskDto.getCreated(), diskDto.getTitle(), diskDto.getLastDate(), user);
        List<Disk> duplDisk = diskRepository.findDuplicateQuery(disk.getTitle(), diskDto.getUserId());
        if (duplDisk.size()!=0) {
            return false;
        }
        diskRepository.save(disk);
        return true;
    }

    @Override
    public void delete(Disk disk) {
        diskRepository.delete(disk);
    }

    /**
     * Метод удаления дисков из репозитория по имени пользователя
     */
    @Override
    @Transactional
    public void deleteUserDisk(Long diskId, User user) {
        Disk disk = diskRepository.getOne(diskId);
        List<Disk> disksUser = diskRepository.findDisksByUser(user);
        if (disksUser.contains(disk))
        diskRepository.delete(disk);
    }

    @Override
    public boolean existsById(Long diskId) {
        return diskRepository.existsById(diskId);
    }

    public List<DiskResponseDto> getDiskDtoFromDisk (List<Disk> disks) {
        List<DiskResponseDto> disksDto = disks.stream().map(disk -> {
            String userTaken;
            if (disk.getUserTaken()==null) {
                userTaken = "NOT TAKEN USER";
            } else userTaken = disk.getUserTaken().getUsername();
            DiskResponseDto diskResponseDto = new DiskResponseDto(
                    disk.getId(), disk.getCreated(), disk.getTitle(),
                    disk.getUser().getUsername(), userTaken);
            return diskResponseDto;
        }).collect(Collectors.toList());
        return disksDto;
    }

    public ResponseEntity<?> getResponse (List<Disk> disks) {
        List<DiskResponseDto> getDisks = getDiskDtoFromDisk(disks);
        return disks == null && disks.isEmpty()? new ResponseEntity<>("Not found!", HttpStatus.NOT_FOUND)
                :new ResponseEntity<List<DiskResponseDto>>(getDisks, HttpStatus.OK);
    }
}
