package com.talkweb.xwzcxt.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.xwzcxt.service.RuleDataTypeServiceI;
import com.talkweb.xwzcxt.pojo.RuleDataTypePojo;

public class RuleDataTypeAction extends baseAction {
	@Autowired
	private RuleDataTypeServiceI ruleDataTypeService;
	
	public String getRuleDataTypeSelect() {
		List<RuleDataTypePojo> li = ruleDataTypeService.getRuleDataTypeSelect();
		RuleSelect wtree = new RuleSelect();
		wtree.setValue("cTracefunId");
		wtree.setText("cTracefunName");
		try {
			if (li != null && li.size() > 0) {
				List<Map> l = this.initSelectData(li, wtree);
				this.formatData(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}