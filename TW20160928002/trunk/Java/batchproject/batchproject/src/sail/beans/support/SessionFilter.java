package sail.beans.support;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

public class SessionFilter implements Filter {

	private FilterConfig filterConfig;
	private String[] notValidate;
	private String loginPage;

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
			
			if (GeneralTools.isFilterUrl(referer)||isNotValidate(url, referer)||url.contains("dynamic")
					||url.contains("business")) {
				chain.doFilter(req, res);
			} else {
				String isLogin = (String) session.getAttribute(ISLOGIN);
				response.setContentType(CHARSET);
				if ("true".equals(isLogin)) {
					request.getHeader(REFERER);
					// 验证成功，继续处理
					chain.doFilter(req, res);
				} else {
					// 验证不成功,让用户重新登陆
					StringBuffer sb = new StringBuffer();
					sb.append("<script language='javascript'>");
					sb.append("window.top.location.href='");
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

	private boolean isNotValidate(String url, String referer) {
		boolean blFlag = false;
		if (notValidate == null || notValidate.length == 0) {
			return false;
		}
		for (int i = 0; i < notValidate.length; i++) {
			if (url.indexOf(notValidate[i]) != -1
					|| (referer != null && referer.indexOf(notValidate[i]) != -1)) {
				blFlag = true;
				break;
			}
		}
		return blFlag;
	}
}
