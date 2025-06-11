package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, String> {

}
