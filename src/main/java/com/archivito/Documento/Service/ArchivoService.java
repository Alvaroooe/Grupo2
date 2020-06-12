package com.archivito.Documento.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.archivito.Base.AWS.ClientBuilder;
import com.archivito.Base.Constant.Define;
import com.archivito.Documento.Model.Archivo;
import com.archivito.Documento.Model.Objeto;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class ArchivoService {


    List<Archivo> archivos = new ArrayList<>();

    
    public List<String> listar() {
        ClientBuilder clientBuilder = new ClientBuilder();
        AmazonS3 amazonS3 = clientBuilder.clientBuilder();

        List<String> list = new ArrayList<>();

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(Define.BUCKET_NAME);
        ObjectListing objectListing = amazonS3.listObjects(listObjectsRequest);

        List<S3ObjectSummary> summaries = objectListing.getObjectSummaries();
        if (summaries.size() > 0) {
            for (S3ObjectSummary item : summaries) {
                if (!item.getKey().endsWith("/")) list.add(item.getKey());
            }
        }

        return list;
    }
    
    public Objeto eliminar(String id){

        ClientBuilder clientBuilder = new ClientBuilder();
        AmazonS3 amazonS3 = clientBuilder.clientBuilder();

        //amazonS3.deleteObject(Define.BUCKET_NAME, nombre);
        try {
        	amazonS3.deleteObject(Define.BUCKET_NAME, id);
        	Objeto obj = new Objeto("true", "El archivo " +id + " se elimino correctamente");
        	return obj;
        }
        catch (AmazonClientException e) {
        	Objeto obj = new Objeto("false", "Error, no se elimino los archivos");
        	return obj;
        }

    }
    
    public Objeto descargar(String id){

    	ClientBuilder clientBuilder = new ClientBuilder();
        AmazonS3 amazonS3 = clientBuilder.clientBuilder();
        
        try {
            
            S3Object s3Object = amazonS3.getObject(Define.BUCKET_NAME, id);
            S3ObjectInputStream objectContent = s3Object.getObjectContent();
            
            FileUtils.copyInputStreamToFile(objectContent, new File("D:\\"+id));
            
        	Objeto obj = new Objeto("true", "El archivo " +id + " se ha descargado satisfactoriamente");
        	return obj;
        } catch (IOException e) {
            e.printStackTrace();
            Objeto obj = new Objeto("false", "Error, no se descargado el archivo");
        	return obj;
        }

    }
    
}

