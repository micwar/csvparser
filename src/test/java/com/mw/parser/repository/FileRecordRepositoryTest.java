package com.mw.parser.repository;

import com.mw.parser.database.model.FileRecord;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FileRecordRepositoryTest {

    @Autowired
    private FileRecordRepository fileRecordRepository;

    private static final String ID = "Id_1";
    private static final String NAME1 = "Name_1";
    private static final String NAME2 = "Name_2";
    private static final String DESCRIPTION = "Description_1";

    @Test
    @Transactional
    public void testDatabaseCRUD(){

        FileRecord fileRecord = new FileRecord();
        fileRecord.setPrimaryKey(ID);
        fileRecord.setName(NAME1);
        fileRecord.setDescription(DESCRIPTION);
        fileRecord.setUpdateTime(new Date());

        fileRecordRepository.save(fileRecord);

        FileRecord fromDB = fileRecordRepository.getOne(ID);

        Assert.assertEquals(fromDB, fileRecord);

        fileRecord.setName(NAME2);
        fileRecordRepository.save(fileRecord);

        fromDB = fileRecordRepository.getOne(ID);

        Assert.assertEquals(fromDB.getName(), NAME2);
    }
}
