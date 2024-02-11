package me.deftkang.springbootdeveloper.api;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class ArticleAPI {

    public long calculateModifiableDate(LocalDateTime createdDatedAt) {
        LocalDate localDate = LocalDate.now();
        long diffDate = ChronoUnit.DAYS.between(createdDatedAt.toLocalDate(), localDate);
        //생성일이 10일이면 하루 남은걸로 한다.
        long modifiableDate = (11 - diffDate) > 0 ? (11 - diffDate) : 0;

        return modifiableDate;
    }
}
