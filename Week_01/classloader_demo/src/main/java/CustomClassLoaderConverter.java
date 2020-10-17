import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @program: classloader_demo
 * @description: 通过文件的绝对路径加载以及调用Hello.xlass的hello方法
 * @author: yarne
 * @create: 2020-10-16 15:14
 **/
public class CustomClassLoaderConverter extends ClassLoader {
    public static void main(String[] args) {
        try {
            //调用customDefineClassByFile方法并且进行实例化
            Object hello = new CustomClassLoaderConverter().customDefineClassByFile("Hello", new File("/Users/yarne/Desktop/Hello/Hello.xlass")).newInstance();

            //已经看过Hello代码情况下的hello方法调用
            Method hello1 = hello.getClass().getMethod("hello", null);
            hello1.invoke(hello, null);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Class<?> customDefineClassByFile(String name, File file)
            throws ClassFormatError {
        //定义文件相同长度的字节数组
        byte[] encodeByte = new byte[(int) file.length()];
        try (InputStream inputStream = new FileInputStream(file)) {
            //将文件的字节全部读出来
            inputStream.read(encodeByte);
            //还原字节
            for (int i = 0; i < encodeByte.length; i++) {
                encodeByte[i] = (byte) (255 - encodeByte[i]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, encodeByte, 0, encodeByte.length, null);
    }
}