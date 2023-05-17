package com.kt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private int id;
    @Column(name = "name", columnDefinition = "nvarchar(255)")
    private String name;
    @Column(name = "code")
    private int code;
    @Column(name = "description", columnDefinition = "nvarchar(500)")
    private String description;
    @Column(name = "price")
    private float price;
    @Column(name = "amount")
    private int amount;
    @Column(name = "stock")
    private int stock;
    @Column(name = "image", columnDefinition = "varchar(500)", unique = true)
    private String image;
    @Column(name = "status")
    private int status;
    @Temporal(TemporalType.DATE)
    @Column(name = "createDate")
    private Date createDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    @JsonIgnore
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;
}
