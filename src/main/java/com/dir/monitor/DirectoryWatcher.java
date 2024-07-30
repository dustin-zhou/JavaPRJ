package com.dir.monitor;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectoryWatcher {
    private static final Logger logger = LoggerFactory.getLogger(DirectoryWatcher.class);

    public static void main(String[] args) {
        ConfigLoader config = new ConfigLoader("config.properties");

        String directoryPath = config.getProperty("monitor.directory");
        String backupDirectoryPath = config.getProperty("backup.directory");
        int checkInterval = config.getIntProperty("check.interval.seconds");

        Path path = Paths.get(directoryPath);

        if (!Files.exists(path)) {
            logger.error("Monitoring directory does not exist: " + directoryPath);
            return;
        }
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
                WatchKey key;
                try {
                    key = watchService.take();
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
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error("Thread interrupted", e);
                } catch (IOException e) {
                    logger.error("IOException occurred", e);
                }
            }, 0, checkInterval, TimeUnit.SECONDS);

        } catch (IOException e) {
            logger.error("Failed to set up watch service", e);
        }
    }

    private static void processFile(Path filePath, String backupDirectoryPath) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        for (String line : lines) {
            logger.info(line);
        }

        backupFile(filePath, backupDirectoryPath);
    }

    private static void backupFile(Path filePath, String backupDirectoryPath) throws IOException {
        Path backupDir = Paths.get(backupDirectoryPath);
        if (!Files.exists(backupDir)) {
            Files.createDirectories(backupDir);
        }
        Path backupFilePath = backupDir.resolve(filePath.getFileName());
        Files.copy(filePath, backupFilePath, StandardCopyOption.REPLACE_EXISTING);
        logger.info("File backed up: " + backupFilePath);
    }
}
