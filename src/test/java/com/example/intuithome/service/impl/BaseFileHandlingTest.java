package com.example.intuithome.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class BaseFileHandlingTest {

    @Spy
    BaseFileHandling mFileHandling;

    @Test
    public void readFileAndInsertToDB_nullFile(){
        mFileHandling.readFileAndInsertToDB(null);

        verify(mFileHandling, never()).runBatch(any(), any());
    }

    @Test
    public void readFileAndInsertToDB_fileNotExist(){
        mFileHandling.readFileAndInsertToDB("test1.csv");

        verify(mFileHandling, never()).runBatch(any(), any());
    }

    @Test
    public void readFileAndInsertToDB_emptyFile(){
        mFileHandling.readFileAndInsertToDB("emptyTest.csv");

        verify(mFileHandling, never()).runBatch(any(), any());
    }

    @Test
    public void readFileAndInsertToDB_onlyHeaderFile(){
        mFileHandling.readFileAndInsertToDB("onlyHeaderTest.csv");

        verify(mFileHandling, times(1)).runBatch(any(), any());
    }

    @Test
    public void readFileAndInsertToDB_properFile(){
        mFileHandling.readFileAndInsertToDB("test.csv");

        verify(mFileHandling, times(1)).runBatch(any(), any());
    }
}