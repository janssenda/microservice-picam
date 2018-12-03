package com.dm.micropifs;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;

@Controller
public class ReceiveController {

    private static List<byte[]> store = new LinkedList<byte[]>(){

        public boolean add(byte[] object) {
            boolean result;
            if(this.size() < 10)
                result = super.add(object);
            else
            {
                super.removeFirst();
                result = super.add(object);
            }
            return result;
        }
    };

    private int count = 0;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Object index(){

        return "/static/index.html";
    }


    @RequestMapping(value = "/next", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Object> getFrameData() {

        HttpHeaders headers = new HttpHeaders();

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.IMAGE_JPEG)
                .body(store.get(0));


    }

    //consumes = { "multipart/form-data" }
    //@RequestParam("file") MultipartFile file
    @RequestMapping(value = {"/receive"}, method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile (@RequestParam("file") MultipartFile file) throws Exception {

    //    MultipartFile file = (MultipartFile) request;
//        this.store.clear();
//        this.store.add(file.getBytes());

        store.add(file.getBytes());
        this.count += 1;

        System.out.println("Recieved: " + this.count + " --> store size: " + store.size());


        return "success";


    }





}