package Simple.University.System.demo.Repository.Core;

import Simple.University.System.demo.Entity.Core.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
    default T findEntityById(Long id) {
        return this.findById(id).orElseGet(null);
    }
}
