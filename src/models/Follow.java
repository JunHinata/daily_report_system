package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "follows")
@NamedQueries ({
    @NamedQuery(
            name = "getMyAllFollows",
            query = "SELECT f FROM Follow AS f WHERE f.follow = :id ORDER BY f.id DESC"
            ),
    @NamedQuery(
            name = "getMyAllFollowers",
            query = "SELECT f FROM Follow AS f WHERE f.followed = :id ORDER BY f.id DESC"
            ),
    @NamedQuery(
            name = "checkDuplication",
            query = "SELECT f FROM Follow AS f WHERE f.follow = :login AND f.followed = :id"
            )
})

@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "follow_id", nullable = false)
    private Employee follow;

    @ManyToOne(optional = false)
    @JoinColumn(name = "followed_id", nullable = false)
    private Employee followed;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getFollow() {
        return follow;
    }
    public void setFollow(Employee follow) {
        this.follow = follow;
    }

    public Employee getFollowed() {
        return followed;
    }
    public void setFollowed(Employee followed) {
        this.followed = followed;
    }

}
