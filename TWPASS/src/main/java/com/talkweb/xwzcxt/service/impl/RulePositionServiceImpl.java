package com.talkweb.xwzcxt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.RulePositionDal;
import com.talkweb.xwzcxt.pojo.TRulePosition;
import com.talkweb.xwzcxt.service.DpPositionServiceI;
import com.talkweb.xwzcxt.service.RulePositionServiceI;
import com.talkweb.xwzcxt.vo.RulePositionVo;

public class RulePositionServiceImpl implements RulePositionServiceI {

	@Autowired
	private RulePositionDal rulePositionDal;

	@Autowired
	private DpPositionServiceI dpPositionService;

	@Override
	public RulePositionVo getRulePositionByID(String id) {
		return this.ChangeModel(rulePositionDal.getRulePositionByID(id));
	}

	private RulePositionVo ChangeModel(TRulePosition trp) {
		RulePositionVo p = new RulePositionVo();
		if (trp != null) {
			BeanUtils.copyProperties(trp, p);
			// 执行岗位
			if (p.getcPidExec() != null && !p.getcPidExec().isEmpty()) {
				p.setcPNameExec(dpPositionService.getPositionNameByID(p
						.getcPidExec()));
			}
			// 验证岗位
			if (p.getcPidCheck() != null && !p.getcPidCheck().isEmpty()) {
				p.setcPNameCheck(dpPositionService.getPositionNameByID(p
						.getcPidCheck()));
			}
			// 评价岗位
			if (p.getcPidReview() != null && !p.getcPidReview().isEmpty()) {
				p.setcPNameReview(dpPositionService.getPositionNameByID(p
						.getcPidReview()));
			}
			// 反馈1
			if (p.getcPidFeedback1() != null && !p.getcPidFeedback1().isEmpty()) {
				p.setcPNameFeedback1(dpPositionService.getPositionNameByID(p
						.getcPidFeedback1()));
			}
			// 反馈2
			if (p.getcPidFeedback2() != null && !p.getcPidFeedback2().isEmpty()) {
				p.setcPNameFeedback2(dpPositionService.getPositionNameByID(p
						.getcPidFeedback2()));
			}
			// 异常反馈1
			if (p.getcPidErrFeedback1() != null
					&& !p.getcPidErrFeedback1().isEmpty()) {
				p.setcPNameErrFeedback1(dpPositionService.getPositionNameByID(p
						.getcPidErrFeedback1()));
			}
			// 异常反馈2
			if (p.getcPidErrFeedback2() != null
					&& !p.getcPidErrFeedback2().isEmpty()) {
				p.setcPNameErrFeedback2(dpPositionService.getPositionNameByID(p
						.getcPidErrFeedback2()));
			}
		}
		return p;
	}

	private List<RulePositionVo> ChangeModelList(List<TRulePosition> tlist) {
		List<RulePositionVo> vlist = new ArrayList<RulePositionVo>();
		if (tlist != null && tlist.size() > 0) {
			for (TRulePosition t : tlist) {
				RulePositionVo v = ChangeModel(t);
				vlist.add(v);
			}
		}
		return vlist;
	}

	@Override
	public Pagination getAllRulePositionsByConditions(Map params,
			Pagination page) {
		Pagination pagination = rulePositionDal.getAllRulePositions(params, page); 
		pagination.setList(ChangeModelList(pagination.getList()));
		return pagination;
	}

	@Override
	public int addRulePosition(Map params) {
		return rulePositionDal.addRulePosition(params);
	}
}
