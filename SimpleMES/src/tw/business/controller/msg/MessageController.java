package tw.business.controller.msg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.business.entity.msg.Message;
import tw.business.service.msg.MessageService;
import tw.sysbase.entity.Pagination;

@RestController
@RequestMapping("message")
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	/**
	 * 分页查询消息
	 * @param msg
	 * @return
	 */
	@RequestMapping("list")
	public Object list(Message msg) {
		
		Pagination page = messageService.listPage(msg);
		return page;
	}
	/**
	 * 获得未读信息
	 * @return
	 */
	public Object unRead() {
		
		return null;
	}
	
	/**
	 * 根据id获取详情,获取详情后，将结果变为已读
	 * @param pid
	 * @return
	 */
	@RequestMapping("get")
	public Object get(String pid) {
		
	
	
		return null;
	}
	@RequestMapping("updateStatus")
	public Object updateStatus(String pid) {
		Message msg=messageService.updateStatus(pid);
		return msg;
	}
}
