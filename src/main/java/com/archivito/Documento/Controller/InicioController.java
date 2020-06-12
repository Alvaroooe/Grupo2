package com.archivito.Documento.Controller;


import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.services.s3.model.ObjectMetadata;

import com.archivito.Base.AWS.ClientBuilder;
import com.archivito.Base.Constant.Define;
import com.archivito.Documento.Model.Archivo;
import com.archivito.Documento.Model.Documento;
import com.archivito.Documento.Model.Objeto;
import com.archivito.Documento.Service.ArchivoService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/")
public class InicioController {

	@Autowired
    ArchivoService archivoService;


    @GetMapping(value={"", "/"})
    public String iniciarSesion(Model model){

        model.addAttribute("objeto", archivoService.listar()); 
        return "page/archivo";
    }

    @RequestMapping(value = "", method = RequestMethod.POST,consumes = {"multipart/form-data"})
    @ResponseBody
    public Documento save(@RequestParam("archivos") ArrayList<MultipartFile> archivos) throws IOException {

        Documento documento = new Documento();
        Set<Archivo> listArchivos = new HashSet<>();


        if(archivos.size() > 0) {

            ClientBuilder clientBuilder = new ClientBuilder();
            AmazonS3 amazonS3 = clientBuilder.clientBuilder();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            for (MultipartFile archivo : archivos) {

                String archivo_real = archivo.getOriginalFilename();
                String archivo_ext = archivo_real.substring(archivo_real.lastIndexOf('.'));
                String archivo_rand = UUID.randomUUID().toString();
                String archivo_nuevo = archivo_rand+(archivo_ext).toLowerCase();

                amazonS3.putObject( Define.BUCKET_NAME, archivo_nuevo, archivo.getInputStream(), objectMetadata );

                Archivo arch = new Archivo();
                arch.setName(archivo_nuevo);
                listArchivos.add(arch);
            }

        }
        documento.setArchivos(listArchivos);
        return documento ;
    }

    @GetMapping(value="/listar")
    @ResponseBody
    public List<String> listar() {
       return archivoService.listar();
    }
    
    
    @DeleteMapping(value="/{id}")
    @ResponseBody
    public Objeto eliminar(@PathVariable(name = "id") String id){

        return archivoService.eliminar(id);

    }

    @GetMapping(value="/descargar/{id}")
    @ResponseBody
    public Objeto descargar(@PathVariable(name = "id") String id){

    	return archivoService.descargar(id);

    }

}
