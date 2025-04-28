import cn.devicelinks.component.openfeign.annotation.OpenFeignClient;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;

/**
 * ClassPath类扫描单元测试
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class ClassPathScanningTest {
    public static void main(String[] args) throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:cn/devicelinks/api/**/*.class");

        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();

        for (Resource resource : resources) {
            MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
            ClassMetadata classMetadata = metadataReader.getClassMetadata();
            AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();

            if (annotationMetadata.hasAnnotation(OpenFeignClient.class.getName())) {
                // 说明是目标接口，继续注册
                System.out.println(classMetadata.getClassName());
            }
        }
    }
}
