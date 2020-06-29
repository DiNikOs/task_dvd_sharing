/**
 * @author Ostrovskiy Dmitriy
 * @created 20.06.2020
 * DiskRepository
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.dinikos.dvd_sharing.backend.entities.Disk;
import ru.dinikos.dvd_sharing.backend.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiskRepository extends JpaRepository<Disk, Long>, JpaSpecificationExecutor<Disk> {

    Optional<Disk> findById(Long id);
    // Запрос на диски пользователя
    List<Disk> findDisksByUser(User user);
    Disk findDiskById(Long diskId);
    // Запрос на диски взятые пользователем
    List<Disk> findDisksByUserTaken(User user);

    // Запрос на диски которые можно взять(свободные)
    @Query(value = "SELECT disks " +
            "FROM Disk disks" +
            " WHERE disks.userTaken IS null")
    List<Disk> findFreeDisksQuery();

    // Запрос на взятые у пользователей(отданные) диски
    @Query(value = "SELECT disks " +
            "FROM Disk disks" +
            " WHERE disks.userTaken IS NOT null and disks.userId =:user_id")
    List<Disk> findGivenDisksQuery(@Param("user_id") Long user_id);

    // Запрос на диски по title и user_id (для проверки на дубликаты)
    @Query(value = "SELECT disks " +
            "FROM Disk disks" +
            " WHERE disks.userId =:user_id and disks.title =:title")
    List<Disk> findDuplicateQuery(@Param("title") String title, @Param("user_id") Long user_id);

    void deleteDiskById(Long id);
}
