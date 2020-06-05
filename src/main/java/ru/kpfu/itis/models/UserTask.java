package ru.kpfu.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = UserTask.TABLE_NAME)
public class UserTask {
    public static final String TABLE_NAME = "users_tasks";

    @EmbeddedId
    private Id id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Id.TASK_ID_COLUMN, insertable = false, updatable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Id.USER_ID_COLUMN, insertable = false, updatable = false)
    private User user;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class Id implements Serializable {

        public static final String TASK_ID_COLUMN = "task_id";
        public static final String USER_ID_COLUMN = "user_id";

        @Column(name = TASK_ID_COLUMN)
        private Long taskId;

        @Column(name = USER_ID_COLUMN)
        private Long userId;
    }
}
