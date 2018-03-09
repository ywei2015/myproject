package test;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSON;

import spc.beans.entity.spc.TSpcStandard;
import spc.beans.service.rediscache.ParamStandardManagerService;
/**
 * @author YWW
 * @date 2017-11-1
 * */
@RunWith(SpringJUnit4ClassRunner.class) // = extends SpringJUnit4ClassRunner
@ContextConfiguration(locations = { "classpath*:applicationContext.xml"})
@WebAppConfiguration
//@Transactional(transactionManager="transactionManager")
public class TestParamStandardManager extends AbstractJUnit4SpringContextTests{
	private static final Logger logger = LoggerFactory.getLogger(TestParamStandardManager.class);
	
	@Autowired
	private ParamStandardManagerService paramStandardManagerService;
	
	@Test
	public void test1() {
		Map<String,TSpcStandard> map =paramStandardManagerService.getLatestParameterStandard("ID1128", "test20171102aa");
		Iterator iterator=map.keySet().iterator();
		while(iterator.hasNext()){
			TSpcStandard standard=map.get(iterator.next());
			System.out.println(standard);
		}
		logger.debug(JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd HH:mm:ss"));
	}
}
