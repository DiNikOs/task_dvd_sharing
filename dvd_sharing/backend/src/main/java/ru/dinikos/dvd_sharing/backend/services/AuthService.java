/**
 * @author Ostrovskiy Dmitriy
 * @created 26.06.2020
 * AuthService
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dinikos.dvd_sharing.backend.jwt.JwtProvider;
import ru.dinikos.dvd_sharing.backend.controllers.dto.AuthRequest;
import ru.dinikos.dvd_sharing.backend.controllers.dto.AuthResponse;
import ru.dinikos.dvd_sharing.backend.controllers.rest.UserRestController;
import ru.dinikos.dvd_sharing.backend.entities.User;
import ru.dinikos.dvd_sharing.backend.controllers.dto.SystemUser;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
    // Map для хранения связки авторизованных пользователей id user и token
    Map<Long, String> authResponseMap = new HashMap<>();


    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    public User saveUser(SystemUser systemUser){
        return userService.save(systemUser);
    }

    /**
     * Метод проверки login - password пользователя
     * Метод возвращает id user и token при успешной проверке
     * @key - userId - id пользователя
     * @value - taken - такен который присваивается пользователю для доступу по Rest
     */
    public AuthResponse getAuthResponse(AuthRequest request){
        User user = userService.findByLoginAndPassword(request.getUsername(), request.getPassword());
        if (user==null) return null;
        Long userId = user.getId();
        String token = jwtProvider.generateToken(user.getUsername());
        saveAuth(userId, token);
        AuthResponse response = new AuthResponse(userId, token);
        return response;
    }

    /**
     * Метод возвращает Map для проверки авторизации пользователяя
     */
    public Map<Long, String> getAuthResponseMap(){
        return authResponseMap;
    }

    /**
     * Метод заполнения Map таблицы авторизованными пользователями
     * @key - userId - id пользователя
     * @value - taken - такен который присваивается пользователю для доступу по Rest
     */
    private void saveAuth(Long userId, String token) {
        authResponseMap.put(userId,token);
        logger.info("SaveAuto: " + authResponseMap.toString());
    }

    /**
     * Метод удаления записи с авторизованным пользователем из Map таблицы
     * Метод возвращает true в случае успеха (разлогин)
     * @key - userId - id пользователя
     * @value - taken - такен который присваивается пользователю для доступу по Rest Api
     */
    public boolean deliteAuth (Long userId, String token) {
        boolean del = authResponseMap.remove(userId, token);
        logger.info("DelAuth: " + authResponseMap.toString());
        return del;
    }
}
