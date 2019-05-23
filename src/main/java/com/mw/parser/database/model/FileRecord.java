package com.mw.parser.database.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "CSV_TABLE")
@Data
@EqualsAndHashCode
public class FileRecord {

    @Id
    @CsvBindByName(column = "PRIMARY_KEY", required = true)
    private String primaryKey;

    @Column
    @CsvBindByName(column = "NAME", required = true)
    private String name;

    @Column
    @CsvBindByName(column = "DESCRIPTION", required = true)
    private String description;

    @Column
    @CsvBindByName(column = "UPDATED_TIMESTAMP", required = true)
    @CsvDate("yyyy-MM-dd'T'HH:mm:ss")
    private Date updateTime;
}
