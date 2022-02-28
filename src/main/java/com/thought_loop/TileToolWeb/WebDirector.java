package com.thought_loop.TileToolWeb;


import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@RestController
public class WebDirector {
    private Layout layout;
    private Room room;
    private Tile tile;

    //MediaType.APPLICATION_JSON_VALUE
    // MediaType.APPLICATION_FORM_URLENCODED_VALUE
    //"application/json; charset=UTF-8"
    @PostMapping(path ="/measure_submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] generateImage(WebLayout webLayout){
   //public String generateImage(WebLayout webLayout){

        LayoutRendering layoutRendering = new LayoutRendering(webLayout);
        BufferedImage layoutAsBufferedImage = layoutRendering.generateBufferedImage(Integer.parseInt(webLayout.getPatternType()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(layoutAsBufferedImage, "jpeg", outputStream);
            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            //System.out.println(IOUtils.toString(inputStream,"UTF-8"));
            return IOUtils.toByteArray(inputStream);
            //return IOUtils.toString(inputStream,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @GetMapping (path ="/clear_images")
    public String clearImages(){
        String[] pathnames;
        File imageDirectory = new File("GeneratedImages");
        boolean error = false;
        int numDeleted = 0;
        pathnames = imageDirectory.list();
        for(String pathname: pathnames){
            System.out.println(pathname);
            File file = new File(pathname);
            if(file.delete()) {
                numDeleted++;
            }
        }
        return "Number of files before delete: " + pathnames.length + " Number of files deleted: " + numDeleted;
    }

}
