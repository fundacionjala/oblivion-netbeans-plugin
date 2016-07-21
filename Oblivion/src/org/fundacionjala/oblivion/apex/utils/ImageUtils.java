/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 * Utility class for Images.
 * @author Marcelo Garnica
 */
public class ImageUtils {
    
   /**
    * Converts a given Image into a BufferedImage
    *
    * @param originalImage - The Image to be converted
    * @return The converted BufferedImage
    */
   public static BufferedImage toBufferedImage(Image originalImage) {
       if (originalImage instanceof BufferedImage) {
           return (BufferedImage) originalImage;
       }
       BufferedImage convertedImage = new BufferedImage(originalImage.getWidth(null), originalImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
       Graphics2D graphics = convertedImage.createGraphics();
       graphics.drawImage(originalImage, 0, 0, null);
       graphics.dispose();
       return convertedImage;
   }
   
   /**
    * Merges two images into one image.
    * @param originalImage - The original or container image.
    * @param imageToMerge - The image to be merged into the original one.
    * @return The merged image.
    */
   public static Image mergeImages(Image originalImage, Image imageToMerge) {
       return mergeImages(toBufferedImage(originalImage), toBufferedImage(imageToMerge));
   }
   
   /**
    * Merges two Buffered Images into one Buffered Image.
    * @param originalImage - The original or container image.
    * @param imageToMerge - The image to be merged into the original one.
    * @return The merged BufferedImage.
    */
   public static BufferedImage mergeImages(BufferedImage originalImage, BufferedImage imageToMerge) {
        ColorModel colorModel = originalImage.getColorModel();
        boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
        WritableRaster raster = originalImage.copyData(null);
        BufferedImage mergedImage = new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
        Graphics2D graphics = mergedImage.createGraphics();
        graphics.drawImage(imageToMerge, 0, originalImage.getHeight() - imageToMerge.getHeight(), null);
        graphics.dispose();
        return mergedImage;
   }
}
