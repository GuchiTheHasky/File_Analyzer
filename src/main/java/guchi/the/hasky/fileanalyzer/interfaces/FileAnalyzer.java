package guchi.the.hasky.fileanalyzer.interfaces;

import guchi.the.hasky.fileanalyzer.entity.FileInfo;

public interface FileAnalyzer {
    /**
     * Return new FileInfo with:
     * List<String> sentences;
     * int count word;
     */
    FileInfo analyze(String path, String word);

//    /**
//     * Return int count of current word in String.
//     */
//    int countWord(String path, String word); // я залишив цей метод, щоб було більше тестів в абстрактному класі

}
