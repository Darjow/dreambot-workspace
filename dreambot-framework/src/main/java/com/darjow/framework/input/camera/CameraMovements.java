package com.darjow.framework.input.camera;

import org.dreambot.api.methods.input.Camera;

import java.util.Random;

import static com.darjow.framework.input.camera.CameraConstants.*;

public class CameraMovements {


    public static void randomSwipe(){
        Random random = new Random();
        int randomPitch = random.nextInt(MAX_PITCH - MIN_PITCH + 1) + MIN_PITCH;
        int randomYaw = random.nextInt(MAX_YAW - MIN_YAW + 1) + MIN_YAW;

        Camera.rotateTo(randomYaw, randomPitch);

    }
}
