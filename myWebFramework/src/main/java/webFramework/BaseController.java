package webFramework;

import java.io.EOFException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.io.EofException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import baseFramework.jackson.JsonUtils;

/**
 * @author lichao
 * @date 2015年11月21日
 */
public class BaseController {
	Logger logger = LoggerFactory.getLogger(BaseController.class);

	/**
	 * 返回页面
	 * 
	 */
	protected ModelAndView pageView(String viewName, Model model) {
		ModelAndView modelAndView = new ModelAndView(viewName, model.asMap());
		return modelAndView;
	}

	/**
	 * json
	 * 
	 */
	protected ModelAndView jsonView(Model model) {
		MappingJackson2JsonView view = new MappingJackson2JsonView(JsonUtils.getObjectMapper());
		ModelAndView modelAndView = new ModelAndView(view, model.asMap());
		return modelAndView;
	}

	/**
	 * pretty-json
	 * 
	 */
	protected ModelAndView prettyJsonView(Model model) {
		MappingJackson2PrettyJsonView view = new MappingJackson2PrettyJsonView(JsonUtils.getObjectMapper());
		ModelAndView modelAndView = new ModelAndView(view, model.asMap());
		return modelAndView;
	}

	@ExceptionHandler
	public String exp(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		String requestURI = request.getRequestURI();
		try {
			if (ex instanceof MissingServletRequestParameterException) {// param-missing
				logger.error(
						String.format("http params missing. request URL:%s, message:%s", requestURI, ex.getMessage()),
						ex);
			} else if (ex instanceof EofException || ex instanceof EOFException) {
				logger.error("eof exception request URL:" + requestURI + ",message:" + ex.getMessage(), ex);
				return null;
			} else {
			}
			response.setStatus(500);
		} catch (Exception e1) {
			logger.error("", e1);
		}
		return ex.getMessage();
	}
}
