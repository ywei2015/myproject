package com.talkweb.xwzcxt.action;

import org.apache.struts2.convention.annotation.Action;


@Action(value="demoaction")
public class DemoAction extends baseAction {

	public void test()
	{
		super.writeJson("1111111111");
	}
}
