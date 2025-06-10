package ch.oliumbi.neorem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "executions")
public class Execution {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "job_id")
    private UUID jobId;

    @Column(name = "start")
    private LocalDateTime start;

    @Column(name = "end")
    private LocalDateTime end;

    @Column(name = "task", nullable = false)
    private String task;

    @Column(name = "status", nullable = false)
    private String status;

    // todo rethink
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "execution_id", referencedColumnName = "id")
    private Set<ExecutionMessage> roles = new HashSet<>();
}
