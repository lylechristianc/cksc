package ph.edu.cksc.college.advweb.blog.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @JsonView(View.Summary.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonView(View.Summary.class)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonView(View.Summary.class)
    @Column(name = "title", unique = true)
    private String title;

    @JsonView(View.Summary.class)
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @JsonView(View.Summary.class)
    @Column(name = "published")
    private boolean published;

    @JsonView(View.Summary.class)
    @CreationTimestamp
    private Date createdAt;

    @JsonView(View.Summary.class)
    @UpdateTimestamp
    private Date updatedAt;

}