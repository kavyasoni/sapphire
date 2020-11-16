# Base Framework For Automation Testing

#### **How to add artifact in maven?**
    Step1. Download latest jar  (artifact/Sapphire.jar)
    Step2. Run below command in terminal
        mvn install:install-file -Dfile=<path-to-jar> -DgroupId=com.mcoe.framework -DartifactId=automation-base-framework -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true
         
         example

        mvn install:install-file -Dfile=/Users/ksoni/Sapphire.jar -DgroupId=com.mcoe.framework -DartifactId=automation-base-framework -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true
    Step3. Add below dependency in pom.xml

            <dependency>
                <groupId>com.mcoe.framework</groupId>
                <artifactId>automation-base-framework</artifactId>
                <scope>test</scope>
                <version>1.0</version>
            </dependency>
            
            
#### **TestNg maven dependencies**

		<dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>6.9.6</version>
            <scope>test</scope>
        </dependency>




