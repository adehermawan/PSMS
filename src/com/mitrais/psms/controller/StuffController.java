package com.mitrais.psms.controller;

import java.awt.print.Book;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mitrais.psms.dao.StuffDAO;
import com.mitrais.psms.model.Stuff;

public class StuffController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private StuffDAO stuffDao;
	
	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = "";

		stuffDao = new StuffDAO(jdbcURL, jdbcUsername, jdbcPassword);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String action = req.getServletPath();
		
		try {
			switch (action) {
			case "/new":
				showNewForm(req, resp);
				break;
			case "/insert":
				insertStuff(req, resp);
			break;
			case "/delete":
				deleteStuff(req,resp);
			break;
			case "/edit":
				showEditForm(req,resp);
			break;
			case "/update":
				updateStuff(req,resp);
			break;
			default:
				listStuff(req,resp);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void updateStuff(HttpServletRequest req, HttpServletResponse resp) 
	throws SQLException,IOException, ServletException{
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		String location = req.getParameter("location");
		
		Stuff stuff = new Stuff(id, name, description, quantity, location);
		stuffDao.updateStuff(stuff);
		resp.sendRedirect("list");
	}

	private void showEditForm(HttpServletRequest req, HttpServletResponse resp) 
	throws SQLException, IOException, ServletException{
		int id = Integer.parseInt(req.getParameter("id"));
		Stuff existingStuff = stuffDao.getStuff(id);
		RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/StuffForm.jsp");
		req.setAttribute("stuff", existingStuff);
		dispatcher.forward(req, resp);
	}

	private void deleteStuff(HttpServletRequest req, HttpServletResponse resp) 
	throws SQLException, IOException, ServletException{
		int id = Integer.parseInt(req.getParameter("id"));
		
		Stuff stuff = new Stuff(id);
		stuffDao.deleteStuff(stuff);
		resp.sendRedirect("list");
	}

	private void insertStuff(HttpServletRequest req, HttpServletResponse resp) 
	throws SQLException, IOException, ServletException{
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		String location = req.getParameter("location");
		
		Stuff newStuff = new Stuff(name, description, quantity, location);
		stuffDao.insertStuff(newStuff);
		resp.sendRedirect("list");		
	}

	private void showNewForm(HttpServletRequest req, HttpServletResponse resp) 
	throws SQLException, IOException, ServletException{
		RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/StuffForm.jsp");
		dispatcher.forward(req, resp);
	}

	private void listStuff(HttpServletRequest req, HttpServletResponse resp) 
	throws SQLException, IOException, ServletException{
		List<Stuff> listStuff = stuffDao.listAllStuff();
		req.setAttribute("listStuff", listStuff);
		RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/StuffList.jsp");
		dispatcher.forward(req, resp);
	}
}
