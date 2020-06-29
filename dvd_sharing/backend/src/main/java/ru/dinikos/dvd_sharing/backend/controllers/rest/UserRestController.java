/**
 * @author Ostrovskiy Dmitriy
 * @created 09.06.2020
 * DataController
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.controllers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.dinikos.dvd_sharing.backend.controllers.dto.DiskIdDto;
import ru.dinikos.dvd_sharing.backend.entities.Disk;
import ru.dinikos.dvd_sharing.backend.entities.User;
import ru.dinikos.dvd_sharing.backend.exception.DataErrorResponse;
import ru.dinikos.dvd_sharing.backend.exception.DataNotFoundException;
import ru.dinikos.dvd_sharing.backend.exception.ValidationErrorBuilder;
import ru.dinikos.dvd_sharing.backend.services.AuthService;
import ru.dinikos.dvd_sharing.backend.services.DiskService;
import ru.dinikos.dvd_sharing.backend.services.TakenService;
import ru.dinikos.dvd_sharing.backend.services.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ru.dinikos.dvd_sharing.backend.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/v0")
@CrossOrigin(origins = "*")
public class UserRestController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    private UserService userService;
    private DiskService diskService;
    private TakenService takenService;
    private AuthService authService;
    private StringBuilder token = new StringBuilder();

    Map<Long, String> authResponseMap;

    @Autowired
    public UserRestController(UserService userService, DiskService diskService,
                              TakenService takenService, AuthService authService) {
        this.userService = userService;
        this.diskService = diskService;
        this.takenService = takenService;
        this.authService = authService;
    }

    /**
     * Метод стартовый
     */
    @GetMapping("start")
    public ResponseEntity<?> getStartApi() {
        List<Disk> listUser = diskService.findAllDisk();
        return listUser == null && listUser.isEmpty()? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                :new ResponseEntity<List<Disk>>(listUser, HttpStatus.OK);
    }

    /**
     * Метод  возвращает список пользователей
     */
    @GetMapping("users")
    public ResponseEntity<?> getIdUser(HttpServletRequest request,
                                       @RequestHeader (value="Authorization") String header) {
        Long adminId = 1L;
        if (!isAutorized(adminId, header)) return new ResponseEntity<>("Bad authorized!", HttpStatus.UNAUTHORIZED);
        if (!request.isUserInRole("ADMIN")) return  new ResponseEntity<>("No access!!",HttpStatus.UNAUTHORIZED);
        List<User> listUser = userService.findAll();
        return listUser == null && listUser.isEmpty()? new ResponseEntity<>("Not found!",HttpStatus.NOT_FOUND)
                :new ResponseEntity<List<User>>(listUser, HttpStatus.OK);
    }

    /**
     * Метод возвращает пользователя
     */
    @GetMapping("users/{id}")
    public ResponseEntity<?> getIdUser(@PathVariable Long id,
                                       @RequestHeader (value="Authorization", required = false) String header) {
        if (!isAutorized(id, header)) return new ResponseEntity<>("Bad authorized!", HttpStatus.UNAUTHORIZED);
        User user = userService.findById(id);
        Boolean isUser = userService.isUsernameExist(user.getUsername());
        return user == null ? new ResponseEntity<>("Not found!", HttpStatus.NOT_FOUND)
                :new ResponseEntity<User>(user, HttpStatus.OK);
    }
    //TODO Список собственных дисков пользователя  ОКНО2
    /**
     * Метод возвращает диски пользователя userDisks
     */
    @GetMapping("users/{id}/disks")
    public ResponseEntity<?> getUserDisks(@PathVariable Long id,
                                          @RequestHeader (value="Authorization", required = false) String header) {
        if (!isAutorized(id, header)) return new ResponseEntity<>("Bad authorized!", HttpStatus.UNAUTHORIZED);
        List<Disk> listDisk = diskService.findDisksByUser(userService.findById(id));
        return diskService.getResponse(listDisk);
    }
    //TODO Список взятых дисков пользователем ОКНО4
    /**
     * Метод возвращает диски взятые пользователем takenDisk
     */

    @GetMapping("/users/{id}/disks_taken")
    public ResponseEntity<?> getTakenUserDisks(@PathVariable Long id,
                                               @RequestHeader (value="Authorization", required = false) String header) {
        if (!isAutorized(id, header)) return new ResponseEntity<>("Bad authorized!", HttpStatus.UNAUTHORIZED);
        List<Disk> listDisk = diskService.findTakenDisksByUser(userService.findById(id));
        return diskService.getResponse(listDisk);
    }

    //TODO Метод забирает диск у владельца(если диск не взят)
    /**
     * Метод забирает диск у владельца(если диск не взят)
     * возвращает список взятых
     */
    @PostMapping("/users/{id}/disks_taken")
    public ResponseEntity<?> putTakenUserDisks(@PathVariable Long id,
                                               @RequestBody @Valid DiskIdDto diskId, Errors errors,
                                               @RequestHeader (value="Authorization", required = false) String header) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        if (!isAutorized(id, header)) return new ResponseEntity<>("Bad authorized!", HttpStatus.UNAUTHORIZED);
        Disk disk = diskService.findById(diskId.getId());
        if (diskId == null || !diskService.findFreeDisks().contains(disk)) {
            new ResponseEntity<>("Bad request!", HttpStatus.BAD_REQUEST);
        } else {
            takenService.saveTakenItem(id, diskId.getId());
        }
        List<Disk> listDisk = diskService.findTakenDisksByUser(userService.findById(id));
        return diskService.getResponse(listDisk);
    }
    //TODO Список отданных дисков(взятых у пользователя) ОКНО5
    /**
     * Метод возвращает список дисков отданных (взятых) у пользователя givenDisk
     */

    @GetMapping("/users/{id}/disks_given")
    public ResponseEntity<?> getGivenUserDisks(@PathVariable Long id,
                                               @RequestHeader (value="Authorization", required = false) String header) {
        if (!isAutorized(id, header)) return new ResponseEntity<>("Bad authorized!", HttpStatus.UNAUTHORIZED);
        List<Disk> listDisk = diskService.findGivenDisksByUser(userService.findById(id));
        return diskService.getResponse(listDisk);
    }

    //TODO Метод отдаёт диск владельцу (возвращает)
    /**
     * Метод отдаёт диск владельцу (возвращает)
     * возвращает список взятых
     */
    @PostMapping("/users/{id}/disks_given")
    public ResponseEntity<?> putGivenUserDisks(@PathVariable Long id,
                                               @RequestBody @Valid DiskIdDto diskId, Errors errors,
                                               @RequestHeader (value="Authorization", required = false) String header) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        if (!isAutorized(id, header)) return new ResponseEntity<>("Bad authorized!", HttpStatus.UNAUTHORIZED);
        if (diskId == null|| id == null || takenService.findTakenItemByDiskId(diskId.getId())==null) {
            new ResponseEntity<>("Bad request!",  HttpStatus.BAD_REQUEST);
        } else {
            takenService.deliteTakenItem(diskId.getId());
        }
        List<Disk> listDisk = diskService.findTakenDisksByUser(userService.findById(id));
        return diskService.getResponse(listDisk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userService);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRestController that = (UserRestController) o;
        return userService.equals(that.userService);
    }

    private Long getIdUser(Principal principal) {
        return userService.findIdByUserName(principal.getName());
    }

    private boolean isAutorized(Long id, String header){
        token.setLength(0);
        authResponseMap = authService.getAuthResponseMap();
        if (authResponseMap.containsKey(id)) {
            if (header.startsWith(TOKEN_PREFIX)){
                String parts[] = header.split(" ");
                token.append(parts[1].trim());
            }
            logger.info("TokenHeader Response: id= " + id + "token= " + token);
            if (authResponseMap.get(id).equals(token.toString())) {
                logger.info("isAuth = TRUE");
                return true;
            }
        }
        return false;
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

