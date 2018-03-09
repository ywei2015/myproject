package tw.business.service.msg;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import tw.business.entity.msg.Message;
import tw.sysbase.dao.GenericDao;
import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.Pagination;
import tw.sysbase.pub.Constants;
import tw.utils.HttpUtils;

@Service
@Transactional
public class MessageService {
	@Autowired
	private GenericDao genericDao;
	/**
	 * 分页查询 查询状态必查条件
	 * @param msg
	 * @return
	 */
	public Pagination listPage(Message msg) {
		String status=null;
		HttpServletRequest httpServletRequest = HttpUtils.getHttpServletRequest();
		String pid = (String) httpServletRequest.getAttribute(Constants.SESSION_KEY_OPERATOR);
		if(msg.getStatus()==null) {
			status="(10,20)";
		}else {
			status="("+msg.getStatus()+")";
		}
		
		Object [] params= {1,msg.getTitle(),msg.getContent(),pid};
		String hql="from t_message t where t.sysFlag = ? and t.title = ? and t.content = ? and  t.receiver = ? and t.status in "+status+" order by t.lastModifedTime desc";
		PaginationSupport p= genericDao.getPageWithParasByHql(hql, params, msg);
		return Pagination.init(p);
	}
	public Message get(String pid) {
		// TODO Auto-generated method stub
		return (Message) genericDao.getById(Message.class, pid);
	}
	/**
	 * 查看详情，更新状态
	 * @param pid
	 */
	public Message updateStatus(String pid) {
		 Message msg =(Message) genericDao.getById(Message.class, pid);
		 if(msg.getStatus()==10) {
			 msg.setStatus(20);
		 }
		return msg;
		
	}
	
}
