package ch.oliumbi.neorem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "task", nullable = false)
    private String task;

    @Column(name = "cron", nullable = false)
    private String cron;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    // todo rethink
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    private Set<JobParameter> parameters = new HashSet<>();
}
