package tw.sysbase.pub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
 
 
public class LoginFilter implements Filter {

	private FilterConfig filterConfig;
	private String[] notValidate;
	private String loginPage;
	private List<String> trustHostList = new ArrayList<String>();

	private static final String CHARSET = "text/html; charset=UTF-8";
	private static final String ISLOGIN = "isLogin";
	private static final String CONTENT = "<font size='7' color=red>您没有该功能的访问权限,请与管理员联系!</font>";

	public void doFilter(final ServletRequest req, final ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		try {
			String url = request.getRequestURI();
			String referer = request.getHeader(REFERER);
			if(isNotValidate(url) || url.contains("/dynamic")){
				chain.doFilter(req, res);
			} else {
				String isLogin = (String) session.getAttribute(ISLOGIN);
				response.setContentType(CHARSET);
				if ("true".equals(isLogin)) {
					request.getHeader(REFERER);
					chain.doFilter(req, res);
				} else {
					// 验证不成功,让用户重新登陆
					StringBuffer sb = new StringBuffer();
					sb.append("<script language='javascript'>window.parent.location.href='");
					sb.append(request.getContextPath());
					sb.append(loginPage);
					sb.append("';</script>");
					response.getWriter().println(sb.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isRight(HttpServletRequest request) {
		if (isSystemMenu(request) == false) {
			return true;
		}
		String url = request.getRequestURI();
		WebApplicationContext wac = getWebContext(request);
		SecurityManager securityManager = (SecurityManager) wac.getBean("securityManager");
//		String targetId = securityManager.getDicIdByUrl(url);
		String targetId = "";
		if (StringUtils.isBlank(targetId)) {
			return true;
		}
//		return securityManager.hasPermissionByTargetId(getOperator(request),targetId);
		return true;
	}

	private static final String HTML = ".html";
	private static final String REFERER = "Referer";

	public boolean isSystemMenu(HttpServletRequest request) {
		String url = request.getRequestURI();
		return isSystemMenu(url);
	}
	
	public boolean isSystemMenu(String url) {
		if (StringUtils.isBlank(url)) {
			return false;
		}
		int beginIndex = url.lastIndexOf(HTML);
		return (beginIndex != -1);
	}

	@SuppressWarnings("unused")
	private boolean isDataRequest(HttpServletRequest request) {
		String url = request.getRequestURI();
		String referer = request.getHeader(REFERER);
		if (isSystemMenu(referer) && "/cdMes/DataMap".equals(url)) {
			return true;
		}
		return false;
	}

	private WebApplicationContext getWebContext(HttpServletRequest request) {
		ServletContext ctx = request.getSession().getServletContext();
		return WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
	}

	@SuppressWarnings("unused")
	private String getOperator(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String ret = (String) session.getAttribute("operator");
		return ret;
	}

	public void setFilterConfig(final FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	public void destroy() {
		this.filterConfig = null;
	}

	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
		// 不用验证的URL
		if (filterConfig.getInitParameter("urlnotvalidate") != null) {
			notValidate = filterConfig.getInitParameter("urlnotvalidate")
					.split(",");
		}
		// 登录页面
		if (filterConfig.getInitParameter("login") != null) {
			loginPage = filterConfig.getInitParameter("login");
		} 
		
	}

	private boolean isNotValidate(String url) {
		boolean blFlag = false;
		if (notValidate == null || notValidate.length == 0) {
			return false;
		}

		for (int i = 0; i < notValidate.length; i++) {
			if (url.indexOf(notValidate[i]) != -1) {
				blFlag = true;
				break;
			}
		}
		return blFlag;
	}
}
