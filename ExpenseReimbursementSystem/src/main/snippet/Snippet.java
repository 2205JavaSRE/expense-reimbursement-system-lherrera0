package snippet;

public class Snippet {
	  <properties>
	  	<maven.compiler.source>1.8</maven.compiler.source>
	  	<maven.compiler.target>1.8</maven.compiler.target>
	  	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	  	
	  </properties>
	  
	  <!-- 
	  	How do I connect my Java project to a Postgresql Database?
	  		I have no idea. 
	  	
	  	But I can grab a library of tools prepared by other developers that abstract away 
	  		the connection through classes, interfaces and methods.
	   -->
	   
	   <build>
	   		<plugins>
	   			<plugin>
	    <groupId>org.apache.maven.plugins</groupId>
	    <artifactId>maven-shade-plugin</artifactId>
	    <executions>
	        <execution>
	        	<phase>
}

