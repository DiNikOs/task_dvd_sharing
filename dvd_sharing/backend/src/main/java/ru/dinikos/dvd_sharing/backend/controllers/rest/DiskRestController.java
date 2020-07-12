/**
 * @author Ostrovskiy Dmitriy
 * @created 09.06.2020
 * DataController
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.dinikos.dvd_sharing.backend.controllers.dto.DiskDto;
import ru.dinikos.dvd_sharing.backend.controllers.dto.DiskResponseDto;
import ru.dinikos.dvd_sharing.backend.entities.Disk;
import ru.dinikos.dvd_sharing.backend.exception.DataErrorResponse;
import ru.dinikos.dvd_sharing.backend.exception.DataNotFoundException;
import ru.dinikos.dvd_sharing.backend.exception.ValidationErrorBuilder;
import ru.dinikos.dvd_sharing.backend.services.DiskService;

import javax.servlet.http.HttpServlet;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v0/disks")
public class DiskRestController extends HttpServlet {

    private DiskService diskService;

    @Autowired
    public DiskRestController(DiskService diskService){
        this.diskService = diskService;
    }

    /**
     * Метод возвращает список всех дисков
     */
    @GetMapping()
    public ResponseEntity<?> getAllDisk() {
        List<Disk> listDisk = diskService.findAllDisk();
        return diskService.getResponse(listDisk);
    }

    /**
     * Метод возвращает диск по ID
     */
    @GetMapping("{id}")
    public ResponseEntity<?> getDiskId(@PathVariable(name = "id") Long id) {
        Disk disk = diskService.findById(id);
        DiskResponseDto diskDto = new DiskResponseDto(disk.getId(),
                disk.getCreated(),disk.getTitle(),
                disk.getUser().getUsername(),
                disk.getUserTaken().getUsername());
        return disk == null ? new ResponseEntity<>("Not found!",HttpStatus.NOT_FOUND)
                :new ResponseEntity<DiskResponseDto>(diskDto, HttpStatus.OK);
    }

    //TODO Список свободных дисков ОКНО3
    /**
     * Метод возвращает список свободных дисков
     */
    @GetMapping("free")
    public ResponseEntity<?> getFreeDisks() {
        List<Disk> freeDisks = diskService.findFreeDisks();
        return diskService.getResponse(freeDisks);
    }

    /**
     * Метод добавления диска/дисков
     * возвращает список всех дисков
     */
    @PostMapping()
    public ResponseEntity<?> addDisk(@Valid @RequestBody DiskDto diskDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        diskDto.setId(0L);
        boolean isSave = diskService.save(diskDto);
        if (!isSave) return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);;
        List<Disk> disks = diskService.findAllDisk();
        return diskService.getResponse(disks);
    }

    /**
     * Метод удаления диска пользователем
     * возвращает список всех дисков
     */
    @DeleteMapping()
    public ResponseEntity<?> deleteDiskByUserDisk(@Valid @RequestBody DiskDto diskDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        Long diskId = diskDto.getId();
        Disk disk = diskService.findById(diskId);
        if (diskId==null|| disk==null || !disk.getUserId().equals(diskDto.getUserId())) {
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
        diskService.deleteUserDisk(disk.getId(),disk.getUser());
        if (diskService.existsById(diskId)) return new ResponseEntity<>("Bad request", HttpStatus.NOT_FOUND);
        return diskService.getResponse(diskService.findAllDisk());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiskRestController that = (DiskRestController) o;
        return Objects.equals(diskService, that.diskService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diskService);
    }

    @ExceptionHandler
    public ResponseEntity<DataErrorResponse>
    handleException(DataNotFoundException exc) {
        DataErrorResponse errResp = new DataErrorResponse();
        errResp.setStatus(HttpStatus.NOT_FOUND.value());
        errResp.setMessage(exc.getMessage());
        errResp.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(errResp, HttpStatus.NOT_FOUND);
    }
}

