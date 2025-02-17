package cn.devicelinks.console.annotation;

import cn.devicelinks.console.ConsoleApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.*;

/**
 * 运行测试用例的注解
 * <p>
 * 使用Junit方式运行单元测试用例时将该注解配置到需要运行测试用例的类上即可
 * {@code
 *  @RunTest
 *  public class SearchFieldTest {
 *      //...
 *  }
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringBootTest(classes = ConsoleApplication.class)
public @interface RunTest {
    //...
}
