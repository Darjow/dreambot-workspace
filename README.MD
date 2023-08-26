# Dreambot-workspace


## Introduction
This project consists of two main components: `dreambot-framework` and `dreambot-scripts`. 
The framework provides utility classes and logic, while the scripts use the framework to achieve specific tasks.

## Usage

### Updating the Framework
1. Make your desired changes in the `dreambot-framework` project.
2. Open a terminal/command prompt and navigate to the `dreambot-framework` project directory.
3. Run the following command to build and install the updated framework in your local Maven repository
as a JAR:  `` mvn clean install``

You can ofcourse always go to `view -> tool window -> maven`. And perform it manually. 

### Generating your JAR files
To generate a jar file from your scripts ensure you have generated the `dreambot-framework-{version}-jar`
as the `scripts` require the jar.

1. In case you created a new script, add the building and output step to the `pom.xml` as shown here as an example for the splasher script 
``` 
            <execution>
            <id>splasher</id>
            <configuration>
              <archive>
                <manifest>
                  <mainClass>com.darjow.scripts.splasher.Main</mainClass>
                </manifest>
              </archive>
              <descriptorRefs>
                <descriptorRef>jar-with-dependencies</descriptorRef>
              </descriptorRefs>
              <finalName>splasher</finalName>
              <outputDirectory>${user.home}/DreamBot/Scripts</outputDirectory>
            </configuration>
            <phase>package</phase>
            <goals>
              <goal>single</goal>
            </goals>
          </execution>
```
2. Open a terminal/command prompt and navigate to the `dreambot-scripts` project directory.
3. Run the following command to build and install the scripts in your local Maven repository
as a JAR: ``mvn clean install``
4. As you declared in step 1 that the outputDirectory is at the scripts folder, dreambot now automatiqually 
detects your script and you can run it.

### Result: 
<div style="display: flex; justify-content: center;">
  <img src="https://github.com/Darjow/dreambot-workspace/assets/77673973/1b0e425c-03fa-4644-8f52-3bec628ef6d6" 
       data-canonical-src="https://gyazo.com/eb5c5741b6a9a16c692170a41a49c858.png" 
       width="700" 
       height="400" 
       style="max-width: 100%; height: auto;" />
</div>
