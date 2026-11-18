package ru.jokardo.music_service.service.player;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class MusicPlayer {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(1);
    public static Player player;

    public static boolean playTrackFromResource(String downloadLink) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(downloadLink).openStream())) {
            initializePlayer(in);

            executorService.submit(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    throw new RuntimeException(e);
                }
            }).get();
            return true;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            log.debug("Воспроизведение было остановлено");
            executorService.shutdownNow();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.debug("Ошибка при попытке открыть InputStream");
            throw new RuntimeException(e);
        }
        return false;
    }

    private static void stopPlayer() {
        if (player != null) {
            player.close();
        }
    }

    private static void initializePlayer(BufferedInputStream in) {
        if (player == null) {
            try {
                player = new Player(in);
            } catch (JavaLayerException e) {
                log.debug("Ошибка при попытке создать 'Player'.");
                log.debug("Возможные причины: ");
                log.debug("- не удается инициализировать системное аудиоустройство, оно не поддерживается или отсутствуют ресурсы");
                log.debug("- поток поврежден");
                log.debug("- сбой внутренних ресурсов");
            }
        }
    }

}
