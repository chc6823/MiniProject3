package com.freeflux.controller.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.freeflux.dao.CartDAO;
import com.freeflux.dao.OrderDAO;
import com.freeflux.dto.CartVO;
import com.freeflux.dto.MemberVO;

public class OrderInsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "NonageServlet?command=order_list";
		HttpSession session = request.getSession();
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			url = "NonageServlet?command=login_form";
		} else {
			CartDAO cartDAO = CartDAO.getInstance();
			ArrayList<CartVO> cartList = cartDAO.listCart(loginUser.getId());

			OrderDAO orderDAO = OrderDAO.getInstance();

			int maxOseq = orderDAO.insertOrder(cartList, loginUser.getId());
			url = "NonageServlet?command=order_list&oseq=" + maxOseq;
		}
		response.sendRedirect(url);
	}
}
