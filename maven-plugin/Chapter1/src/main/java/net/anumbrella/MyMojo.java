package net.anumbrella;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.*;


@Mojo(name = "file", defaultPhase = LifecyclePhase.COMPILE)
public class MyMojo extends AbstractMojo {


    public void execute() throws MojoExecutionException, MojoFailureException {
        readFile();
    }

    private void readFile() throws MojoFailureException {
        try {

            InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream("/file.txt"), "utf-8");
            //创建字符流缓冲区
            BufferedReader bufr = new BufferedReader(reader);
            String line;
            while ((line = bufr.readLine()) != null) {
                getLog().info(line);
            }
            reader.close();

        } catch (Exception e) {
            throw new MojoFailureException("读取file信息出错", e);
        }
    }
}
