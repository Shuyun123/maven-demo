package net.anumbrella;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS;


@Mojo(name = "code", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class CodeMojo extends AbstractMojo {

    private static final Configuration FREE_MARKER_CONFIGURATION = new Configuration(DEFAULT_INCOMPATIBLE_IMPROVEMENTS);


    private String basePkgDir;


    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter
    private Config config;


    public void execute() throws MojoExecutionException, MojoFailureException {
        initParams();
        generatorCode();
    }

    private void initParams() {
        basePkgDir = project.getBuild().getSourceDirectory() + File.separator + (config.getPkg() + ".").replaceAll("\\.", "/");
    }

    private void generatorCode() throws MojoFailureException {
        try {
            String name = "entity";
            InputStream source = getTemplate(name);
            StringWriter writer = new StringWriter();
            File targetFile = new File(basePkgDir + File.separator + config.getEntityPkg().toLowerCase(), "Person.java");
            Template template = new Template(name, new InputStreamReader(source, "utf-8"), FREE_MARKER_CONFIGURATION);

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("entityName", "Person");
            List<Map<String, String>> paramsList = new ArrayList<Map<String, String>>();
            Map<String, String> IdMap = new HashMap<String, String>();
            IdMap.put("fieldType", "Integer");
            IdMap.put("fieldName", "id");
            paramsList.add(IdMap);
            Map<String, String> nameMap = new HashMap<String, String>();
            nameMap.put("fieldType", "String");
            nameMap.put("fieldName", "username");
            paramsList.add(nameMap);
            Map<String, String> passwordMap = new HashMap<String, String>();
            passwordMap.put("fieldType", "String");
            passwordMap.put("fieldName", "password");
            paramsList.add(passwordMap);
            data.put("params", paramsList);
            data.put("config", config);
            template.process(data, writer);
            String content = writer.getBuffer().toString();
            FileUtils.writeStringToFile(targetFile, content, "utf-8", false);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    private InputStream getTemplate(String type) {
        String name = type + ".ftl";
        return this.getClass().getResourceAsStream("/" + name);
    }


}
