package com.ndl.trustviec.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "File")
@Table(name = "file")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity extends AbstractAuditingEntity {

    @Column(name = "cif")
    private String cif;

//    @Column(name = "parent_id")
//    private UUID parentId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "origin_file_name")
    private String originFileName;

    @Column(name = "url", columnDefinition = "TEXT")
    private String url;

    @Column(name = "expire_at")
    private LocalDateTime expireAt;
}
