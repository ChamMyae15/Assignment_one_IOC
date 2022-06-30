package com.jdc.assignment.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;

import com.jdc.assignment.domain.Course;
import com.jdc.assignment.listener.SpringContainerManager;
import com.jdc.assignment.model.CourseModel;

@WebServlet(urlPatterns = {"/","/courses","/course-edit"})
public class CourseServlet extends AbstarctBeanFactoryServlet{

	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var page = switch (req.getServletPath()) {
		case "/course-edit"-> "/course-edit.jsp";
		default->{
			
			//load course and set result to request scope
			var model = getBean("courseModel", CourseModel.class);
			req.setAttribute("course", model.getAll());
			yield "/index.jsp";		}
	};
	
		getServletContext().getRequestDispatcher(page).forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//get request param
		var name = req.getParameter("name");
		var duaration = req.getParameter("duration");
		var fees = req.getParameter("fees");
		var description = req.getParameter("description");
		
		//create saved object
		var course = new Course();
		course.setName(name);
		course.setDescription(description);
		course.setFees(Integer.parseInt(fees));
		course.setDuration(Integer.parseInt(duaration));
		
		//save to db
		getBean("courseModel", CourseModel.class).save(course);
		
		//redirect to top page
		resp.sendRedirect("/");
	
		
		
		
	}

}
