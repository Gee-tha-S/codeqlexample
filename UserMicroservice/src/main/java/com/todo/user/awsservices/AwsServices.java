package com.todo.user.awsservices;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class AwsServices {
	private static final String SUFFIX = "/";
	static AWSCredentials credentials = new BasicAWSCredentials(System.getenv("AWS_ACCESS_KEY_ID"),
			System.getenv("AWS_SECRET_ACCESS_KEY"));
	// static AmazonS3 s3client = new AmazonS3Client(credentials);

	static AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard()
			.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_SOUTH_1).build();

	public static String createBucket(String bucketName) {
		CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName,
				com.amazonaws.services.s3.model.Region.AP_Mumbai);
		Bucket resp = amazonS3Client.createBucket(createBucketRequest);
		return resp.getName();

	}

	
	
	public static String createFolder(String bucketName, String folderName) {
		// create meta-data for your folder and set content-length to 0
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);
		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
		// create a PutObjectRequest passing the folder name suffixed by /
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName + SUFFIX, emptyContent,
				metadata);
		// send request to S3 to create folder
		amazonS3Client.putObject(putObjectRequest);
		return folderName;
	}

	public static String uploadFile(String bucketName, String folderName, String path) {
		String fileName =folderName + SUFFIX +path.substring(path.lastIndexOf("/")+1,path.lastIndexOf("."));
				//folderName + SUFFIX + "3dimage.jpg";
		amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, new File(path))
				.withCannedAcl(CannedAccessControlList.PublicRead));
		return "file uploaded!!";

	}

	public static String deleteBucket(String bucketName) {
		for (Bucket bucket : amazonS3Client.listBuckets()) {
			System.out.println(" - " + bucket.getName());
		}
		amazonS3Client.deleteBucket(bucketName);

		return "bucket deleated";

	}

}
