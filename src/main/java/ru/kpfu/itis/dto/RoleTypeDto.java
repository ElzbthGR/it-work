package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.models.Task;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleTypeDto {
    private Task.RoleType roleType;
    private String name;
}
