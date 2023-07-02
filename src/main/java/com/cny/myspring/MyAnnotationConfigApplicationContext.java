package com.cny.myspring;


import com.cny.myspring.annotion.MyAutowired;
import com.cny.myspring.annotion.MyComponent;
import com.cny.myspring.annotion.MyComponentScan;
import com.cny.myspring.annotion.MyScope;
import com.cny.myspring.beanpostporcessor.BeanPostProcessor;
import com.cny.myspring.beans.MyBeanDefinition;
import com.cny.myspring.beans.MyInitializingBean;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : chennengyuan
 */
public class MyAnnotationConfigApplicationContext {

    private Class configClass;

    /**
     * key: 类名
     * value: 类定义信息
     */
    private Map<String, MyBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private Map<String, Object> singletonObjMap = new ConcurrentHashMap<>();

    private List<BeanPostProcessor> beanPostProcessorList = new LinkedList<>();

    public MyAnnotationConfigApplicationContext(Class configClass) throws Exception {
        this.configClass = configClass;
        //1.扫描指定路径下的所有文件
        scanFile(configClass);

        //2.创建单例bean
        createSingletonObject();
    }

    private void createSingletonObject() throws Exception {
        for (Map.Entry<String, MyBeanDefinition> entry : beanDefinitionMap.entrySet()) {
            MyBeanDefinition beanDefinition = entry.getValue();
            if("singleton".equals(beanDefinition.getScope())){
                Object object = beanDefinition.getType().newInstance();

                //设置属性
                doFillProperties(object, beanDefinition);

                //执行初始化前操作
                doBeforeInit(object, beanDefinition);

                //执行初始化操作
                doInit(object);

                //执行初始化后操作
                doAfterInit(object, beanDefinition);

                singletonObjMap.put(entry.getKey(), object);
            }
        }
    }

    private void doBeforeInit(Object object, MyBeanDefinition beanDefinition) {
        if(!BeanPostProcessor.class.isAssignableFrom(beanDefinition.getType())){
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                beanPostProcessor.postProcessBeforeInitialization(object, beanDefinition.getBeanname());
            }
        }
    }

    private void doAfterInit(Object object, MyBeanDefinition beanDefinition) {
        if(!BeanPostProcessor.class.isAssignableFrom(beanDefinition.getType())){
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                beanPostProcessor.postProcessAfterInitialization(object, beanDefinition.getBeanname());
            }
        }
    }

    private void doInit(Object object) {
        if(object instanceof MyInitializingBean){
            try {
                ((MyInitializingBean) object).afterPropertiesSet();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void doFillProperties(Object object, MyBeanDefinition bean) throws Exception {
        Field[] declaredFields = bean.getType().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if(declaredField.isAnnotationPresent(MyAutowired.class)){
                declaredField.setAccessible(true);
                declaredField.set(object, getBean(declaredField.getName()));
            }

        }
    }

    private void scanFile(Class configClass) throws InstantiationException, IllegalAccessException {
        if (configClass.isAnnotationPresent(MyComponentScan.class)) {
            MyComponentScan myComponentScanAnnotation = (MyComponentScan) configClass.getAnnotation(MyComponentScan.class);
            String packagePath = myComponentScanAnnotation.value();//com.cny.myspring

            ClassLoader classLoader = this.getClass().getClassLoader();
            String scanpath = packagePath.replaceAll("\\.", "/");
            URL resource = classLoader.getResource(scanpath);

            // TODO 此处中文路径有问题，故写死 正常是 resource.getFile()
            File file = new File("D:\\项目\\P6程序员\\23-spring-source\\target\\classes\\com\\cny\\myspring\\service");
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    //获取全类名
                    String className = f.getName().replaceAll("\\.class", "");
                    String fullClassName = packagePath + "." + className;

                    try {
                        //加载类
                        Class<?> loadClass = classLoader.loadClass(fullClassName);

                        //2.读取文件的信息，判断是否有MyComponent注解，若有，则保存
                        if (loadClass.isAnnotationPresent(MyComponent.class)) {

                            //增加对BeanPostProcessor的判断
                            if(BeanPostProcessor.class.isAssignableFrom(loadClass)){
                                BeanPostProcessor beanPostProcessor = (BeanPostProcessor) loadClass.newInstance();
                                beanPostProcessorList.add(beanPostProcessor);
                            } else {
                                MyComponent myComponentAnnotation = loadClass.getAnnotation(MyComponent.class);
                                String beanName = myComponentAnnotation.value();
                                if ("".equals(beanName)) {
                                    //若beanname为空，则设置为类名称
                                    beanName = Introspector.decapitalize(loadClass.getSimpleName());
                                }

                                //设置单例还是多例模式
                                MyBeanDefinition beanDefinition = new MyBeanDefinition();
                                beanDefinition.setType(loadClass);
                                beanDefinition.setBeanname(beanName);
                                if(loadClass.isAnnotationPresent(MyScope.class)){
                                    MyScope myScope = loadClass.getAnnotation(MyScope.class);
                                    String scope = myScope.value();
                                    beanDefinition.setScope(scope);
                                } else {
                                    beanDefinition.setScope("singleton");
                                }

                                //保存到map中
                                beanDefinitionMap.put(beanName, beanDefinition);
                            }

                        }

                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }
    }

    public Object getBean(String beanName) throws Exception {
        if(!beanDefinitionMap.containsKey(beanName)){
            throw new Exception(beanName + "不存在");
        }

        MyBeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if("singleton".equals(beanDefinition.getScope())){
            return singletonObjMap.get(beanName);
        }
        return beanDefinition.getType().newInstance();
    }
}
