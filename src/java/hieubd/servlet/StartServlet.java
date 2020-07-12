/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.servlet;

import hieubd.cart.CartProcessor;
import hieubd.entity.Cart;
import hieubd.entity.CartDetail;
import hieubd.entity.Product;
import hieubd.ws.client.CartClient;
import hieubd.ws.client.CartDetailClient;
import hieubd.ws.client.ProductClient;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Admin
 */
public class StartServlet extends HttpServlet {
    private final String SUCCESS = "home.jsp";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = SUCCESS;
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession session = request.getSession(true);
            String username = session.getAttribute("USERNAME").toString();
            try {
               
                    CartClient cartClient = new CartClient();
                    Cart cart = cartClient.findByCustID(Cart.class, username);
                    CartDetailClient cartDetailClient = new CartDetailClient();
                    CartProcessor cartProcessor = (CartProcessor) session.getAttribute("CARTLIST");
                    System.out.println(cart.getCartID());
                    ProductClient productClient = new ProductClient();
                    if (cartProcessor == null){
                        System.out.println("new");
                        cartProcessor = new CartProcessor(username);
                    }
                    List<CartDetail> list = cartDetailClient.findByCartID(new GenericType<List<CartDetail>>(){}, cart.getCartID().toString());
                    System.out.println(list);
                    for (CartDetail cartDetail : list) {
                        System.out.println(cartDetail.getDetailID() + "detId");
                        Product product = productClient.find_XML(Product.class, cartDetail.getProductID().getId().toString());
                        product.setQuantity(cartDetail.getQuantity());
                        System.out.println(product.getName() + " " + product.getId());
                        cartProcessor.getItems().put(product.getId().toString(), product);
                    }
                    session.setAttribute("CARTLIST", cartProcessor);
                    //session.setAttribute("CART", cart);
                } catch (Exception ex) {}
        } catch (Exception e) {
            log(e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
