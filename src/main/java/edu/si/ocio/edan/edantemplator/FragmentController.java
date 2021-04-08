package edu.si.ocio.edan.edantemplator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FragmentController {

    @RequestMapping("/fragment/{template}/{name}")
    public String fragments(@PathVariable("template") String template, @PathVariable("name") String name) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader("/templates", ".hbs");
        Handlebars handlebars = new Handlebars().with(loader);

        Template test = handlebars.compile(template + ".html");

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();

        Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/static/json/fragments/" + template + "/" + name + ".json"));

        Map<String, Object> map = gson.fromJson(reader, type);

        Context context = Context.newBuilder(map).build();

        return test.apply(context);
    }

}
