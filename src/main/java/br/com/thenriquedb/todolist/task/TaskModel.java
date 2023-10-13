package br.com.thenriquedb.todolist.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true, length = 50)
    private String title;

    private String description;

    private Number priority;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private UUID userId;

    public void setTitle(String title) throws Exception {
        if(title.length() > 50) {
            throw new Exception("The title field must be less than 50 characters");
        }

        this.title = title;
    }
}
