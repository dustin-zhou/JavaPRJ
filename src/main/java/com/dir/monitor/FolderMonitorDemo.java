package com.dir.monitor;

import java.io.IOException;
import java.nio.file.*;

public class FolderMonitorDemo {

    public static void main(String[] args) {
        String folderPath = "D:/Watch/monitor"; // 指定要监控的文件夹路径
        String backupFolderPath = "D:/Watch/back"; // 备份文件夹路径

        try {
            Path folder = Paths.get(folderPath);
            WatchService watchService = FileSystems.getDefault().newWatchService();
            folder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            System.out.println("Folder monitoring started...");

            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        Path createdFile = (Path) event.context();
                        System.out.println("New file created: " + createdFile);

                        // 读取文件内容
                        Path filePath = folder.resolve(createdFile);
                        try {

                            System.out.println("File content:");

                            // 备份文件
                            Path backupDir = Paths.get(backupFolderPath);
                            Files.copy(filePath, backupDir.resolve(createdFile), StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("File backed up successfully.");
                        } catch (IOException e) {
                            System.err.println("Failed to read or backup file: " + e.getMessage());
                        }
                    }
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

