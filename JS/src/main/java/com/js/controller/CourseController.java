package com.js.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/course")
public class CourseController {

	@RequestMapping(value="/getImg")
	public ModelAndView getcourseImg(){
		return new ModelAndView("redirect:/Pages/course/course.html");
	}
}
