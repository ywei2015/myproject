package sail.beans.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sail.beans.service.SilkWorkOrderService;


@Controller
@RequestMapping("/silkorder")
public class SilkWorkOrderController {

	@Resource
	private SilkWorkOrderService silkWorkOrderService; 
}
