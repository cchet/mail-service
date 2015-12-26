package at.herzog.mailservice.web.application.rest.datasource;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;

import at.herzog.mailservice.api.context.ContentDataSourceContext;
import at.herzog.mailservice.api.context.MailBuilderContext;
import at.herzog.mailservice.api.datasource.ContentDataSource;
import freemarker.cache.TemplateLookupContext;
import freemarker.cache.TemplateLookupResult;
import freemarker.cache.TemplateLookupStrategy;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

@Dependent
public class FreemarkerContentDataSource implements ContentDataSource {

	private Locale defaultLocale = Locale.US;
	private Template template;
	private Configuration configuration;
	private MailBuilderContext context;
	private String key;

	@PostConstruct
	public void init() {
		configuration = new Configuration(Configuration.VERSION_2_3_23);
		configuration.setAutoFlush(Boolean.TRUE);
		configuration.setLocalizedLookup(Boolean.TRUE);
		try {
			configuration.setDirectoryForTemplateLoading(new File("C:/"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		configuration.setTemplateLookupStrategy(new TemplateLookupStrategy() {

			@Override
			public TemplateLookupResult lookup(TemplateLookupContext ctx) throws IOException {
				final TemplateLookupResult result = ctx
						.lookupWithLocalizedThenAcquisitionStrategy(ctx.getTemplateName(), ctx.getTemplateLocale());
				if (!result.isPositive()) {
					return ctx.lookupWithLocalizedThenAcquisitionStrategy(ctx.getTemplateName(), defaultLocale);
				}
				return result;
			}
		});
		configuration.setTemplateExceptionHandler(new TemplateExceptionHandler() {

			@Override
			public void handleTemplateException(TemplateException te, Environment env, Writer out)
					throws TemplateException {
				throw new IllegalStateException("Error in freemarker template processing", te);
			}
		});
	}

	@Override
	public void init(String key, MailBuilderContext context) {
		this.context = context;
		this.key = key;
		try {
			this.template = configuration.getTemplate(key, context.getLocale());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getContent() {
		final OutputStreamWriter writer = new OutputStreamWriter(System.out);
		try {
			template.process(context.getContentCtx().getContentArgs(), writer);
			writer.flush();
			return writer.toString();
		} catch (TemplateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return "error on template proceessing";
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

//	public static void main(String args[]) {
//		final Map<String, String> map = new HashMap<String, String>();
//		map.put("header", "I am the header");
//		map.put("content", "I am the content");
//		map.put("footer", "I am the footer");
//		FreemarkerContentDataSource bean = new FreemarkerContentDataSource();
//		bean.init();
//		bean.init("mail.ftl", new MailBuilderContext(Locale.FRENCH, "test", null, new ContentDataSourceContext(map)));
//		System.out.println(bean.getContent());
//	}
}
