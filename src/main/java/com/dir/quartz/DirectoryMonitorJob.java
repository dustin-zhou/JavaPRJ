package com.dir.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.nio.file.*;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectoryMonitorJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(DirectoryMonitorJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ConfigLoader config = new ConfigLoader("config.properties");
        String directoryPath = config.getProperty("monitor.directory");
        String backupDirectoryPath = config.getProperty("backup.directory");

        Path path = Paths.get(directoryPath);

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            WatchKey key = watchService.poll();
            if (key != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }

                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();
                    Path filePath = path.resolve(fileName);

                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        logger.info("New file created: " + filePath);
                        processFile(filePath, backupDirectoryPath);
                    }
                }
                key.reset();
            }
        } catch (IOException e) {
            logger.error("IOException occurred", e);
        }
    }

    private void processFile(Path filePath, String backupDirectoryPath) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        for (String line : lines) {
            logger.info(line);
        }

        backupFile(filePath, backupDirectoryPath);
    }

    private void backupFile(Path filePath, String backupDirectoryPath) throws IOException {
        Path backupDir = Paths.get(backupDirectoryPath);
        if (!Files.exists(backupDir)) {
            Files.createDirectories(backupDir);
        }
        Path backupFilePath = backupDir.resolve(filePath.getFileName());
        Files.copy(filePath, backupFilePath, StandardCopyOption.REPLACE_EXISTING);
        logger.info("File backed up: " + backupFilePath);
    }
}