package movies.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String comment;
    private Integer starRate;
}
