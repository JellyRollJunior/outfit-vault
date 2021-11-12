package com.example.outfitvault.model;

import static org.junit.Assert.*;

import com.example.outfitvault.types.Season;

import org.junit.Test;

public class OutfitTest {

    @Test
    public void getImageName() {
        Outfit outfit = new Outfit(1,"imageName", "that's an image", Season.FALL, true);
        assertEquals("imageName", outfit.getPhotoName());
    }
}