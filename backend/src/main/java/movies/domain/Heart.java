package movies.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Heart {

    @Id
    @GeneratedValue
    private Long id;

    private boolean isHeart;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Account account;

}
