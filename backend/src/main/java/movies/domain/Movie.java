package movies.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id @GeneratedValue
    private Long id;

    private String imgUrl;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Comment> comments = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Heart> hearts = new HashSet<>();

    public void addComment(Comment comment){
        this.comments.add(comment);
        comment.setMovie(this);
    }

    public void deleteComment(Comment comment){
        this.comments.remove(comment);
        comment.setMovie(null);
    }

    public void addHeart(Heart heart){
        this.hearts.add(heart);
        heart.setMovie(this);
    }

    public void deleteHeart(Heart heart){
        this.hearts.remove(heart);
        heart.setMovie(null);
    }

}
