/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.entity;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CartDetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CartDetail.findAll", query = "SELECT c FROM CartDetail c")
    , @NamedQuery(name = "CartDetail.findByDetailID", query = "SELECT c FROM CartDetail c WHERE c.detailID = :detailID")
    , @NamedQuery(name = "CartDetail.findByQuantity", query = "SELECT c FROM CartDetail c WHERE c.quantity = :quantity")
    , @NamedQuery(name = "CartDetail.findByUnitPrice", query = "SELECT c FROM CartDetail c WHERE c.unitPrice = :unitPrice")
    , @NamedQuery(name = "CartDetail.findByTotal", query = "SELECT c FROM CartDetail c WHERE c.total = :total")})
public class CartDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "detailID")
    private Integer detailID;
    @Column(name = "quantity")
    private Integer quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "unitPrice")
    private Double unitPrice;
    @Column(name = "total")
    private Double total;
    @JoinColumn(name = "cartID", referencedColumnName = "cartID")
    @ManyToOne
    private Cart cartID;
    @JoinColumn(name = "productID", referencedColumnName = "id")
    @ManyToOne
    private Product productID;

    public CartDetail() {
    }

    public CartDetail(Integer detailID) {
        this.detailID = detailID;
    }

    public Integer getDetailID() {
        return detailID;
    }

    public void setDetailID(Integer detailID) {
        this.detailID = detailID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Cart getCartID() {
        return cartID;
    }

    public void setCartID(Cart cartID) {
        this.cartID = cartID;
    }

    public Product getProductID() {
        return productID;
    }

    public void setProductID(Product productID) {
        this.productID = productID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detailID != null ? detailID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CartDetail)) {
            return false;
        }
        CartDetail other = (CartDetail) object;
        if ((this.detailID == null && other.detailID != null) || (this.detailID != null && !this.detailID.equals(other.detailID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hieubd.ws.CartDetail[ detailID=" + detailID + " ]";
    }
    
}
