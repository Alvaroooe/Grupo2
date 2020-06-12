package com.archivito.Base.AWS;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.archivito.Base.Constant.Define;


public class ClientBuilder {

    AWSCredentials credentials = new BasicAWSCredentials(Define.ACCESSKEY, Define.SECRETKEY);

    public AmazonS3 clientBuilder(){
        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_WEST_1)
                .build();

        if( !s3Client.doesBucketExistV2(Define.BUCKET_NAME) ) s3Client.createBucket(Define.BUCKET_NAME);

        return s3Client;
    }



}
