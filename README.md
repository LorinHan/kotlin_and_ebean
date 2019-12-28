### 1. Idea创建springboot项目，勾选web -> springboot web
### 2. 导入kotlin依赖项以及插件配置

```
        <!-- 版本 -->
        <properties>
            <java.version>1.8</java.version>
			<kotlin.version>1.3.60</kotlin.version>
        </properties>

        <!-- kotlin依赖项 -->
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-stdlib-jdk8</artifactId>
            <version>${kotlin.version}</version>
        </dependency>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-reflect</artifactId>
            <version>${kotlin.version}</version>
        </dependency>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-stdlib</artifactId>
            <version>${kotlin.version}</version>
        </dependency>
```
- compile时 指定目录，不指定的话用maven打包时会失败
- 插件配置：在 Kotlin 中，data class 默认没有无参构造方法，并且 data class 默认为 final 类型，不可以被继承。注意的是，如果我们使用 Spring + Kotlin 的模式，那么使用 @autowared 就可能遇到这个问题。因此，我们可以添加 NoArg 为标注的类生成无参构造方法。使用 AllOpen 为被标注的类去掉 final，允许被继承。

```
<plugin>
    <artifactId>kotlin-maven-plugin</artifactId>
    <configuration>
        <jvmTarget>1.8</jvmTarget>
    </configuration>
    <groupId>org.jetbrains.kotlin</groupId>
    <version>${kotlin.version}</version>
    <executions>
        <execution>
            <id>compile</id>
            <phase>compile</phase>
            <goals> <goal>compile</goal> </goals>
            <configuration>
                <sourceDirs>
                    <sourceDir>${project.basedir}/src/main/java</sourceDir>
                    <!--<sourceDir>${project.basedir}/src/main/java</sourceDir>-->
                </sourceDirs>
            </configuration>
        </execution>
        <execution>
            <id>test-compile</id>
            <phase>test-compile</phase>
            <goals> <goal>test-compile</goal> </goals>
        </execution>
    </executions>
    <dependencies>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-maven-noarg</artifactId>
            <version>${kotlin.version}</version>
        </dependency>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-maven-allopen</artifactId>
            <version>${kotlin.version}</version>
        </dependency>
    </dependencies>
</plugin>
```
### 3. 导入eBean依赖项

```
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
<!-- ebean -->
<dependency>
    <groupId>io.ebean</groupId>
    <artifactId>persistence-api</artifactId>
    <version>2.2.1</version>
</dependency>
<dependency>
    <groupId>io.ebean</groupId>
    <artifactId>ebean</artifactId>
    <version>12.1.8</version>
</dependency>
<!-- Optional: query bean support -->
<dependency>
    <groupId>io.ebean</groupId>
    <artifactId>ebean-querybean</artifactId>
    <version>12.1.8</version>
</dependency>
<!-- Optional: query bean generation via APT -->
<dependency>
    <groupId>io.ebean</groupId>
    <artifactId>querybean-generator</artifactId>
    <version>12.1.8</version>
    <scope>provided</scope>
</dependency>
<!-- Test dependencies -->
<!-- includes docker test database container support  -->
<dependency>
    <groupId>io.ebean</groupId>
    <artifactId>ebean-test</artifactId>
    <version>12.1.8</version>
    <scope>test</scope>
</dependency>
```
- 插件配置：

```
<!-- eBean -->
<plugin>
    <groupId>io.repaint.maven</groupId>
    <artifactId>tiles-maven-plugin</artifactId>
    <version>2.12</version>
    <extensions>true</extensions>
    <configuration>
        <tiles>
            <tile>io.ebean.tile:enhancement:12.1.8</tile> <!-- ebean enhancement -->
            <tile>io.ebean.tile:kotlin-kapt:1.5</tile> <!-- kotlin compile with query bean generation -->
        </tiles>
    </configuration>
</plugin>
```
### 4. 完整的pom文件：
- 注意修改项目名称、包名和artifacId等信息

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.example</groupId>
    <artifactId>kotlin_and_ebean</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>kotlin_and_ebean</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <kotlin.version>1.3.60</kotlin.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <!-- kotlin需要的依赖 -->
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-stdlib-jdk8</artifactId>
            <version>${kotlin.version}</version>
        </dependency>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-reflect</artifactId>
            <version>${kotlin.version}</version>
        </dependency>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-stdlib</artifactId>
            <version>${kotlin.version}</version>
        </dependency>
        <!--<dependency>-->
            <!--<groupId>org.jetbrains.kotlin</groupId>-->
            <!--<artifactId>kotlin-test</artifactId>-->
            <!--<version>1.3.61</version>-->
            <!--<scope>test</scope>-->
        <!--</dependency>-->
        <!-- /kotlin需要的依赖 -->

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.18</version>
        </dependency>

        <!-- ebean -->
        <dependency>
            <groupId>io.ebean</groupId>
            <artifactId>persistence-api</artifactId>
            <version>2.2.1</version>
        </dependency>
        <dependency>
            <groupId>io.ebean</groupId>
            <artifactId>ebean</artifactId>
            <version>12.1.8</version>
        </dependency>
        <!-- Optional: query bean support -->
        <dependency>
            <groupId>io.ebean</groupId>
            <artifactId>ebean-querybean</artifactId>
            <version>12.1.8</version>
        </dependency>
        <!-- Optional: query bean generation via APT -->
        <dependency>
            <groupId>io.ebean</groupId>
            <artifactId>querybean-generator</artifactId>
            <version>12.1.8</version>
            <scope>provided</scope>
        </dependency>
        <!-- Test dependencies -->
        <!-- includes docker test database container support  -->
        <dependency>
            <groupId>io.ebean</groupId>
            <artifactId>ebean-test</artifactId>
            <version>12.1.8</version>
            <scope>test</scope>
        </dependency>
        <!-- /ebean -->
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <mainClass>com.example.kotlin_and_ebean.KotlinAndEbeanApplication</mainClass>
                </configuration>
            </plugin>
            <!--在 Kotlin 中，data class 默认没有无参构造方法，并且 data class 默认为 final 类型，不可以被继承。注意的是，如果我们使用 Spring + Kotlin 的模式，
            那么使用 @autowared 就可能遇到这个问题。因此，我们可以添加 NoArg 为标注的类生成无参构造方法。使用 AllOpen 为被标注的类去掉 final，允许被继承。-->
            <plugin>
                <artifactId>kotlin-maven-plugin</artifactId>
                <configuration>
                    <jvmTarget>1.8</jvmTarget>
                </configuration>
                <groupId>org.jetbrains.kotlin</groupId>
                <version>${kotlin.version}</version>
                <executions>
                    <execution>
                        <id>compile</id>
                        <phase>compile</phase>
                        <goals> <goal>compile</goal> </goals>
                        <configuration>
                            <sourceDirs>
                                <sourceDir>${project.basedir}/src/main/java</sourceDir>
                                <!--<sourceDir>${project.basedir}/src/main/java</sourceDir>-->
                            </sourceDirs>
                        </configuration>
                    </execution>
                    <execution>
                        <id>test-compile</id>
                        <phase>test-compile</phase>
                        <goals> <goal>test-compile</goal> </goals>
                    </execution>
                </executions>
                <dependencies>
                    <dependency>
                        <groupId>org.jetbrains.kotlin</groupId>
                        <artifactId>kotlin-maven-noarg</artifactId>
                        <version>${kotlin.version}</version>
                    </dependency>
                    <dependency>
                        <groupId>org.jetbrains.kotlin</groupId>
                        <artifactId>kotlin-maven-allopen</artifactId>
                        <version>${kotlin.version}</version>
                    </dependency>
                </dependencies>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <executions>
                    <execution>
                        <id>compile</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>testCompile</id>
                        <phase>test-compile</phase>
                        <goals>
                            <goal>testCompile</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <!-- eBean -->
            <plugin>
                <groupId>io.repaint.maven</groupId>
                <artifactId>tiles-maven-plugin</artifactId>
                <version>2.12</version>
                <extensions>true</extensions>
                <configuration>
                    <tiles>
                        <tile>io.ebean.tile:enhancement:12.1.8</tile> <!-- ebean enhancement -->
                        <tile>io.ebean.tile:kotlin-kapt:1.5</tile> <!-- kotlin compile with query bean generation -->
                    </tiles>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```
### 5. 打开Idea设置 settings中的plugins，查找ebean 11，添加Ebean 11 Enhancement，然后重启Idea，在菜单栏的Build中可以看到Ebean 11 Enhancement的选项，勾选它
### 6. 在resources文件夹下新建ebean.properties，添加配置

```
ebean.search.packages= com.example
# the name of the default server
datasource.default=db
## define these in external properties ...
datasource.db.username=root
datasource.db.password=root
datasource.db.databaseUrl=jdbc:mysql://localhost:3306/springboot_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
# 这是mysql8的驱动，如果是mysql5，引入mysql5的驱动jar，而且下面要去掉cj，改为com.mysql.jdbc.Driver
datasource.db.databaseDriver=com.mysql.cj.jdbc.Driver
```

### 7. 创建eBean的工厂类

```
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class MyEbeanServerFactory implements FactoryBean<EbeanServer> {

    public EbeanServer getObject() throws Exception {
        return createEbeanServer();
    }

    public Class<?> getObjectType() {
        return EbeanServer.class;
    }

    public boolean isSingleton() {
        return true;
    }

    /**
     * Create a EbeanServer instance.
     */
    private EbeanServer createEbeanServer() {

        ServerConfig config = new ServerConfig();
        config.setName("db");

        // load configuration from ebean.properties,默认从resources下的ebean.properties中加载配置
        config.loadFromProperties();
        config.setDefaultServer(true);
        // other programmatic configuration

        return EbeanServerFactory.create(config);
    }
}
```
### 8. 创建实体类
```
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "t_author")
class Author{
    @Id
    var id: Long? = null
    var realName: String? = null
    var nickName: String? = null
}
```
### 9. kotlin使用Ebean，这里不写services了，直接写一个controller
- ebean的操作方式有多种: 
- 1.可以直接通过通过实体类操作.save() .delete()等等方法;
- 2.也可以通过ebean生成的"Q实体类"进行各种操作;
- 3.也可以用工厂类EbeanServer来操作实体类或者使用原生sql
- 注意：我们只写了Author类，推测是Ebean会帮我们打包出"Q实体类"，原本我是用maven打包过，后来再用了QAuthor这个类，没有出问题，但是当我用pom重新构建一个项目的时候发现QAuthor类找不到，几经尝试发现编写完实体类之后，需要先用maven compile一下才会在target下的domain包中生成出Q实体类，然后就可以直接操作Q实体类了。
```
import com.example.kotlin_and_ebean.domain.Author
import io.ebean.EbeanServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class EbeanController {
    @Autowired
    private lateinit var ebeanServer: EbeanServer

    @RequestMapping(value = ["/ebean"], method = [RequestMethod.GET])
    fun getAuthorList(): String {
//        新增
        val author = Author()
//        author.id = 10L
//        author.realName = "test"
//        author.nickName = "test"
//        ebeanServer.save(author)
//        查询
        var find = ebeanServer.find(Author::class.java, 1)
        println(find?.nickName)
        return "ebean"
    }

    @GetMapping("/testEbean")
    fun testEbean(): List<Author> {
        /*
        * 注意： QAuthor并不是我们自己写的，如果要使用这个类的话，需要编译一下，
        * 编写完实体类之后，maven compile一下，就会在target的domain包下出现query包，里面会有这个类
        * */
        val authors = QAuthor().setMaxRows(2).findList() // 类型是BeanList
        authors.forEach{
            println(it.id)
        }
        for(item in authors) {
            println(item.nickName)
        }
        return authors
    }

    @GetMapping("/testNative")
    fun testNative(): String {
        var findNative = ebeanServer.sqlQuery("select id, real_name from t_author where id <= 2;").findList()
        // 类型应该是List<Any>, 直接toString当做json返回就行了
        println(findNative is List<Any>)
        return findNative.toString()
    }

    @GetMapping("/testKotlin")
    fun testKotlin(): String {
        var arr = intArrayOf(1, 2, 3)
        println(arr[0])
        var arrList = arrayListOf<Int>(1, 2, 3)
        arrList.add(4)
        println(arrList)
        var str = "abc"
        str += "$str 的长度为：${str.length * 2}"
        println(str)
        arr[0] = 2
        when(arr[0]) {
            1 -> println("nice")
            else -> println("fuck")
        }
        println("==========")
        if(3 in arr) {
            println("3 in arr!")
        }
//        for((item, index) in arrList.withIndex()) {
//            println("$item 的下标为$index")
//        }
        arrList.withIndex().forEach{ print(it)}
        var obj = hashMapOf<String, Int>("a" to 1, "b" to 2)
        println(obj)
        return "test kotlin"
    }
}
```
### 10. java使用Ebean，这里不写services了，直接写一个controller
```
@GetMapping("/test2")
public String test2() {
    Author author = ebeanServer.find(Author.class, 1);
    System.out.println(author.getNickName());
    return "ok";
}
```