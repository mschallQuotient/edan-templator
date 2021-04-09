package edu.si.ocio.edan.edantemplator;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HomepageController
{
    @RequestMapping("/")
    public String index() throws IOException
    {
        TemplateLoader loader = new ClassPathTemplateLoader("/templates", ".hbs");
        Handlebars handlebars = new Handlebars().with(loader);

        Template index = handlebars.compile("index.html");
        return index.apply("");
    }

    @RequestMapping("/fragment")
    public String fragments() throws IOException
    {
        TemplateLoader loader = new ClassPathTemplateLoader("/templates", ".hbs");
        Handlebars handlebars = new Handlebars().with(loader);

        Template index = handlebars.compile("fragment.html");
        return index.apply("");
    }

}
