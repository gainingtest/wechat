package com.js.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/personalInfo")
public class PersonalInfoController {

	/**
	 * 由个人设置页跳转到个人资料变更页面
	 * 
	 * @return
	 */
	@RequestMapping("/toInfo")
	public ModelAndView toInfo() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/member_center/identity_verification");
		return mv;
	}
}
