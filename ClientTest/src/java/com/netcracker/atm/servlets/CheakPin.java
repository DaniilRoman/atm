
package com.netcracker.atm.servlets;

import com.netcracker.atm.Atm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class CheakPin extends Dispatcher {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try{
        int pin = Integer.parseInt(request.getParameter("pin"));
        Atm atm = (Atm)request.getSession().getAttribute("atm");
        
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("mySecretPassword");        
        String encryptedPin = encryptor.encrypt(String.valueOf(pin));
        
           if(atm.checkPin(encryptedPin)) super.forward("/CardValid", request, response);
           else{ 
               request.getSession().setAttribute("ex", "Invalid pin code.");
               request.getSession().setAttribute("page", "pin.jsp");
               super.forward("/false.jsp", request, response);
           }
        }catch(NumberFormatException ex){
            request.getSession().setAttribute("ex", "Check the correctness of the PIN code.");
            request.getSession().setAttribute("page", "pin.jsp");
            super.forward("/false.jsp", request, response);
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
