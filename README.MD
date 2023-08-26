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
``` <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <version>3.2.0</version>
        <executions>
          <execution>
            <id>package-splasher</id>
            <phase>package</phase>
            <goals>
              <goal>jar</goal>
            </goals>
            <configuration>
              <includes>
                <include>com/darjow/scripts/splasher/**</include>
              </includes>
              <outputDirectory>${user.home}\DreamBot\Scripts</outputDirectory>
              <classifier>splasher</classifier>
            </configuration>
          </execution>
        </executions>
      </plugin>
```
2. Open a terminal/command prompt and navigate to the `dreambot-scripts` project directory.
3. Run the following command to build and install the scripts in your local Maven repository
as a JAR: ``mvn clean install``
4. As you declared in step 1 that the outputDirectory is at the scripts folder, dreambot now automatiqually 
detects your script and you can run it.

See: ![outcome](https://prnt.sc/rfc4PNp_X_tk)
   

