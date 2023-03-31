package guchi.the.hasky.fileanalyzer.entity;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class FileStatistic {
    @NonNull
    private int countWord;
    private List<String> sentences;
}

