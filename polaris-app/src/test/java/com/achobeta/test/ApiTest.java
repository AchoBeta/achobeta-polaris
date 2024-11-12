package com.achobeta.test;

import com.achobeta.types.constraint.PrefixConstraint;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    private Validator validator;

    @AllArgsConstructor
    class Dog {
        @PrefixConstraint(value = "zhangsan")
        private String name;
    }

    @Test
    public void testA() {
//        Set<ConstraintViolation<Dog>> violations = validator.validate(new Dog("zhangsan111"));
        Set<ConstraintViolation<Dog>> violations = validator.validate(new Dog("111"));
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    @Test
    public void test() {
        log.info("测试完成");
    }

    @Test
    public void git() {
        try {
            String diff = getDiffForJavaFiles();
            System.out.println(diff);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getDiffForJavaFiles() throws IOException, InterruptedException {
        // 获取最新提交的哈希值
        ProcessBuilder logProcessBuilder = new ProcessBuilder("git", "log", "-1", "--pretty=format:%H");
        logProcessBuilder.directory(new File("."));
        Process logProcess = logProcessBuilder.start();

        BufferedReader logReader = new BufferedReader(new InputStreamReader(logProcess.getInputStream()));
        String latestCommitHash = logReader.readLine();
        logReader.close();
        logProcess.waitFor();

        // 获取最新提交与上一个提交之间的差异
        ProcessBuilder diffProcessBuilder = new ProcessBuilder("git", "diff", "--name-only", latestCommitHash + "^", latestCommitHash);
        diffProcessBuilder.directory(new File("."));
        Process diffProcess = diffProcessBuilder.start();

        BufferedReader diffReader = new BufferedReader(new InputStreamReader(diffProcess.getInputStream()));
        String line;
        StringBuilder diffCode = new StringBuilder();
        while ((line = diffReader.readLine()) != null) {
            if (line.endsWith(".java")) {
                // 如果文件是 Java 文件，获取该文件的 diff
                ProcessBuilder fileDiffProcessBuilder = new ProcessBuilder("git", "diff", latestCommitHash + "^", latestCommitHash, "--", line);
                fileDiffProcessBuilder.directory(new File("."));
                Process fileDiffProcess = fileDiffProcessBuilder.start();

                BufferedReader fileDiffReader = new BufferedReader(new InputStreamReader(fileDiffProcess.getInputStream()));
                String fileDiffLine;
                while ((fileDiffLine = fileDiffReader.readLine()) != null) {
                    diffCode.append(fileDiffLine).append("\n");
                }
                fileDiffReader.close();
                fileDiffProcess.waitFor();
            }
        }
        diffReader.close();

        int exitCode = diffProcess.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Failed to get diff, exit code: " + exitCode);
        }

        return diffCode.toString();
    }

}
