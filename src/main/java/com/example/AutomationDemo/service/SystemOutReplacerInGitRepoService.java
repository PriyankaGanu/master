package com.example.AutomationDemo.service;

import com.example.AutomationDemo.model.AutomationData;
import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

@Service
public class SystemOutReplacerInGitRepoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemOutReplacerInGitRepoService.class);
    private static final String SYSTEM_OUT_PATTERN = "System.out.println";
    private static final String LOGGER_DECLARATION = "private static final Logger LOGGER = LoggerFactory.getLogger";
    private static final String LOGGER_STATEMENT = "LOGGER.info";
    private static final String LOGGER_IMPORT = "import org.slf4j.Logger;";
    private static final String LOGGER_FACTORY_IMPORT = "import org.slf4j.LoggerFactory;";

    public void replaceSystemOut(AutomationData automationData) {
        LOGGER.info("Starting System.out.println replacement...");

        // Clone the GitHub repository
        File tempDir = new File(System.getProperty("java.io.tmpdir"), "cloned-repo");
        if (tempDir.exists()) {
            deleteDirectory(tempDir); // Clean up if the directory already exists
        }

        try {
            LOGGER.info("Cloning repository from URL: {}", automationData.getGitRepoUrl());
            Git.cloneRepository()
                    .setURI(automationData.getGitRepoUrl())
                    .setDirectory(tempDir)
                    .call();
            LOGGER.info("Repository cloned successfully!");

            // Process all Java files in the cloned repository
            try (Stream<Path> paths = Files.walk(tempDir.toPath())) {
                paths.filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".java"))
                        .forEach(this::processFile);
            }
        } catch (Exception e) {
            LOGGER.error("Error cloning repository or processing files: {}", e.getMessage());
        } finally {
            // Clean up the cloned repository
            deleteDirectory(tempDir);
        }

        LOGGER.info("Replacement complete!");
    }

    private void processFile(Path filePath) {
        try {
            // Read the file content
            String content = new String(Files.readAllBytes(filePath));

            // Check if the file contains System.out.println
            if (content.contains(SYSTEM_OUT_PATTERN)) {
                // Add logger declaration if not present
                if (!content.contains(LOGGER_DECLARATION)) {
                    content = addLoggerDeclaration(content, filePath);
                }

                // Add logger imports if not present
                if (!content.contains(LOGGER_IMPORT)) {
                    content = addImport(content, LOGGER_IMPORT);
                }
                if (!content.contains(LOGGER_FACTORY_IMPORT)) {
                    content = addImport(content, LOGGER_FACTORY_IMPORT);
                }

                // Replace System.out.println with LOGGER.info
                content = content.replaceAll(
                        "System\\.out\\.println\\((.*?)\\);",
                        LOGGER_STATEMENT + "($1);"
                );

                // Write the updated content back to the file
                Files.write(filePath, content.getBytes());
                LOGGER.info("Processed: {}", filePath);
            }
        } catch (IOException e) {
            LOGGER.error("Error processing file {}: {}", filePath, e.getMessage());
        }
    }

    private String addLoggerDeclaration(String content, Path filePath) {
        // Find the class name
        String className = filePath.getFileName().toString().replace(".java", "");

        // Add logger declaration after the package and imports
        String loggerDeclaration = "private static final Logger LOGGER = LoggerFactory.getLogger(" + className + ".class);";
        int insertPosition = content.indexOf("{") + 1; // After the opening brace of the class
        return content.substring(0, insertPosition) + "\n    " + loggerDeclaration + "\n" + content.substring(insertPosition);
    }

    private String addImport(String content, String importStatement) {
        // Add the import statement after the package declaration
        int insertPosition = content.indexOf(";") + 1; // After the package declaration
        return content.substring(0, insertPosition) + "\n" + importStatement + "\n" + content.substring(insertPosition);
    }

    private void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                deleteDirectory(file);
            }
        }
        directory.delete();
    }
}