package d3e.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageResizeService implements Runnable {

    private static final long SLEEP_TIME = 1_000;

    private class ResizeJob {
        String fileName;
        int width;
        int height;
        D3EResourceHandler handler;

        ResizeJob(String fileName, int width, int height, D3EResourceHandler handler) {
            this.fileName = fileName;
            this.width = width;
            this.height = height;
            this.handler = handler;
        }
    }

    private Queue<ResizeJob> jobs = new ConcurrentLinkedQueue<>();

    public void resize(String fileName, int width, int height, D3EResourceHandler handler) {
        pushJob(new ResizeJob(fileName, width, height, handler));
    }
    
    public void resizeNow(String fileName, int width, int height, D3EResourceHandler handler) {
    	resizeAndSave(new ResizeJob(fileName, width, height, handler));
    }

    @Override
    public void run() {
        while (true) {
            ResizeJob job = popJob();
            if (job == null) {
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                }
            } else {
                if (!resizeAndSave(job)) {
                    pushJob(job);
                }
            }
        }
    }

    private boolean resizeAndSave(ResizeJob job) {
        // Get the original resource from resourceHandler
        Resource originalResource = job.handler.get(job.fileName);
        File tempCopy = null;
        
        try {
            // Retrieve the original file to resize
            File originalFile = originalResource.getFile();
            // Create a temporary copy of the original file with "copy_" prefix
            tempCopy = new File(originalFile.getParent(), "copy_" + originalFile.getName());
            Files.copy(originalFile.toPath(), tempCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);
            // Create the resized file path
            File resizedFile = new File(originalFile.getParent(), tempCopy.getName());
            // Perform the resize operation and save to the new file
            Thumbnails.of(tempCopy)
                      .size(job.width, job.height) // Resize to specified width and height
                      .toFile(resizedFile); // Save to the resized file
            // Create a new DFile and set the MIME type and ID
            DFile dFile = new DFile();
            dFile.setId(FileUtils.getResizedName(job.fileName, job.width, job.height));
            dFile.setMimeType(FileUtils.detectMimeType(resizedFile));

            // Create a new Resource for the resized file
            Resource resizedResource = new FileSystemResource(resizedFile);

            // Save the resized file using the original resource handler
            job.handler.persist(dFile, resizedResource);

        } catch (IOException e) {
            e.printStackTrace(); // Log the exception
            return false;
        } finally {
            // Clean up the temporary copy
            if (tempCopy != null && tempCopy.exists()) {
                tempCopy.delete();
            }
        }
        return true;
    }

    private synchronized void pushJob(ResizeJob job) {
        this.jobs.add(job);
    }

    private synchronized ResizeJob popJob() {
        return this.jobs.poll();
    }
}
