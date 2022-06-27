package movies.domain;

import movies.dto.CommentRequestDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String comment;

    private float starRate;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Account account;

    public Comment(CommentRequestDto requestDto){
        this.comment = requestDto.getComment();
        this.starRate = requestDto.getStarRate();
    }

    public void updateComment(CommentRequestDto requestDto){
        this.comment = requestDto.getComment();
        this.starRate = requestDto.getStarRate();
    }
}
