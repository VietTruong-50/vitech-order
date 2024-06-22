package vn.hust.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hust.api.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
