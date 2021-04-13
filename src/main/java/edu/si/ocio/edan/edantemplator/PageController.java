package edu.si.ocio.edan.edantemplator;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PageController
{
    @RequestMapping("/page/{name}")
    public String fragments(@PathVariable("name") String name) throws IOException
    {
        Gson gson = new Gson();
        TemplateLoader loader = new ClassPathTemplateLoader("/templates", ".hbs");
        Handlebars handlebars = new Handlebars().with(loader);

        Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/static/json/pages/" + name + ".json"));
        Map<?, ?> page = gson.fromJson(reader, Map.class);
        List<Map<?, ?>> components = (List<Map<?,?>>) page.get("components");

        String html = "";

        for(int i = 0; i < components.size(); i++)
        {
            String edanUrl = (String) components.get(i).get("edanUrl");
            String componentType = (String) components.get(i).get("componentType");

            Map<?, ?> options = new HashMap<>();

            if(components.get(i).containsKey("options"))
            {
                options = (Map<?, ?>) components.get(i).get("options");
            }

            Template template = handlebars.compile(componentType + ".html");

            reader = Files.newBufferedReader(Paths.get("src/main/resources/static/json/edan/" + componentType + "/" + edanUrl + ".json"));
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> componentMap = gson.fromJson(reader, type);

            html += template.apply(componentMap);
        }

        Map<Object, Object> parameters = new HashMap<>();
        parameters.put("css", page.get("css"));
        parameters.put("js", page.get("js"));
        parameters.put("htmlBefore", page.get("htmlBefore"));
        parameters.put("htmlBefore", page.get("htmlAfter"));
        parameters.put("content", html);

        String pageTemplateFile = "page.html";
        if (page.get("pageTemplate") instanceof String) {
          pageTemplateFile = (String) page.get("pageTemplate");
        }

        Template pageTemplate = handlebars.compile(pageTemplateFile);

        return pageTemplate.apply(parameters);
    }
}
