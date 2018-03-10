

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/LoginFilter")
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		System.out.println("LoginFilter: " + httpRequest.getRequestURI());
		
		if (this.isUrlAllowedWithoutLogin(httpRequest.getRequestURI())) {
			// keep default action: pass along the filter chain
			chain.doFilter(request, response);
			return;
		}

		if (httpRequest.getSession().getAttribute("user") == null) {
			httpResponse.sendRedirect("/project2/LoginRevised.jsp");
			return;
		} else {
			chain.doFilter(request, response);
			return;
		}
		
	}
	
	private boolean isUrlAllowedWithoutLogin(String requestURI) {
//		return true;
		if (requestURI.endsWith("LoginRevised.jsp") || requestURI.endsWith("Login.js") 
				|| requestURI.endsWith("Login") || requestURI.endsWith("LoginRevised.css")
				|| requestURI.endsWith("movie.jpg") || requestURI.endsWith("_dashboard.jsp")
				|| requestURI.endsWith("_dashboard.js") || requestURI.endsWith("EmployeeLogin")
				|| requestURI.endsWith("performance.jsp") || requestURI.endsWith("MainSuggestion")
				|| requestURI.endsWith("AndroidSearch") || requestURI.endsWith("AndroidCount"))
		{
			return true;
		}
		return false;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
