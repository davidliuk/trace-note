package cn.neud.trace.note.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExpiredData {
    private LocalDateTime expireTime;
    private Object data;
}
