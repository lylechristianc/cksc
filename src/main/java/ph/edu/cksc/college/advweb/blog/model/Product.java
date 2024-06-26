package ph.edu.cksc.college.advweb.blog.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", unique = true)
    @NotBlank(message = "Name is required")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @NotBlank(message = "Description is required")
    private String description;

    @Column(name = "price")
    @Min(value = 1, message = "Minimum price is 1.00")
    @NotNull(message = "Price is required")
    private BigDecimal price;

    @Column(name = "picture")
    private String picture;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
