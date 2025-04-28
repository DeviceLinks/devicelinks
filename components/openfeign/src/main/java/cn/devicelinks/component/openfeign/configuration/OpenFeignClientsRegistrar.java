package cn.devicelinks.component.openfeign.configuration;

import cn.devicelinks.component.openfeign.annotation.EnableOpenFeignClients;
import cn.devicelinks.component.openfeign.annotation.OpenFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * {@link OpenFeignClient}注解扫描注册Bean
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class OpenFeignClientsRegistrar implements ImportBeanDefinitionRegistrar{

    private static final String CLIENT_SCAN_RESOURCE_PATH = "classpath*:cn/devicelinks/**/*.class";

    private static final String ANNOTATION_ATTRIBUTE_CLIENTS = "clients";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(EnableOpenFeignClients.class.getName());
        Class<?>[] clients = (Class<?>[]) attributes.get(ANNOTATION_ATTRIBUTE_CLIENTS);

        // If no Clients are specified, scan all
        if (ObjectUtils.isEmpty(clients)) {
            ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
            scanner.addIncludeFilter(new AnnotationTypeFilter(OpenFeignClient.class));
            List<Class<?>> scanClientClassList = this.doScanClients();
            clients = !ObjectUtils.isEmpty(scanClientClassList) ? scanClientClassList.toArray(Class[]::new) : new Class[]{};
        }

        Arrays.stream(clients).forEach(clientClass -> {
            OpenFeignClient openFeignClient = clientClass.getAnnotation(OpenFeignClient.class);
            String feignTargetServiceName = openFeignClient.name();
            if (ObjectUtils.isEmpty(feignTargetServiceName)) {
                log.warn("FeignClient: [{}], cannot be registered, " +
                        "reason: the name attribute is not configured in the @OpenFeignClient annotation.", clientClass.getName());
                return;
            }
            RootBeanDefinition beanDefinition = new RootBeanDefinition();
            beanDefinition.setBeanClass(OpenFeignClientFactoryBean.class);
            // Constructor Arguments
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(clientClass);
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(feignTargetServiceName);

            registry.registerBeanDefinition(clientClass.getName(), beanDefinition);
        });
    }

    private List<Class<?>> doScanClients() {
        List<Class<?>> clientClassNameList = new ArrayList<>();
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(CLIENT_SCAN_RESOURCE_PATH);

            MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();

            for (Resource resource : resources) {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                ClassMetadata classMetadata = metadataReader.getClassMetadata();
                AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();

                if (annotationMetadata.hasAnnotation(OpenFeignClient.class.getName())) {
                    clientClassNameList.add(Class.forName(classMetadata.getClassName()));
                }
            }
        } catch (Exception e) {
            log.error("An exception was encountered when scanning the interface annotated with @OpenFeignClient.", e);
        }
        log.info("The list of interfaces annotated with @OpenFeignClient is scanned: [{}].", clientClassNameList);
        return clientClassNameList;
    }
}
