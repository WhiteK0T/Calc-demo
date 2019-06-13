package controller;

import model.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Intepretator {

    private List<ICalc> listOperation = new ArrayList<>();
    private List<String> listStrOperation = new ArrayList<>();

    public Intepretator() {
        findAndAddModel();
        listOperation.forEach(iCalc -> listStrOperation.add(iCalc.getOperation()));
    }

    public List<String> getListSupportOperations() {
        return listStrOperation;
    }

    public int startIntr(int a, int b, String operator) {
        return listOperation.stream()
                .filter(p -> p.getOperation().equals(operator))
                .findFirst()
                .get()
                .calc(a, b);
    }

    private void findAndAddModel() {
        String pathStr = getPathProgName(Addition.class);
        if (pathStr.contains(".jar")) {
            jarLoadClass(pathStr, listOperation);
        } else {
            dirLoadClass(pathStr, listOperation);
        }
    }

    private void jarLoadClass(String pathStr, List<ICalc> listOperation) {
        try {
            JarFile jarFile = new JarFile(pathStr);
            Enumeration<JarEntry> e = jarFile.entries();
            URL[] urls = {new URL("jar:file:" + pathStr + "!/")};
            URLClassLoader cl = URLClassLoader.newInstance(urls);
            while (e.hasMoreElements()) {
                JarEntry je = e.nextElement();
                if (je.isDirectory() || !je.getName().endsWith(".class")) {
                    continue;
                }
                if (je.getName().startsWith("model") & !je.getName().contains("ICalc")) {
                    // -6 because of .class
                    String className = je.getName().substring(0, je.getName().length() - 6);
                    className = className.replace('/', '.');
                    Class c = cl.loadClass(className);
                    listOperation.add((ICalc) c.newInstance());
                }
            }
        } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void dirLoadClass(String pathStr, List<ICalc> listOperation) {
        Path path = Paths.get(pathStr + "/model/");
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
            for (Path file : directoryStream) {
                if (!"ICalc.class".equals(file.getFileName().toString())) {
                    String a = file.getFileName().toString();
                    Class tmp = Class.forName("model." + a.substring(0, a.indexOf(".")));
                    listOperation.add((ICalc) tmp.newInstance()); //надо заменить на не деприкайтед) пока хз на че
                }
            }
        } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static String getPathProgName(Class<?> classGetPath) {
        String pathClass = classGetPath.getProtectionDomain().getCodeSource().getLocation().getFile();
        if (pathClass == null) return null;
        if (pathClass.charAt(0) == '/' & System.getProperty("os.name").toLowerCase().contains("win"))
            pathClass = pathClass.substring(1);
        return Paths.get(pathClass).toAbsolutePath().toString();
    }

}

