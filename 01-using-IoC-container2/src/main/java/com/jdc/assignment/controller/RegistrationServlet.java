package com.jdc.assignment.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdc.assignment.domain.Registration;
import com.jdc.assignment.impl.OpenClassModelImpl;
import com.jdc.assignment.impl.RegistrationModelImpl;
import com.jdc.assignment.model.OpenClassModel;
import com.jdc.assignment.model.RegistrationModel;

@WebServlet(urlPatterns = {
		"/registration",
		"/registration-edit"
})
public class RegistrationServlet extends AbstarctBeanFactoryServlet {

	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		var classId = req.getParameter("classId");
		if(classId==null) {
			System.out.println("its null");
		}
		
		var openClassModel = getBean("openClassModel", OpenClassModel.class);
		var classes = openClassModel.findById(Integer.parseInt(classId));
		req.setAttribute("classes", classes);
		
		var page = switch (req.getServletPath()) {
		case "/registration"-> {
			
			var model = getBean("registrationModel", RegistrationModel.class);
			req.setAttribute("registration", model.getRegistrationById(Integer.parseInt(classId)));
			
			yield "registration";
		}
		default ->"registration-edit";
		};
		
		getServletContext().getRequestDispatcher("/%s.jsp".formatted(page)).forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		var classId = req.getParameter("classId");
		var student = req.getParameter("student");
		var phone = req.getParameter("phone");
		var email = req.getParameter("email");
		
		var openClassModel = getBean("openClassModel", OpenClassModel.class);
		var openClass = openClassModel.findById(Integer.parseInt(classId));
		
		Registration r = new Registration();
		r.setOpenClass(openClass);
		r.setStudent(student);
		r.setPhone(phone);
		r.setEmail(email);
		
		getBean("registrationModel", RegistrationModel.class).save(r);
		
		resp.sendRedirect("/");
		
		
	}

}
