package bbw.tm.backend.role;

import bbw.tm.backend.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Getter
@Setter
public class Role extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;
}