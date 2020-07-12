/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.cart;

import hieubd.entity.Account;
import hieubd.entity.Cart;
import hieubd.entity.CartDetail;
import hieubd.entity.Product;
import hieubd.stax.XMLUtils;
import hieubd.ws.client.AccountClient;
import hieubd.ws.client.CartClient;
import hieubd.ws.client.CartDetailClient;
import hieubd.ws.client.ProductClient;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Admin
 */
public class CartProcessor implements Serializable{
    private String customerId;
    private Map<String, Product> items;

    public CartProcessor() {
    }

    public CartProcessor(String customerId) {
        this.customerId = customerId;
    }
   

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Map<String, Product> getItems() {
        if (items == null){
            items = new HashMap<>();
        }
        return items;
    }

    public void setItems(Map<String, Product> items) {
        this.items = items;
    }

    private void updateCartTotal(Cart cart){
        CartDetailClient cartDetailClient = new CartDetailClient();
        List<CartDetail> cartDetails = cartDetailClient.findByCartID(new GenericType<List<CartDetail>>(){}, cart.getCartID().toString());
        Double total = 0.0;
        for (CartDetail cartDetail : cartDetails) {
            Double subTotal = cartDetail.getTotal();
            total += subTotal;
        }
        cart.setTotal(total);
        CartClient cartClient = new CartClient();
        cartClient.edit_XML(cart, cart.getCartID().toString());
    }
    
    
    public void addItemToCart(String productId, int quantity) throws Exception{
        if (items == null){
            items = new HashMap<>();
        }
        ProductClient client = new ProductClient();
        Product product = client.find_XML(Product.class, productId);
        CartClient cartClient = new CartClient();
        Cart cart = null;
        try {
            cart = cartClient.findByCustID(Cart.class, customerId);
        } catch (Exception ex) {
            cart = null;
        }      
        if (cart == null){            
            cart = new Cart();
            cart.setCartID(1);
            cart.setCustID(new AccountClient().find_XML(Account.class, customerId));
            cart.setTotal(0.0);
            System.out.println(cart.getCartID() + " " + cart.getCustID() + " " + cart.getTotal());
            cartClient.create_XML(cart);
        }
        cart = cartClient.findByCustID(Cart.class, customerId);     
        CartDetailClient cartDetailClient = new CartDetailClient();
        CartDetail cartDetail = new CartDetail();
        cartDetail.setQuantity(quantity);
        product.setQuantity(quantity);
        if (items.containsKey(productId)){
            cartDetail = cartDetailClient.findByCartIDAndProductID(CartDetail.class, cart.getCartID().toString(), productId);
            cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
            product.setQuantity(cartDetail.getQuantity() + quantity);
            //System.out.println("map exist");
            //System.out.println(cartDetail.getCartID() + " id");
        }
        
        double total = cartDetail.getQuantity() * XMLUtils.getThePrice(product.getPrice());
        cartDetail.setTotal(total);
        cartDetail.setUnitPrice(XMLUtils.getThePrice(product.getPrice()));
        cartDetail.setProductID(product);
        cartDetail.setCartID(cart);
        
        //cartDetail.setDetailID(1);
        //System.out.println(cart.getCartID() + "-" + productId);
        boolean isExisted;
        try {
            CartDetail tmp = cartDetailClient.findByCartIDAndProductID(CartDetail.class, cart.getCartID().toString(), productId);
            isExisted = true;
        } catch (Exception e) {
            isExisted = false;
        }
        if (isExisted){
            //exist in db
            //System.out.println("exist detail");
            cartDetailClient.edit_XML(cartDetail, cartDetail.getDetailID()+"");
            updateCartTotal(cart);
        }else{
            //System.out.println("not exist detail");
            // not exist
            cartDetail.setDetailID(1);
            cartDetailClient.create_XML(cartDetail);
            updateCartTotal(cart);
        }
        
        items.put(productId, product);
    }
    
    public void deleteItemFromCart(String productID) throws Exception{
        if (items == null){
            return;
        }
        if (items.containsKey(productID)){
            items.remove(productID);
            if (items.isEmpty()){
                items = null;
            }
            
            CartDetailClient cartDetailClient = new CartDetailClient();
            CartDetail cartDetail = cartDetailClient.findByProdID(CartDetail.class, productID);
            cartDetailClient.remove(cartDetail.getDetailID().toString());
            Cart cart = new CartClient().findByCustID(Cart.class, customerId);
            updateCartTotal(cart);
        }
    }
    
    public void updateItemFromCart(String productID, int quantity) throws Exception{
        if (items == null){
            return;
        }
        ProductClient productClient = new ProductClient();
        Product product = productClient.find_XML(Product.class, productID);
        CartClient cartClient = new CartClient();
        Cart cart = cartClient.findByCustID(Cart.class, customerId);
        CartDetailClient cartDetailClient = new CartDetailClient();
        CartDetail detail = cartDetailClient.findByCartIDAndProductID(CartDetail.class, cart.getCartID().toString(), productID);

        detail.setQuantity(quantity);
        product.setQuantity(quantity);
        double total = detail.getQuantity() * XMLUtils.getThePrice(product.getPrice());
        detail.setTotal(total);
        cartDetailClient.edit_XML(detail, detail.getDetailID().toString());
        updateCartTotal(cart);
        
        items.put(productID, product);
    }
}
