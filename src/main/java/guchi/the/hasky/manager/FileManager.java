package guchi.the.hasky.manager;

import guchi.the.hasky.fileanalyzer.utils.DefaultModifierForTests;

import java.io.*;

public class FileManager {

    public static int filesCount(String path) {
        validatePath(path);
        File directory = new File(path);
        return filesCount(directory);
    }

    public static int directoriesCount(String path) {
        validatePath(path);
        File directory = new File(path);
        return directoriesCount(directory);
    }

    public static void copy(String path, String destination) {
        validatePath(path, destination);
        copyFile(path, destination);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void move(String source, String destination) {
        File file = new File(source);
        System.out.println(file.getName());
        File newFile = new File(destination + file.getName());
        file.renameTo(newFile);
    }

    private static int filesCount(File directory) {
        int count = 0;
        File[] directories = directory.listFiles();
        if (directories == null) {
            return count;
        }
        for (File file : directories) {
            if (file.isFile()) {
                count++;
                count += filesCount(file);
            } else {
                File[] files = file.listFiles();
                if (files != null) {
                    count += filesCount(file);
                }
            }
        }
        return count;
    }

    private static int directoriesCount(File directory) {
        int count = 0;
        File[] directories = directory.listFiles();
        if (directories == null) {
            return count;
        }
        for (File file : directories) {
            if (file.isDirectory()) {
                count++;
                count += directoriesCount(file);
            } else {
                File[] files = file.listFiles();
                if (files != null) {
                    count += directoriesCount(file);
                }
            }
        }
        return count;
    }

    private static void copyFile(String path, String destination) {
        File source = new File(path);
        validateFileSize(source);
        File dest = new File(destination + File.separator + source.getName());
        try (FileInputStream input = new FileInputStream(source);
             FileOutputStream output = new FileOutputStream(dest)) {
            int size = (int) source.length();
            byte[] buffer = new byte[size];
            int length;
            while ((length = input.read(buffer)) != -1) {
                output.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new RuntimeException("Exception during file is reading.", e);
        }
    }

    @DefaultModifierForTests
    static void validatePath(String path, String destination) {
        validatePath(path);
        validatePath(destination);
    }

    @DefaultModifierForTests
    static void validatePath(String path) {
        if (path == null) {
            throw new NullPointerException("Error, current path is null.");
        }
        if (!new File(path).exists()) {
            throw new IllegalArgumentException("Error, current directory: " + path + " doesn't exist.");
        }
    }

    @DefaultModifierForTests
    static void validateFileSize(File source) {
        if (source.length() > 8192) {
            throw new StackOverflowError("Error, current file: " + source + " is too large.");
        }
    }
}
