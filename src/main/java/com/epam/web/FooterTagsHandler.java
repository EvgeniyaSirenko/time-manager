package com.epam.web;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

public class FooterTagsHandler extends TagSupport {

	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspTagException {
		try {
			pageContext.getOut().write("<div id=\"footer\">");
			pageContext.getOut().write("<br><br> &copy;Time manager, 2021");
		} catch (IOException e) {
			throw new JspTagException(e.getMessage());
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().write("</div>");
		} catch (IOException e) {
			throw new JspTagException(e.getMessage());
		}
		return EVAL_PAGE;
	}

}
