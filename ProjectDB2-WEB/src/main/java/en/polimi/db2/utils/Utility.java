package en.polimi.db2.utils;

import java.time.LocalDate;

import javax.servlet.ServletContext;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

public class Utility {

	private static Utility instance;
	
	
	public static Utility getInstance() {
		if(instance==null) {
			instance= new Utility();
		}
		return instance;
	}
	
	public TemplateEngine connectTemplate(ServletContext sc) {
		TemplateEngine tempEngine = new TemplateEngine();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(sc);
		templateResolver.setTemplateMode(TemplateMode.HTML);
        tempEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
        return tempEngine;
	}

	public boolean checkString(String s) {
		return s!=null && s!="";
	}
	
	public boolean isMail(String s) {
		return checkString(s) && s.contains("@");
	}
	
	public boolean notBeforToday(LocalDate ld) {
		LocalDate date=LocalDate.now();
		return ld.isAfter(date);
	}
	
	
	
}
