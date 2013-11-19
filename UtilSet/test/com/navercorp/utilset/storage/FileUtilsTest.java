package com.navercorp.utilset.storage;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;



@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class FileUtilsTest {
    @Rule 
    public TemporaryFolder testFolder = new TemporaryFolder();
    String destFilePath;
    String fileName = "test.txt";
	String content = "hello utilset";
    
    @Before
    public void setUp() throws Exception{
        File downloadFolder = testFolder.newFolder("download");
        this.destFilePath = downloadFolder.getAbsolutePath();
    }
    
    @Test
    public void shouldCreateFileAndGetContent(){
    	// given
    	InputStream inputStream = new ByteArrayInputStream(content.getBytes());

    	// when
		boolean created = FileUtils.createAndCopyContentToFile(destFilePath, fileName, inputStream);
		
		// then
		String path = destFilePath + File.separator + fileName;
		assertThat(created, is(true));
		assertThat(FileUtils.exist(path), is(true));
		assertThat(FileUtils.getFileContent(path).trim(), is(content));
    }

    @Test
    public void shouldDeleteFile(){
    	// given
    	InputStream inputStream = new ByteArrayInputStream(content.getBytes());
    	FileUtils.createAndCopyContentToFile(destFilePath, fileName, inputStream);
    	String path = destFilePath + File.separator + fileName;

    	// when
    	boolean deleted = FileUtils.delete(path);

    	// then
    	assertThat(deleted, is(true));
		assertThat(FileUtils.exist(path), is(false));
    }

    @Test
    public void shouldGetFalseWhenDeleteNonExistedFile(){
    	// given
    	String path = "This is a wrong path";

    	// when
    	boolean deleted = FileUtils.delete(path);

    	// then
    	assertThat(deleted, is(false));
    }
}
